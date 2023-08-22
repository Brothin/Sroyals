package com.example.sroyals;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.example.sroyals.databinding.ActivityWishingScreenBinding;

public class WishingScreen extends AppCompatActivity {

    //View Binding
    ActivityWishingScreenBinding binding;
    MediaPlayer mediaPlayer=new MediaPlayer();
    Button nextButton;
    String name,msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWishingScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mediaPlayer=MediaPlayer.create(WishingScreen.this,R.raw.song);
        mediaPlayer.start();

        nextButton=findViewById(R.id.nextButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                Intent intent = new Intent(WishingScreen.this, us.class);
                startActivity(intent);
            }
        });

        name="Shreya";
        msg = "Happy Birthday "+name+" \uD83C\uDF89 and many many happy returns of the day!! \uD83C\uDF82 \uD83E\uDD73 \uD83C\uDF89 \uD83C\uDF81 \uD83C\uDF8A \uD83E\uDDC1 \uD83C\uDF70";

        binding.name.setText(name);

        //working with share button
        binding.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Share the Image
                Bitmap image = getBitmapFromView(binding.finalImage);
                try {
                    shareImageAndText(image);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void shareImageAndText(Bitmap image) throws IOException {

        StrictMode.VmPolicy.Builder builder=new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Bitmap bitmap=image;
        File f=new File(getExternalCacheDir()+"/"+"Meme app"+".png");
        Intent shareimage=new Intent(Intent.ACTION_SEND);
        FileOutputStream outputStream =new FileOutputStream(f);
        bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
        outputStream.flush();
        outputStream.close();
        shareimage.setType("image/*");
        shareimage.putExtra(Intent.EXTRA_TEXT,"Hey check out this cool meme.");
        shareimage.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
        shareimage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(shareimage);
    }

    private Bitmap getBitmapFromView(View view) {
        //Define a bitmap with same height and width
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(),view.getHeight(),Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the background view of layout
        Drawable background = view.getBackground();
        if(background != null){
            background.draw(canvas);
        }else{
            canvas.drawColor(Color.WHITE);
        }
        //draw the view on canvas
        view.draw(canvas);

        return returnedBitmap;

    }

}
