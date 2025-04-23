package com.os0.navigation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;

import android.view.Gravity;

import android.view.View;

import android.widget.ImageButton;

import android.widget.TextView;
import android.widget.Button;
import android.widget.LinearLayout;


import java.util.Collection;


import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import com.os0.navigation.models.*;
import com.os0.navigation.models.Memo;


public class Memory extends AppCompatActivity implements AddDialogMemo.DialogListener{

    Button add;
    MemoList list;
    Button yes;
    Button no;
    Button back;

    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memo_activity_main);

        loadData();

        add = findViewById(R.id.addBtnMemo);
        back =  findViewById(R.id.backBtnMemo);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                add.animate().scaleX(1.1f).scaleY(1.1f).setDuration(200).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        add.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200);
                    }
                });

                openDialog();

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Memory.this,Navigation.class);
                startActivity(i);
            }
        });
    }


    View.OnLongClickListener myLongClickListener = new View.OnLongClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        // @Override
        public boolean onLongClick(View v) {
            // TODO Auto-generated method stub

            // create a Dialog component
            final Dialog dialog = new Dialog(context);

            //tell the Dialog to use the dialog.xml as it's layout description
            dialog.setContentView(R.layout.memo_dialog_ask);
            dialog.setTitle("Android Custom Dialog Box");

            yes = (Button)dialog.findViewById(R.id.yesBtnMemo);
            no = dialog.findViewById(R.id.noBtnMemo);


            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    yes.animate().scaleX(1.1f).scaleY(1.1f).setDuration(200).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            yes.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200);
                        }
                    });

                    removeItem(v.getId());
                    saveData();
                    loadData();
                    dialog.dismiss();
                }
            });

            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    no.animate().scaleX(1.1f).scaleY(1.1f).setDuration(200).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            no.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200);
                        }
                    });

                    dialog.dismiss();
                }
            });

            dialog.show();

            return true;
        }
    };


    View.OnClickListener myClickListener= new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        public void onClick(View v) {
            int id = v.getId();
            ImageButton btn;
            btn = findViewById(id);



            com.os0.navigation.models.Memo mm = findById(list, id );

            mm.finished = true;

            btn.setImageResource(R.drawable.memo_check);

            saveData();

            loadData();

        }
    };


    public void openDialog(){
        AddDialogMemo addDialog = new AddDialogMemo();
        addDialog.show(getSupportFragmentManager(), "add dialog");
    }

    public void insertItem(String note, boolean finished){
        Memo nm = new Memo(note, finished);
        list.add(nm);

        refreshList(nm);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void removeItem(int id){
        list.removeIf(m -> m.Id == id);
    }

    public void applyText(String note) {
        insertItem(note, false);

        saveData();
    }

    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("taskList", json);
        editor.apply();
    }

    private void loadData() {
        LinearLayout vertical = (LinearLayout) findViewById(R.id.verticalMemo);
        vertical.removeAllViews();

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("taskList", null);

        try
        {
            list = gson.fromJson(json, MemoList.class);
            if(list == null){
                list = new MemoList();
            }
            else {
                for (Memo r : list) {
                    refreshList(r);
                }
            }

        }
        catch (IllegalStateException | JsonSyntaxException exception) {

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Memo findById(Collection<Memo> list, int id){
        Memo mm = list.stream()
                .filter(memo -> id == memo.GetId())
                .findAny()
                .orElse(null);
        return mm;
    }

    public void refreshList(Memo nm) {
        LinearLayout vertical = (LinearLayout) findViewById(R.id.verticalMemo);

        LinearLayout horizontal = new LinearLayout(this);
        horizontal.setOrientation(LinearLayout.HORIZONTAL);

        ImageButton btn = new ImageButton(this);


        int id = View.generateViewId();
        btn.setId(id);
        nm.Id = id;

        final float scale = getResources().getDisplayMetrics().density;

        LinearLayout.LayoutParams lpb = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        lpb.gravity = Gravity.CENTER;

        lpb.setMargins((int) (10 * scale + 0.5f),0,0,0);

        btn.setOnClickListener(myClickListener);
        btn.setOnLongClickListener(myLongClickListener);
        btn.setBackgroundColor(Color.TRANSPARENT);
        btn.setLayoutParams(lpb);

        TextView tv = new TextView(this);
        tv.setText(nm.tvText);

        // Defining the layout parameters of the TextView
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        int pixelsMargin = (int) (10 * scale + 0.5f);
        int pixelsMarginL = (int) (20 * scale + 0.5f);
        int pixelsMinH = (int) (50 * scale + 0.5f);
        lp.setMargins(pixelsMarginL, pixelsMargin, 0, pixelsMargin);


        int pixelsPadding = (int) (5 * scale + 0.5f);
        tv.setPadding(pixelsPadding, pixelsPadding, pixelsPadding, pixelsPadding);

        int pixelsW = (int) (270 * scale + 0.5f);

        tv.setWidth(pixelsW);
        tv.setMinHeight(pixelsMinH);
        tv.setGravity(Gravity.CENTER);

        tv.setBackground(ContextCompat.getDrawable(Memory.this, R.drawable.round));

        if(nm.finished == true){

            tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            btn.setImageResource(R.drawable.memo_check);
            btn.setColorFilter(Color.WHITE);
            //    btn.setColorFilter(ColorUtils.blendARGB(Color.parseColor("#FFFFFF"), Color.parseColor("#CCCCCC"), 0.5F));
        }
        else{
            tv.setTextColor(Color.BLACK);
            btn.setImageResource(R.drawable.memo_star);

        }


        // Setting the parameters on the TextView
        tv.setLayoutParams(lp);


        horizontal.addView(tv);
        horizontal.addView(btn);

        vertical.addView(horizontal);
    }

}