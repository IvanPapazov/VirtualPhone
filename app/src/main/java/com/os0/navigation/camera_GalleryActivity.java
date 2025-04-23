package com.os0.navigation;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class camera_GalleryActivity extends Activity {

    private ListView listView;
    private ArrayList<camera_MediaObject> mediaObjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.camera_activity_gallery);
        mediaObjects =  camera_Common.loadData(this);
        listView = findViewById(R.id.list);
        camera_MediaObjectAdapter mediaObjectAdapter = new camera_MediaObjectAdapter(this, extractFilePaths(), extractMediaIndicators());
        listView.setAdapter(mediaObjectAdapter);
        onItemClicked();
    }

    private ArrayList<String> extractFilePaths() {
        ArrayList<camera_MediaObject> mediaObjects = camera_Common.loadData(getApplicationContext());
        ArrayList<String> filePaths = new ArrayList<String>(mediaObjects.size());
        for (int i = 0; i < mediaObjects.size(); i++) {
            filePaths.add(mediaObjects.get(i).getMediaFilePath());
        }
        return filePaths;
    }

    private ArrayList<Boolean> extractMediaIndicators() {
        ArrayList<camera_MediaObject> mediaObjects = camera_Common.loadData(getApplicationContext());
        ArrayList<Boolean> mediaIndicators = new ArrayList<Boolean>(mediaObjects.size());
        for (int i = 0; i < mediaObjects.size(); i++) {
           mediaIndicators.add(mediaObjects.get(i).getIsImage());
        }
        return  mediaIndicators;
    }

    private void onItemClicked() {
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(camera_GalleryActivity.this, camera_PreviewMediaActivity.class);
                intent.putExtra("media object", i);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaObjects = camera_Common.loadData(this);
        camera_Common.saveData(this, mediaObjects);
    }
}