package com.example.mediaplayerapp;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class VideoPlayerActivity extends AppCompatActivity {

    VideoView videoView;
    EditText urlInput;
    TextView videoTime;

    Button openUrl, play, pause, stop, restart;

    Handler handler = new Handler();

    Runnable updateVideoTime = new Runnable() {
        @Override
        public void run() {

            if(videoView != null){

                int current = videoView.getCurrentPosition();
                int total = videoView.getDuration();

                if(total > 0){
                    videoTime.setText(formatTime(current) + " / " + formatTime(total));
                }

                handler.postDelayed(this, 500);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        videoView = findViewById(R.id.videoView);
        urlInput = findViewById(R.id.urlInput);
        videoTime = findViewById(R.id.videoTime);

        openUrl = findViewById(R.id.openUrlBtn);
        play = findViewById(R.id.playVideo);
        pause = findViewById(R.id.pauseVideo);
        stop = findViewById(R.id.stopVideo);
        restart = findViewById(R.id.restartVideo);

        openUrl.setOnClickListener(v -> {

            String url = urlInput.getText().toString().trim();

            if(!url.isEmpty()){

                Uri uri = Uri.parse(url);

                videoView.setVideoURI(uri);

                videoView.setOnPreparedListener(mp -> {

                    videoView.start();

                    videoTime.setText("00:00 / " + formatTime(videoView.getDuration()));

                    handler.post(updateVideoTime);

                });

            }

        });

        play.setOnClickListener(v -> {

            videoView.start();
            handler.post(updateVideoTime);

        });

        pause.setOnClickListener(v -> {

            if(videoView.isPlaying()){
                videoView.pause();
            }

        });

        stop.setOnClickListener(v -> {

            videoView.pause();
            videoView.seekTo(0);

            videoTime.setText("00:00 / " + formatTime(videoView.getDuration()));

        });

        restart.setOnClickListener(v -> {

            videoView.seekTo(0);
            videoView.start();
            handler.post(updateVideoTime);

        });

    }

    private String formatTime(int milliseconds){

        int minutes = (milliseconds / 1000) / 60;
        int seconds = (milliseconds / 1000) % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }

}