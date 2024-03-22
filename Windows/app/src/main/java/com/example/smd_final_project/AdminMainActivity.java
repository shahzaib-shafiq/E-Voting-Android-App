package com.example.smd_final_project;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminMainActivity extends AppCompatActivity {
    ViewPager2 viewPagerAdmin;
    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    BottomNavigationView bottomnaavAdmin;
    String imageUri,audioUri,symbol;
    FirebaseDatabase database;
    FirebaseStorage storage;
    AdminHomeFragment adminHomeFragment;

    ActivityResultLauncher<String> launcher,audioLauncher,launcherSymbol;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        viewPagerAdmin = findViewById(R.id.viewpagerAdmin);
        bottomnaavAdmin = findViewById(R.id.bottomnavAdmin);
        fragmentArrayList.add(new AdminHomeFragment());
        fragmentArrayList.add(new AdminUserFragment());
        fragmentArrayList.add(new AdminCandidateFragment());
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance(/*"gs://final-project-8ec33.appspot.com"*/);


        AdminAdapter adminAdapter = new AdminAdapter(this,fragmentArrayList);
        viewPagerAdmin.setAdapter(adminAdapter);
        viewPagerAdmin.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottomnaavAdmin.setSelectedItemId(R.id.home);
                        break;
                    case 1:
                        bottomnaavAdmin.setSelectedItemId(R.id.user);
                        break;
                    case 2:
                        bottomnaavAdmin.setSelectedItemId(R.id.candidate);
                        break;
                }
                super.onPageSelected(position);
            }
        });
        bottomnaavAdmin.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        viewPagerAdmin.setCurrentItem(0);
                        break;
                    case R.id.user:
                        viewPagerAdmin.setCurrentItem(1);
                        break;
                    case R.id.candidate:
                        viewPagerAdmin.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });

        launcherSymbol = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                Fragment currentFragment = fragmentArrayList.get(viewPagerAdmin.getCurrentItem());
                if(currentFragment instanceof AdminHomeFragment){
                    AdminHomeFragment adminHomeFragment = (AdminHomeFragment) currentFragment;
                    adminHomeFragment.SetSymbol(result);
                }
            }
        });

        launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                Fragment currentFragment = fragmentArrayList.get(viewPagerAdmin.getCurrentItem());
                if(currentFragment instanceof AdminHomeFragment){
                    AdminHomeFragment adminHomeFragment = (AdminHomeFragment) currentFragment;
                    adminHomeFragment.setImage(result);
                }
            }
        });

        audioLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                // Handle the selected audio file URI
                Fragment currentFragment = fragmentArrayList.get(viewPagerAdmin.getCurrentItem());
                if (currentFragment instanceof AdminHomeFragment) {
                    AdminHomeFragment adminHomeFragment = (AdminHomeFragment) currentFragment;
                    adminHomeFragment.setAudio(result);
                }
            }
        });
    }




    public void SetProfileImage(View view) {
        launcher.launch("image/*");
    }

    public void SetAudio(View view) {
        audioLauncher.launch("audio/*");
    }

    public void SaveToFirebase(View view) {
        int currentPosition = viewPagerAdmin.getCurrentItem();
        Fragment currentFragment = fragmentArrayList.get(currentPosition);

    if (currentFragment instanceof AdminHomeFragment) {
        AdminHomeFragment adminHomeFragment = (AdminHomeFragment) currentFragment;
        final StorageReference[] storageReference = {storage.getReference().child("image/").child(adminHomeFragment.getImgName())};
        storageReference[0].putFile(adminHomeFragment.getImage()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference[0].getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        imageUri = uri.toString();
                        storageReference[0] = storage.getReference().child("audio/").child(adminHomeFragment.getAudioName());
                        storageReference[0].putFile(adminHomeFragment.getAudio()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                storageReference[0].getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        audioUri = uri.toString();
                                        storageReference[0] = storage.getReference().child("symbol/").child(adminHomeFragment.getSymbolName());
                                        storageReference[0].putFile(adminHomeFragment.getSymbol()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                storageReference[0].getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        symbol = uri.toString();
                                                        Candidate candidate = new Candidate(imageUri,audioUri,symbol,adminHomeFragment.getName(),
                                                                adminHomeFragment.getCnic(),adminHomeFragment.getPartyName(),adminHomeFragment.getArea(),
                                                                adminHomeFragment.getType().toString(),0);
                                                        FirebaseDatabase.getInstance().getReference().child("Candidate").push().setValue(candidate);
                                                        Toast.makeText(getApplicationContext(), "User Added!", Toast.LENGTH_SHORT).show();
                                                        Log.d("TAG", "SaveToFirebaseAndSQLite: "+adminHomeFragment.getArea()+audioUri);
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
        // upload data
    }
    }

    public void SetIntakhabiNishan(View view) {
        launcherSymbol.launch("image/*");
    }
}