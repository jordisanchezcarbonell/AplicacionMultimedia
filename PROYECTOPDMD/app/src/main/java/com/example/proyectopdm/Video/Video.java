package com.example.proyectopdm.Video;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.proyectopdm.R;
import com.github.chrisbanes.photoview.PhotoView;


/* ----------------------------------------------------------------------------*
Classe:Se vera el video que se obtiene de un intent
* -------------------------------------------------------------------------*/
public class Video extends AppCompatActivity {
    VideoView videoView;
    ImageView imageView;
    PhotoView photoView;
    MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videofullscreen);
        videoView = findViewById(R.id.videoview);
        mediaController = new MediaController(this);
        //imageView = findViewById(R.id.IV1);
        photoView = (PhotoView) findViewById(R.id.IV1);

        String uri = getIntent().getStringExtra("VIDEO_URI");
         Uri videoUri = Uri.parse(uri);


        videoView.setMediaController(mediaController);

        videoView.setVideoURI(videoUri);

        videoView.requestFocus();
        videoView.start();


    }

}