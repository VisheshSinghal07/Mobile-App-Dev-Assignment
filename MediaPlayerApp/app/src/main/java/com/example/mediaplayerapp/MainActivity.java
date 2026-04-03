package com.example.mediaplayerapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button audioBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audioBtn = findViewById(R.id.audioBtn);

        audioBtn.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this,
                    AudioPlayerActivity.class);

            startActivity(intent);

        });

    }
}