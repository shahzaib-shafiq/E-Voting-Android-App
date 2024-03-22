package com.example.smd_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class CastVoteMainActivity extends AppCompatActivity {

    ImageView imgCastVote;
    TextView name,party,noofvotes;
    Button btnVote;

    String audioUrl;
    private MyBoundService myBoundService;
    private boolean myBoundServiceFlag = false;
    Candidate candidate = new Candidate();
    User user = new User();



    private ServiceConnection myServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            MyBoundService.MyBinder myBinder = (MyBoundService.MyBinder) iBinder;
            myBoundService = myBinder.getServiceMethod();
            myBoundServiceFlag = true;
//            myBoundService.setAudiourl(candidate.getAudioUrl());
            myBoundService.setAudiourl(audioUrl);
            myBoundService.Play();
            Log.d("TAG","connection Created...");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_vote_main);
        imgCastVote = findViewById(R.id.imgcastvote);
        btnVote = findViewById(R.id.btncastvote);
        name = findViewById(R.id.txtviewname);
        party = findViewById(R.id.txtviewparty);
        noofvotes = findViewById(R.id.txtnoofvotes);

        Intent intent = getIntent();

        Log.d("TAG","onCreate...");

        candidate.setId(String.valueOf(intent.getIntExtra("id",0)));
        candidate.setName(intent.getStringExtra("name"));
        candidate.setCnic(intent.getStringExtra("cnic"));
        candidate.setArea(intent.getStringExtra("area"));
        candidate.setType(intent.getStringExtra("type"));
        candidate.setImageUrl(intent.getStringExtra("imageurl"));
        candidate.setAudioUrl(intent.getStringExtra("audiourl"));
        candidate.setSymbolUrl(intent.getStringExtra("symbol"));
        candidate.setPartyName(intent.getStringExtra("party"));
        candidate.setNoOfVotes(intent.getIntExtra("noofvotes",0));
        user.setName(intent.getStringExtra("username"));
        user.setCnic(intent.getStringExtra("usercnic"));
        user.setVote(intent.getBooleanExtra("userisvote",false));
        user.setRole(intent.getStringExtra("role"));
        user.setPassword(intent.getStringExtra("password"));
        Picasso.get().load(candidate.getImageUrl()).into(imgCastVote);
        audioUrl = candidate.getAudioUrl();
//        Log.d("TAG","bind audio"+audioUrl);
        name.setText(candidate.getName());
        party.setText(candidate.getPartyName());
        noofvotes.setText(String.valueOf(candidate.getNoOfVotes()));
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("TAG","onStart...");
        Intent intent = new Intent(this,MyBoundService.class);
        bindService(intent,myServiceConnection,BIND_AUTO_CREATE);
//        Play_Pause();
    }


    @Override
    protected void onStop() {
        super.onStop();
        if(myBoundServiceFlag){
            unbindService(myServiceConnection);
        }
    }

    public void Play_Pause() {
        if(myBoundServiceFlag)
        {
            if(!myBoundService.isPlay()){
                myBoundService.Play();
            }
            else{
                myBoundService.Pause();
            }
        }
    }

    public void CastVote(View view) {
//        user.setVote(true);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User");
        DatabaseReference myRef2 = database.getReference("Candidate");


        Query query = myRef.orderByChild("cnic").equalTo(user.getCnic());
        if(user.getVote()==false){
            user.setVote(true);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    Log.d("TAG","getrecord...");
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String key = childSnapshot.getKey();
                        Log.d("TAG","getkey ="+key);
                        DatabaseReference recordRef = myRef.child(key);
                        Map<String, Object> updates = new HashMap<>();
                        updates.put("cnic",user.getCnic());
                        updates.put("name",user.getName());
                        updates.put("password",user.getPassword());
                        updates.put("role",user.getRole());
                        updates.put("vote",user.getVote());
                        recordRef.updateChildren(updates);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

            Query query2 = myRef2.orderByChild("cnic").equalTo(candidate.getCnic());
            Log.d("TAG","getcancnic"+candidate.getCnic());
            query2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {
                        Log.d("TAG","getrecord...");
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            String key = childSnapshot.getKey();
                            Log.d("TAG","getkey ="+key);
                            DatabaseReference recordRef = myRef.child(key);
                            Map<String, Object> updates = new HashMap<>();
                            updates.put("cnic",candidate.getCnic());
                            updates.put("name",candidate.getName());
                            updates.put("noOfVotes",candidate.getNoOfVotes()+1);
                            updates.put("partyName",candidate.getPartyName());
                            updates.put("type",candidate.getType());
                            updates.put("area",candidate.getArea());
                            updates.put("imageUrl",candidate.getImageUrl());
                            updates.put("audioUrl",candidate.getAudioUrl());
                            updates.put("symbolUrl",candidate.getSymbolUrl());
                            recordRef.updateChildren(updates);
                            candidate.setNoOfVotes(candidate.getNoOfVotes()+1);
                            noofvotes.setText(String.valueOf(candidate.getNoOfVotes()));
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Log.w("TAG", "Failed to read value.", error.toException());
                }
            });
        }

    }

}