package com.os0.navigation;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonSyntaxException;
import com.os0.navigation.models.MemoList;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    float x1, x2, y1, y2;
    TextView memotv;
    TextView schedtv;
    TextView hourNav, minuteNav;
    SharedPreferences spChronic;
    SharedPreferences.Editor editorChronic;
    private long mssChronic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        memotv = findViewById(R.id.memorytxt);
        schedtv = findViewById(R.id.scheduler);

        // ======== Memo ========
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("taskList", null);
        MemoList list = gson.fromJson(json, MemoList.class);
        if (list == null || list.isEmpty()) {
            memotv.setText("Memory");
        } else {
            memotv.setText(list.get(0).tvText);
        }

        // ======== Schedule ========
        SharedPreferences sp = getSharedPreferences("schedule shared preferences", MODE_PRIVATE);
        Gson gson2 = new Gson();
        Type type = new TypeToken<ArrayList<ScheduleItem>>() {}.getType();
        ArrayList<ScheduleItem> items = null;
        if (sp.contains("schedule list")) {
            String json2 = sp.getString("schedule list", null);
            items = gson2.fromJson(json2, type);
        }
        if (items == null || items.isEmpty()) {
            schedtv.setText("No schedules");
        } else {
            schedtv.setText(items.get(0).toString());
        }

        // ======== Clock Navigation ========
        hourNav = findViewById(R.id.hourNav);
        minuteNav = findViewById(R.id.minuteNav);
        spChronic = getSharedPreferences("Clock", MODE_PRIVATE);
        editorChronic = spChronic.edit();
        mssChronic = spChronic.getLong("mss", 0);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(Calendar.getInstance().getTimeInMillis() - mssChronic);
        hourNav.setText(String.format("%02d", c.get(Calendar.HOUR_OF_DAY)));
        minuteNav.setText(String.format("%02d", c.get(Calendar.MINUTE)));
        CustomAnalogClock AnalClock = findViewById(R.id.AnalClock);
        AnalClock.setDiff(
                c.get(Calendar.HOUR_OF_DAY) - Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE) - Calendar.getInstance().get(Calendar.MINUTE),
                false
        );
        CountDownTimer cdt = new CountDownTimer(9000000, 1000) {
            @Override
            public void onTick(long l) {
                mssChronic = spChronic.getLong("mss", 0);
                c.setTimeInMillis(Calendar.getInstance().getTimeInMillis() - mssChronic);
                hourNav.setText(String.format("%02d", c.get(Calendar.HOUR_OF_DAY)));
                minuteNav.setText(String.format("%02d", c.get(Calendar.MINUTE)));
                AnalClock.setDiff(
                        c.get(Calendar.HOUR_OF_DAY) - Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                        c.get(Calendar.MINUTE) - Calendar.getInstance().get(Calendar.MINUTE),
                        false
                );
            }
            @Override
            public void onFinish() {}
        };
        cdt.start();

        // ======== Logout ========
        Button logoutBtn = findViewById(R.id.logoutBUTT);
        logoutBtn.setOnClickListener(v -> {
            SharedPreferences prefBoolean = getApplicationContext()
                    .getSharedPreferences("BooleanPref", MODE_PRIVATE);
            prefBoolean.edit().putBoolean("isLoggedIn", false).apply();
            startActivity(new Intent(getApplicationContext(), LogInActivity.class));
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        memotv = findViewById(R.id.memorytxt);
        schedtv = findViewById(R.id.scheduler);

        // ======== Memo ========
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("taskList", null);
        MemoList list = gson.fromJson(json, MemoList.class);
        if (list == null || list.isEmpty()) {
            memotv.setText("Memory");
        } else {
            memotv.setText(list.get(0).tvText);
        }

        // ======== Schedule ========
        SharedPreferences sp = getSharedPreferences("schedule shared preferences", MODE_PRIVATE);
        Gson gson2 = new Gson();
        Type type = new TypeToken<ArrayList<ScheduleItem>>() {}.getType();
        ArrayList<ScheduleItem> items = null;
        if (sp.contains("schedule list")) {
            String json2 = sp.getString("schedule list", null);
            items = gson2.fromJson(json2, type);
        }
        if (items == null || items.isEmpty()) {
            schedtv.setText("No schedules");
        } else {
            schedtv.setText(items.get(0).toString());
        }
    }

    @Override
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
                    startActivity(new Intent(MainActivity.this, Navigation.class));
                }
                break;
        }
        return false;
    }
}