package com.example.sroyals;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.sroyals.databinding.ActivityMemesBinding;
import com.example.sroyals.databinding.ActivityWishingScreenBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class memes extends AppCompatActivity {

    MediaPlayer mediaPlayer=new MediaPlayer();
    ImageView memeimage;
    Button next,share;
    ProgressBar progressBar;
    public String url;
    ActivityMemesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memes);
        binding = ActivityMemesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mediaPlayer=MediaPlayer.create(memes.this,R.raw.song4);
        mediaPlayer.start();
        memeimage=findViewById(R.id.memeimage);
        next=findViewById(R.id.next);
        share=findViewById(R.id.share);
        progressBar=findViewById(R.id.progressBar);
        memecall();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memecall();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    shareimage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void memecall(){
        url="https://meme-api.com/gimme";
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.memeimage.setVisibility(View.GONE);
        RequestQueue que= Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String imgUrl=response.getString("url");
                            Glide.with(getApplicationContext()).load(imgUrl).into(binding.memeimage);
                            binding.progressBar.setVisibility(View.GONE);
                            binding.memeimage.setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(memes.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        que.add(jsonObjectRequest);
        // Access the RequestQueue through your singleton class.
        MySingelton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
    public void shareimage() throws IOException {
        StrictMode.VmPolicy.Builder builder=new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        BitmapDrawable drawable=(BitmapDrawable)memeimage.getDrawable();
        Bitmap bitmap=drawable.getBitmap();
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

    @Override
    protected void onStart() {
        super.onStart();
    }
}