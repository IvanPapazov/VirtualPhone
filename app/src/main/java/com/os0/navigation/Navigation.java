package com.os0.navigation;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Button;

public class Navigation extends AppCompatActivity {

    float x1, x2, y1, y2;

    Button Bchat;
    Button Bphone;
    Button Bvideo;
    Button Bcamera;
    Button Bgallery;
    Button Bbrowser;
    Button Bshed;
    Button Breminder;
    Button Bcalculator;
    Button Bclock;
    Button Bcalendar;
    Button BTicTackToe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        Bchat = findViewById(R.id.chat);
        Bphone = findViewById(R.id.phone);
        Bvideo = findViewById(R.id.video);
        Bcamera = findViewById(R.id.camera);
        Bgallery = findViewById(R.id.gallery);
        Bbrowser = findViewById(R.id.browser);
        Bshed = findViewById(R.id.shed);
        Breminder = findViewById(R.id.reminder);
        Bcalculator = findViewById(R.id.calculator);
        Bclock = findViewById(R.id.clock);
        Bcalendar = findViewById(R.id.calendar);
        BTicTackToe = findViewById(R.id.TicTackToe);

        Bchat.setOnClickListener(view -> {
            Intent i = new Intent(Navigation.this, Messenger.class);
            startActivity(i);
        });

        Bphone.setOnClickListener(view -> {
            Intent i = new Intent(Navigation.this, paint_MainActivity.class);
            startActivity(i);
        });

        Bvideo.setOnClickListener(view -> {
            Intent i = new Intent(Navigation.this, YouTube_MainAct.class);
            startActivity(i);
        });

        Bcamera.setOnClickListener(view -> {
            Intent i = new Intent(Navigation.this, camera_MainActivity.class);
            startActivity(i);
        });

        Bgallery.setOnClickListener(view -> {
            Intent i = new Intent(Navigation.this, camera_GalleryActivity.class);
            startActivity(i);
        });

        Bbrowser.setOnClickListener(view -> {
            Intent i = new Intent(Navigation.this, web_BrowserMain.class);
            startActivity(i);
        });

        Bshed.setOnClickListener(view -> {
            Intent i = new Intent(Navigation.this, ContactsActivityMain.class);
            startActivity(i);
        });

        Breminder.setOnClickListener(view -> {
            Intent i = new Intent(Navigation.this, Memory.class);
            startActivity(i);
        });

        Bcalculator.setOnClickListener(view -> {
            Intent i = new Intent(Navigation.this, calc_MainActivity.class);
            startActivity(i);
        });

        Bclock.setOnClickListener(view -> {
            Intent i = new Intent(Navigation.this, Clock.class);
            startActivity(i);
        });

        Bcalendar.setOnClickListener(view -> {
            Intent i = new Intent(Navigation.this, SchedulerMainActivity.class);
            startActivity(i);
        });

        BTicTackToe.setOnClickListener(view -> {
            Intent i = new Intent(Navigation.this, TicTakToe.class);
            startActivity(i);
        });
    }

    public boolean onTouchEvent(MotionEvent touchEvent) {
        switch (touchEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if (x1 > x2) {
                    Intent i = new Intent(Navigation.this, MainActivity.class);
                    startActivity(i);
                }
                break;
        }
        return false;
    }

}