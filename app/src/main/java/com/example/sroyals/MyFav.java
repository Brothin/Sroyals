package com.example.sroyals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MyFav extends AppCompatActivity {

    MediaPlayer mediaPlayer=new MediaPlayer();
    Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fav);

        mediaPlayer=MediaPlayer.create(MyFav.this,R.raw.song3);
        mediaPlayer.start();
        nextButton=findViewById(R.id.share);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                Intent intent = new Intent(MyFav.this, memes.class);
                startActivity(intent);
            }
        });
    }
}