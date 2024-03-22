package com.example.smd_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class UserMainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Candidate> candidateArrayList = new ArrayList<>();

    ArrayList<User> userArrayList = new ArrayList<>();


    FirebaseDatabase firebaseDatabase;
    VoterAdapter voterAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        Intent intent = getIntent();
        User user = new User();
        user.setName(intent.getStringExtra("name"));
        user.setCnic(intent.getStringExtra("cnic"));
        user.setVote(intent.getBooleanExtra("isVote",false));
        user.setRole(intent.getStringExtra("role"));
        user.setPassword(intent.getStringExtra("password"));
        userArrayList.add(user);
        firebaseDatabase = FirebaseDatabase.getInstance();
        recyclerView = findViewById(R.id.recyclervoterview);
        voterAdapter = new VoterAdapter(getApplicationContext(),candidateArrayList,userArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(voterAdapter);
        insert();
    }

    private void insert() {
        DatabaseReference myRef = firebaseDatabase.getReference("Candidate");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Candidate candidate = new Candidate();
                    String Id = snapshot.getKey();
                    candidate.setId(Id);
                    candidate.setName(Objects.requireNonNull(snapshot.child("name").getValue()).toString());
                    candidate.setCnic(Objects.requireNonNull(snapshot.child("cnic").getValue()).toString());
                    candidate.setArea(Objects.requireNonNull(snapshot.child("area").getValue()).toString());
                    candidate.setType(Objects.requireNonNull(snapshot.child("type").getValue()).toString());
                    candidate.setImageUrl(Objects.requireNonNull(snapshot.child("imageUrl").getValue()).toString());
                    candidate.setAudioUrl(Objects.requireNonNull(snapshot.child("audioUrl").getValue()).toString());
                    candidate.setSymbolUrl(Objects.requireNonNull(snapshot.child("symbolUrl").getValue()).toString());
                    candidate.setPartyName(Objects.requireNonNull(snapshot.child("partyName").getValue()).toString());
                    candidate.setNoOfVotes(Integer.parseInt(Objects.requireNonNull(snapshot.child("noOfVotes").getValue()).toString()));
                    candidateArrayList.add(candidate);
                    voterAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//
//            }
//    });
    }
}
