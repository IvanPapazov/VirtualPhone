package com.os0.navigation;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class Clock extends AppCompatActivity {

    private long mssChronic;
    Calendar calendarChronic;
    CustomAnalogClock clockChronic;
    SharedPreferences spChronic;
    SharedPreferences.Editor editorChronic;
    boolean pickedChronic = false;

    private int mMinuteChronic, mHourChronic;
    private int mYearChronic, mMonthChronic, mDayChronic;
    private void timePickerChronic(){
        final Calendar cChronic = Calendar.getInstance();
        mHourChronic = cChronic.get(Calendar.HOUR_OF_DAY);
        mMinuteChronic = cChronic.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialogChronic = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker viewChronic, int hourOfDayChronic, int minuteChronic) {
                        mHourChronic = hourOfDayChronic;
                        mMinuteChronic = minuteChronic;
                        int newHourChronic = mHourChronic;
                        int newMinuteChronic = mMinuteChronic;
                        calendarChronic = Calendar.getInstance();
                        int hourChronic = calendarChronic.get(Calendar.HOUR_OF_DAY);
                        int minutEChronic = calendarChronic.get(Calendar.MINUTE);
                        int hourDiffChronic = newHourChronic - hourChronic;
                        int minuteDiffChronic = newMinuteChronic - minutEChronic;
                        clockChronic.setDiff(hourDiffChronic, minuteDiffChronic, true);
                        calendarChronic.set(mYearChronic, mMonthChronic -1, mDayChronic, newHourChronic, newMinuteChronic, Calendar.getInstance().get(Calendar.SECOND));
                        //System.out.println(mYearChronic + " " + mMonthChronic + " " + mDayChronic + " " + newHourChronic + " " + newMinuteChronic + " " + calendarChronic.get(Calendar.SECOND));
                        //System.out.println(calendarChronic.getTime());
                        //System.out.println(Calendar.getInstance().getTime());
                        mssChronic = Calendar.getInstance().getTimeInMillis() - calendarChronic.getTimeInMillis();
                        editorChronic.putLong("mss", mssChronic);
                        editorChronic.apply();
                        pickedChronic = true;
                    }
                }, mHourChronic, mMinuteChronic, false);

        timePickerDialogChronic.show();
    }

    private void datePickerChronic(){
        // Get Current Date
        final Calendar cChronic = Calendar.getInstance();
        mYearChronic = cChronic.get(Calendar.YEAR);
        mMonthChronic = cChronic.get(Calendar.MONTH);
        mDayChronic = cChronic.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialogChronic = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker viewChronic, int yearChronic, int monthOfYearChronic, int dayOfMonthChronic) {
                        mYearChronic = yearChronic;
                        mMonthChronic = monthOfYearChronic + 1;
                        mDayChronic = dayOfMonthChronic;
                        timePickerChronic();
                    }
                }, mYearChronic, mMonthChronic, mDayChronic);
        datePickerDialogChronic.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);

        calendarChronic = Calendar.getInstance();
        Calendar calendarActualChronic = Calendar.getInstance();

        spChronic = getSharedPreferences("Clock", MODE_PRIVATE);
        editorChronic = spChronic.edit();
        mssChronic = spChronic.getLong("mss", 0);

        calendarChronic.setTimeInMillis(calendarActualChronic.getTimeInMillis() - mssChronic);

        clockChronic = (CustomAnalogClock) findViewById(R.id.clockAChronic);
        EditText hour1Chronic = (EditText) findViewById(R.id.hour1Chronic);
        EditText minute1Chronic = (EditText) findViewById(R.id.minute1Chronic);
        Button setTimeChronic = (Button) findViewById(R.id.setTimeChronic);

        //System.out.println(calendarChronic.getTime());

        int secondChronic = calendarActualChronic.get(Calendar.SECOND);
        int hourChronic = calendarChronic.get(Calendar.HOUR_OF_DAY);
        int minuteChronic = calendarChronic.get(Calendar.MINUTE);
        int dayChronic = calendarChronic.get(Calendar.DAY_OF_MONTH);
        int monthChronic = calendarChronic.get(Calendar.MONTH)+1;
        int yearChronic = calendarChronic.get(Calendar.YEAR);
        calendarChronic.set(yearChronic, monthChronic, dayChronic, hourChronic, minuteChronic, secondChronic);
        editorChronic.putLong("mss", calendarActualChronic.getTimeInMillis() - calendarChronic.getTimeInMillis());
        hour1Chronic.setText(String.valueOf(hourChronic));
        minute1Chronic.setText(String.format("%02d", minuteChronic));

        EditText day1Chronic = (EditText) findViewById(R.id.day1Chronic);
        EditText month1Chronic = (EditText) findViewById(R.id.month1Chronic);
        EditText year1Chronic = (EditText) findViewById(R.id.year1Chronic);

        day1Chronic.setText(String.valueOf(dayChronic));
        month1Chronic.setText(String.valueOf(monthChronic));
        year1Chronic.setText(String.valueOf(yearChronic));

        clockChronic.setDiff(hourChronic - calendarActualChronic.get(Calendar.HOUR_OF_DAY), minuteChronic - calendarActualChronic.get(Calendar.MINUTE), true);

        Button btnChronoChronic = (Button) findViewById(R.id.btnChronoChronic);

        btnChronoChronic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent theChronic = new Intent(getApplicationContext(), Chrono.class);
                startActivity(theChronic);
            }
        });

        CountDownTimer cdtChronic = new CountDownTimer(900000000, 1000) {

            @Override
            public void onTick(long l) {
                //System.out.println(l);
                calendarChronic.setTimeInMillis(Calendar.getInstance().getTimeInMillis() - mssChronic);
                int hourChronic = calendarChronic.get(Calendar.HOUR_OF_DAY);
                int minuteChronic = calendarChronic.get(Calendar.MINUTE);
                int dayChronic = calendarChronic.get(Calendar.DAY_OF_MONTH);
                int monthChronic = calendarChronic.get(Calendar.MONTH)+1;
                int yearChronic = calendarChronic.get(Calendar.YEAR);
                int secondChronic = calendarChronic.get(Calendar.SECOND);
                //System.out.println(secondChronic + " " + hourChronic + " " + minuteChronic + " " + dayChronic + " " + monthChronic + " " + yearChronic);
                if (secondChronic == 0 || pickedChronic){
                    minute1Chronic.setText(String.format("%02d", minuteChronic) + "");
                    hour1Chronic.setText(String.valueOf(hourChronic));
                    day1Chronic.setText(String.valueOf(dayChronic));
                    month1Chronic.setText(String.valueOf(monthChronic));
                    year1Chronic.setText(String.valueOf(yearChronic));
                    pickedChronic = false;
                }
            }

            @Override
            public void onFinish() {

            }
        };

        cdtChronic.start();

        setTimeChronic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newHourChronic = Integer.parseInt(String.valueOf(hour1Chronic.getText()));
                int newMinuteChronic = Integer.parseInt(String.valueOf(minute1Chronic.getText()));
                Calendar calendarChronic = Calendar.getInstance();
                int hourChronic = calendarChronic.get(Calendar.HOUR_OF_DAY);
                int minuteChronic = calendarChronic.get(Calendar.MINUTE);
                int hourDiffChronic = newHourChronic - hourChronic;
                int minuteDiffChronic = newMinuteChronic - minuteChronic;
                clockChronic.setDiff(hourDiffChronic, minuteDiffChronic, true);
                calendarChronic.set(Integer.parseInt(String.valueOf(year1Chronic.getText())), Integer.parseInt(String.valueOf(month1Chronic.getText()))-1, Integer.parseInt(String.valueOf(day1Chronic.getText())), newHourChronic, newMinuteChronic, calendarActualChronic.get(Calendar.SECOND));
                //System.out.println(Integer.parseInt(String.valueOf(year1Chronic.getText())) + " " + Integer.parseInt(String.valueOf(month1Chronic.getText())) + " " + Integer.parseInt(String.valueOf(day1Chronic.getText())) + " " + newHourChronic + " " + newMinuteChronic + " " + calendarChronic.get(Calendar.SECOND));
                //System.out.println(calendarChronic.getTime());
                //System.out.println(calendarActualChronic.getTime());
                mssChronic = calendarActualChronic.getTimeInMillis() - calendarChronic.getTimeInMillis();
                editorChronic.putLong("mss", mssChronic);
                editorChronic.apply();
            }
        });

        clockChronic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerChronic();
                //System.out.println(mMonthChronic + " " + mYearChronic + " " + mDayChronic + " " + mHourChronic + " " + mMinuteChronic);
            }
        });

    }
}