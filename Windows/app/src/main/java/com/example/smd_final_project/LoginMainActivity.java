package com.example.smd_final_project;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginMainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    FloatingActionButton fb, google, twitter;
    float opacity = 0;
    LoginAdapter loginAdapter;

    private SigninFragment signinFragment;
    private LoginFragment loginFragment;

    EditText edt_Cnin,edt_pass,edt_Name;
    FirebaseDatabase database;
    DatabaseReference reference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        database = FirebaseDatabase.getInstance(); // get all the tables
        reference  = database.getReference("User");// access the particular table in firebase

        edt_Cnin=findViewById(R.id.edtcancnic);
        edt_pass=findViewById(R.id.edtpassword);
        edt_Name =findViewById(R.id.edtname);

        tabLayout = findViewById(R.id.tab_layout_login);
        viewPager = findViewById(R.id.viewPagerLogin);
        fb = findViewById(R.id.fab_facebook);
        google = findViewById(R.id.fab_google);
        twitter = findViewById(R.id.fab_twitter);

        tabLayout.addTab(tabLayout.newTab().setText("Log In"));
        tabLayout.addTab(tabLayout.newTab().setText("Sign UP"));


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        loginAdapter = new LoginAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), this);
        viewPager.setAdapter(loginAdapter);

        // Add a TabLayout.OnTabSelectedListener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        fb.setTranslationY(300);
        google.setTranslationY(300);
        twitter.setTranslationY(300);
        tabLayout.setTranslationY(300);

        fb.setAlpha(opacity);
        google.setAlpha(opacity);
        twitter.setAlpha(opacity);
        tabLayout.setAlpha(opacity);

        fb.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        google.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        twitter.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();
        tabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();
    }




    public void Login(View view) {
        Toast.makeText(this, "Login pressed!", Toast.LENGTH_SHORT).show();
        loginFragment = (LoginFragment) loginAdapter.instantiateItem(viewPager, 0);
        String cnic = loginFragment.getCnic();
        String password = loginFragment.getPassword();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User");

        Query query = myRef.orderByChild("cnic").equalTo(cnic);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
//                    String key = childSnapshot.getKey();
//                    DatabaseReference recordRef = myRef.child(key);
                        Log.d("TAG", "onDataChange: "+cnic+childSnapshot.child("password").getValue(String.class)+password);
                        if(childSnapshot.child("password").getValue(String.class).equals(password)
                                && childSnapshot.child("role").getValue(String.class).equals("Admin")){
                            Log.d("TAG", "onDataChange: run");
                            startActivity(new Intent(LoginMainActivity.this,AdminMainActivity.class));
                        } else if (childSnapshot.child("password").getValue(String.class).equals(password)
                                && childSnapshot.child("role").getValue(String.class).equals("User")){
                            Intent intent = new Intent(LoginMainActivity.this,UserMainActivity.class);
                            intent.putExtra("cnic",cnic);
                            intent.putExtra("password",password);
                            intent.putExtra("role","User");
                            intent.putExtra("name",childSnapshot.child("name").getValue(String.class));
                            intent.putExtra("isVote",childSnapshot.child("vote").getValue(Boolean.class));
                            startActivity(intent);
                        }

                    }
                }
                else{
                    Toast.makeText(LoginMainActivity.this, "Student Not Found!", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }

    public void SignUp(View view) {
        signinFragment = (SigninFragment) loginAdapter.instantiateItem(viewPager, 1);
        String cnic = signinFragment.getCnic();
        String password = signinFragment.getPassword();
        String name = signinFragment.getName();
        User user = new User("User",cnic,name,password,Boolean.FALSE);
        FirebaseDatabase.getInstance().getReference().child("User").push()
                .setValue(user);
        Toast.makeText(this, "User Added!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(LoginMainActivity.this,UserMainActivity.class));
    }
}