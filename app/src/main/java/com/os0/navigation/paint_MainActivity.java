package com.os0.navigation;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.google.gson.Gson;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.slider.RangeSlider;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;


public class paint_MainActivity extends AppCompatActivity {

    private paint_DrawView paint;

    ImageButton save, color, undo, redo, clear;

   // private RangeSlider rangeSlider;

    paint_MediaList list = new paint_MediaList();

    EditText saveNameEt;
    EditText hexEt;
    Button saveBtn;
    Button cancelBtn;
    ImageButton close;
    RadioButton radiobutton;
    Button backColor;
    RadioButton black, white, gray, red, green, blue, yellow, pink, orange;
    /*
    RadioButton black;
    RadioButton white;
    RadioButton gray;
    RadioButton red;
    RadioButton green;
    RadioButton blue;
    RadioButton yellow;
    RadioButton pink;
    RadioButton orange;
*/
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      //  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paint_activity_main);

        paint = (paint_DrawView) findViewById(R.id.draw_view);
        undo = (ImageButton) findViewById(R.id.btn_undo);
        save = (ImageButton) findViewById(R.id.btn_save);
        color = (ImageButton) findViewById(R.id.btn_color);
        redo = (ImageButton) findViewById(R.id.btn_redo);
        clear = (ImageButton) findViewById(R.id.btn_clear);
        close = (ImageButton) findViewById(R.id.btn_close);


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        clear.setOnClickListener(view -> paint.eraseAll());

        redo.setOnClickListener(view -> paint.redo());

        undo.setOnClickListener(view -> paint.undo());

        save.setOnClickListener(view -> {


            final  Dialog dialog  = new Dialog(context);

            //tell the Dialog to use the dialog.xml as it's layout description
            dialog.setContentView(R.layout.dialog_save_paint);
            dialog.setTitle("Save image");

            dialog.show();

            saveNameEt = (EditText)dialog.findViewById(R.id.save_name_paint) ;
            saveBtn = (Button)dialog.findViewById(R.id.saveBtnPaint);
            cancelBtn = (Button)dialog.findViewById(R.id.cancelBtnPaint);




            cancelBtn.setOnClickListener(v -> dialog.dismiss());

            saveBtn.setOnClickListener(v -> {
                //no.animate().scaleX(1.1f).scaleY(1.1f).setDuration(200).withEndAction(() -> no.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200));
                String filename = saveNameEt.getText().toString();
                if(!filename.endsWith(".png")){
                    filename += ".png";
                }


                Bitmap bmp = paint.save();
                OutputStream imageOutStream = null;
                ContentValues cv = new ContentValues();

                cv.put(MediaStore.Images.Media.DISPLAY_NAME, filename);
                cv.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
                cv.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);

                Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv);

                paint_MediaObject media = new paint_MediaObject(uri.toString(), true);
                list.add(media);
                saveData();

                try {

                    imageOutStream = getContentResolver().openOutputStream(uri);

                    bmp.compress(Bitmap.CompressFormat.PNG, 100, imageOutStream);

                    imageOutStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                dialog.dismiss();
            });

        });

        color.setOnClickListener(view -> {

          final  Dialog dialog  = new Dialog(context);

            dialog.setContentView(R.layout.dialog_color_paint);
            dialog.setTitle("Select Colour");
            dialog.show();

            backColor = dialog.findViewById(R.id.backBtnPaint);
           // rangeSlider = (RangeSlider) dialog.findViewById(R.id.rangebar);
                hexEt = (EditText) dialog.findViewById(R.id.hex_paint);
             black = dialog.findViewById(R.id.btnBlackPaint);
             white= dialog.findViewById(R.id.btnWhitePaint);
             gray = dialog.findViewById(R.id.btnGrayPaint);
            red = dialog.findViewById(R.id.btnRedPaint);
            green= dialog.findViewById(R.id.btnGreenPaint);
            blue= dialog.findViewById(R.id.btnBluePaint);
             yellow= dialog.findViewById(R.id.btnYellowPaint);
             pink= dialog.findViewById(R.id.btnPinkPaint);
             orange = dialog.findViewById(R.id.btnOrangePaint);

//            if (rangeSlider.getVisibility() == View.VISIBLE)
//                rangeSlider.setVisibility(View.GONE);
//            else
//                rangeSlider.setVisibility(View.VISIBLE);

            ArrayList<RadioButton> colorButtons = new ArrayList<>(
                    Arrays.asList(black, white, gray, red, green, blue,yellow ,pink, orange)
            );
            int currentColor = paint.getColor();
            for (RadioButton cb: colorButtons) {
                int btnClr=Color.parseColor(cb.getTag().toString());
                if(btnClr == currentColor){
                    cb.setChecked(true);
                    hexEt.setText(cb.getTag().toString());
                    hexEt.setBackgroundTintList(ColorStateList.valueOf(btnClr));
                }
            }


//            rangeSlider.setValueFrom(0.0f);
//            rangeSlider.setValueTo(100.0f);
//            rangeSlider.setStepSize(1);
//
//            rangeSlider.addOnChangeListener((slider, value, fromUser) -> paint.setStrokeWidth((int) value));

            hexEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {}

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    if(s.length() == 7 && s.toString().startsWith("#")){

                        try{
                            int hexColor = Color.parseColor(s.toString());
                            hexEt.setBackgroundTintList(ColorStateList.valueOf(hexColor));
                            paint.setColor(hexColor);

                        }catch (IllegalArgumentException iae){}

                    }

                }

            });

            View.OnClickListener colorClickListener= v -> {
                radiobutton = dialog.findViewById(v.getId());

                for (RadioButton cb: colorButtons) {
                    if(cb != radiobutton){
                        cb.setChecked(false);
                    }
                }

                String colorHex = radiobutton.getTag().toString();
                hexEt.setText(colorHex);
                hexEt.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(colorHex)));

                paint.setColor(Color.parseColor(colorHex));

            };

            for (Button cb: colorButtons) {
                cb.setOnClickListener(colorClickListener);
            }

           backColor.setOnClickListener(v -> dialog.dismiss());

        });


        ViewTreeObserver vto = paint.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                paint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width = paint.getMeasuredWidth();
                int height = paint.getMeasuredHeight();
                paint.init(height, width);
            }
        });
    }



    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("mediaPreference", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("mediaList", json);
        editor.apply();
    }

    private void loadData() {

/*

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("taskList", null);

        try
        {
            list = gson.fromJson(json, MediaList.class);
            if(list == null){
                list = new MediaList();
            }
            else {
                for (Memo r : list) {
                    refreshList(r);
                }
            }

        }
        catch (IllegalStateException | JsonSyntaxException exception) {

        }
        */

    }



}