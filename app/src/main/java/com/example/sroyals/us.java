package com.example.sroyals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class us extends AppCompatActivity {

    MediaPlayer mediaPlayer=new MediaPlayer();
    VideoView videoView;
    Button nextButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_us);

        mediaPlayer=MediaPlayer.create(us.this,R.raw.song2);
        mediaPlayer.start();

        videoView=findViewById(R.id.video_view);
        String videoPath="android.resource://"+getPackageName()+"/"+R.raw.video;
        Uri uri=Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        MediaController mediaController=new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        videoView.start();

        nextButton=findViewById(R.id.share);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                Intent intent = new Intent(us.this, YourFav.class);
                startActivity(intent);
            }
        });
    }
}