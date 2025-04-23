package com.os0.navigation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import java.util.ArrayList;

public class camera_PreviewMediaActivity extends AppCompatActivity {
    ArrayList<camera_MediaObject> mediaObjects;

    private ImageView imgView;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.camera_activity_preview_media);
        imgView = (ImageView) findViewById(R.id.imgViewForDisplay);
        videoView = (VideoView) findViewById(R.id.videoViewForDisplay) ;
        mediaObjects = camera_Common.loadData(getApplicationContext());
        Intent intent = getIntent();
        int position = intent.getIntExtra("media object", -1);
        camera_MediaObject obj = mediaObjects.get(position);
        displayMedia(obj);
    }

    private void displayMedia(camera_MediaObject mediaObject) {
        if(mediaObject.getIsImage()) {
            imgView.setVisibility(View.VISIBLE);
            videoView.setVisibility(View.GONE);
            imgView.setImageURI(Uri.parse(mediaObject.getMediaFilePath()));
        } else {
            imgView.setVisibility(View.GONE);
            videoView.setVisibility(View.VISIBLE);
            videoView.setVideoURI(Uri.parse(mediaObject.getMediaFilePath()));
            videoView.start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}