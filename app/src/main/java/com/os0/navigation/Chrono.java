package com.os0.navigation;

import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

class BetonTimer extends CountDownTimer {

    public BetonTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long l) {

    }

    @Override
    public void onFinish() {

    }

    public void onStart() {

    }
}

public class Chrono extends AppCompatActivity {

    boolean cdtRunningChronic;
    ChronicAdapter caChronic;
    ArrayList<String> arrayChronic;
    ListView listChronic;

    SharedPreferences spChronic;
    SharedPreferences.Editor editorChronic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chrono);

        arrayChronic = new ArrayList<String>();

        loadDataChronic();

        listChronic = (ListView) findViewById(R.id.listChronic);
        caChronic = new ChronicAdapter(Chrono.this, arrayChronic);
        listChronic.setAdapter(caChronic);

        Button btnClockChronic = (Button) findViewById(R.id.btnClockChronic);
        TextView hoursTChronic = (TextView) findViewById(R.id.hoursChronic);
        TextView minutesTChronic = (TextView) findViewById(R.id.minutesChronic);
        TextView secondsTChronic = (TextView) findViewById(R.id.secondsChronic);
        TextView millisTChronic = (TextView) findViewById(R.id.millisChronic);
        Button ssbtnChronic = (Button) findViewById(R.id.ssbtnChronic);
        Button wipeChronic = (Button) findViewById(R.id.wipeBtnChronic);

        hoursTChronic.setText("00");
        minutesTChronic.setText("00");
        secondsTChronic.setText("00");
        millisTChronic.setText("000");

        btnClockChronic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        BetonTimer cdttChronic = new BetonTimer(900000000, 1) {
            int hourChronic = 0, minuteChronic = 0, secondChronic = 0, milliChronic = 0;
            Calendar cc1Chronic = Calendar.getInstance();
            Calendar cc2Chronic = Calendar.getInstance();
            public void onStart(){
                hourChronic = 0;
                minuteChronic = 0;
                secondChronic = 0;
                milliChronic = 0;
                cc1Chronic = Calendar.getInstance();
                cc2Chronic = Calendar.getInstance();
            }
            @Override
            public void onTick(long l) {
                cc2Chronic = Calendar.getInstance();
                milliChronic = (int)(cc2Chronic.getTimeInMillis() - cc1Chronic.getTimeInMillis());
                secondChronic = milliChronic / 1000;
                minuteChronic = secondChronic / 60;
                hourChronic = minuteChronic / 60;
                millisTChronic.setText(String.format("%03d", milliChronic %1000) + "");
                secondsTChronic.setText(String.format("%02d", secondChronic %60) + "");
                minutesTChronic.setText(String.format("%02d", minuteChronic %60) + "");
                hoursTChronic.setText(String.format("%02d", hourChronic %60) + "");
                System.out.println(String.format("%02d", hourChronic %60) + ":" + String.format("%02d", minuteChronic %60) + ":" + String.format("%02d", secondChronic %60) + ":" + (String.format("%03d", milliChronic %1000)));
            }

            @Override
            public void onFinish() {
                cc1Chronic = null;
                cc2Chronic = null;
                arrayChronic.add(String.format("%02d", hourChronic %60) + ":" + String.format("%02d", minuteChronic %60) + ":" + String.format("%02d", secondChronic %60) + ":" + (String.format("%03d", milliChronic %1000)));
                saveDataChronic();
                secondChronic = 0;
                minuteChronic = 0;
                hourChronic = 0;
                milliChronic = 0;
                millisTChronic.setText(String.format("%03d", milliChronic %1000) + "");
                secondsTChronic.setText(String.format("%02d", secondChronic %60) + "");
                minutesTChronic.setText(String.format("%02d", minuteChronic %60) + "");
                hoursTChronic.setText(String.format("%02d", hourChronic %60) + "");
            }
        };

        cdtRunningChronic = false;

        ssbtnChronic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!cdtRunningChronic) {
                    cdttChronic.start();
                    cdttChronic.onStart();
                    cdtRunningChronic = true;
                }
                else {
                    cdttChronic.cancel();
                    cdttChronic.onFinish();
                    cdtRunningChronic = false;
                    caChronic = new ChronicAdapter(Chrono.this, arrayChronic);
                    listChronic.setAdapter(caChronic);
                }
            }
        });

        wipeChronic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wipeDataChronic();
            }
        });

    }

    private void loadDataChronic(){
        spChronic = getSharedPreferences("Chrono", MODE_PRIVATE);
        // creating a variable for gsonChronic.
        Gson gsonChronic = new Gson();

        // below line is to get to string present from our
        // shared prefs if not present setting it as null.
        String jsonChronic = spChronic.getString("chronicHistory", null);

        // below line is to get the type of our array list.
        Type typeChronic = new TypeToken<ArrayList<String>>() {}.getType();

        // in below line we are getting data from gsonChronic
        // and saving it to our array list
        arrayChronic = gsonChronic.fromJson(jsonChronic, typeChronic);

        // checking below if the array list is empty or not
        if (arrayChronic == null) {
            // if the array list is empty
            // creating a new array list.
            arrayChronic = new ArrayList<>();
        }
    }

    private void saveDataChronic() {
        spChronic = getSharedPreferences("Chrono", MODE_PRIVATE);

        editorChronic = spChronic.edit();

        Gson gsonChronic = new Gson();

        String jsonChronic = gsonChronic.toJson(arrayChronic);

        editorChronic.putString("chronicHistory", jsonChronic);

        editorChronic.apply();

    }

    private void wipeDataChronic(){
        spChronic = getSharedPreferences("Chrono", MODE_PRIVATE);

        editorChronic = spChronic.edit();

        arrayChronic.clear();
        saveDataChronic();
        loadDataChronic();
        caChronic = new ChronicAdapter(Chrono.this, arrayChronic);
        listChronic.setAdapter(caChronic);
    }
}