package com.example.mediaplayerapp;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.OpenableColumns;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AudioPlayerActivity extends AppCompatActivity {

    Button openFile, play, pause, stop, restart;
    TextView audioName, timeText;

    MediaPlayer mediaPlayer;
    Uri audioUri;

    Handler handler = new Handler();

    static final int PICK_AUDIO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);

        openFile = findViewById(R.id.openFileBtn);
        play = findViewById(R.id.playBtn);
        pause = findViewById(R.id.pauseBtn);
        stop = findViewById(R.id.stopBtn);
        restart = findViewById(R.id.restartBtn);
        audioName = findViewById(R.id.audioName);
        timeText = findViewById(R.id.timeText);

        play.setEnabled(false);
        pause.setEnabled(false);
        stop.setEnabled(false);
        restart.setEnabled(false);

        openFile.setOnClickListener(v -> {

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("audio/*");

            startActivityForResult(intent, PICK_AUDIO);

        });

        play.setOnClickListener(v -> {

            if(mediaPlayer != null && !mediaPlayer.isPlaying()){
                mediaPlayer.start();
                handler.post(updateTime);
            }

        });

        pause.setOnClickListener(v -> {

            if(mediaPlayer != null && mediaPlayer.isPlaying()){
                mediaPlayer.pause();
            }

        });

        stop.setOnClickListener(v -> {

            if(mediaPlayer != null){

                mediaPlayer.stop();
                mediaPlayer.release();

                try {

                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(this, audioUri);
                    mediaPlayer.prepare();

                    timeText.setText("00:00 / " + formatTime(mediaPlayer.getDuration()));

                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }

        });

        restart.setOnClickListener(v -> {

            if(mediaPlayer != null){
                mediaPlayer.seekTo(0);
                mediaPlayer.start();
                handler.post(updateTime);
            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){

        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == PICK_AUDIO && resultCode == RESULT_OK && data != null){

            audioUri = data.getData();

            try {

                if(mediaPlayer != null){
                    mediaPlayer.release();
                }

                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(this,audioUri);
                mediaPlayer.prepare();

                timeText.setText("00:00 / " + formatTime(mediaPlayer.getDuration()));

            }
            catch (Exception e){
                e.printStackTrace();
            }

            audioName.setText("Selected: " + getFileName(audioUri));

            play.setEnabled(true);
            pause.setEnabled(true);
            stop.setEnabled(true);
            restart.setEnabled(true);
        }

    }

    Runnable updateTime = new Runnable() {

        @Override
        public void run() {

            if(mediaPlayer != null && mediaPlayer.isPlaying()){

                int current = mediaPlayer.getCurrentPosition();
                int total = mediaPlayer.getDuration();

                timeText.setText(formatTime(current) + " / " + formatTime(total));

                handler.postDelayed(this,500);
            }

        }

    };

    private String formatTime(int milliseconds){

        int minutes = (milliseconds / 1000) / 60;
        int seconds = (milliseconds / 1000) % 60;

        return String.format("%02d:%02d",minutes,seconds);
    }

    private String getFileName(Uri uri){

        String result = null;

        if(uri.getScheme().equals("content")){

            Cursor cursor = getContentResolver()
                    .query(uri,null,null,null,null);

            try{

                if(cursor != null && cursor.moveToFirst()){

                    result = cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    OpenableColumns.DISPLAY_NAME));

                }

            }finally {

                if(cursor != null)
                    cursor.close();
            }

        }

        if(result == null){
            result = uri.getPath();
        }

        return result;
    }

}