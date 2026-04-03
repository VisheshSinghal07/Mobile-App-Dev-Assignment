package com.example.mediaplayerapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button audioBtn, videoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audioBtn = findViewById(R.id.audioBtn);
        videoBtn = findViewById(R.id.videoBtn);

        audioBtn.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this,
                    AudioPlayerActivity.class);
            startActivity(intent);

        });

        videoBtn.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this,
                    VideoPlayerActivity.class);
            startActivity(intent);

        });

    }
}