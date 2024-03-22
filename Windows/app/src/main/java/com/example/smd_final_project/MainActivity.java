package com.example.smd_final_project;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class MainActivity extends AppCompatActivity {

    ImageView applogo,voterlogo;
    Button login;
    float opacity = 0;
    FirebaseDatabase database;
    FirebaseStorage storage;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // This will hide the action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        database= FirebaseDatabase.getInstance("https://final-project-8ec33-default-rtdb.firebaseio.com/");
        storage = FirebaseStorage.getInstance("gs://final-project-8ec33.appspot.com");
        applogo= findViewById(R.id.imgapplogo);
        voterlogo = findViewById(R.id.imgvoterlogo);
        login = findViewById(R.id.btnvotenow);

        applogo.setTranslationY(-300);
        voterlogo.setTranslationY(300);
        login.setTranslationY(300);


        applogo.setAlpha(opacity);
        voterlogo.setAlpha(opacity);
        login.setAlpha(opacity);


        applogo.animate().translationY(0).alpha(1).setDuration(2000).setStartDelay(400).start();
        voterlogo.animate().translationY(0).alpha(1).setDuration(2000).setStartDelay(600).start();
        login.animate().translationY(0).alpha(1).setDuration(2000).setStartDelay(800).start();


    }

    public void MainPage(View view) {
        startActivity(new Intent(this,LoginMainActivity.class));
    }
}