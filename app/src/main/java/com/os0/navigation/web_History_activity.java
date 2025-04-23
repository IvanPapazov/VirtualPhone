package com.os0.navigation;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

public class web_History_activity extends AppCompatActivity {

    private ImageButton btnHDel;
    private web_HistoryItemList hst;
    private ListView listViewHistory;
    private web_BrowserHistoryAdapter brAdapter;
    private ImageView btnRowH;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_activity_history);


        loadData_H();
        listViewHistory = findViewById(R.id.listviewHistory);
        brAdapter = new web_BrowserHistoryAdapter(this, hst);
        listViewHistory.setAdapter(brAdapter);


        Dialog dialog = new Dialog(web_History_activity.this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        btnHDel = findViewById(R.id.btnHDel);
        btnHDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();


            }
        });


        Button btnHBack = findViewById(R.id.btnHBack);
        btnHBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(web_History_activity.this, web_BrowserMain.class);
                startActivity(intent);
            }
        });
    }

    private void showDialogDel1() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.web_custom_dialog2);
        dialog.setCancelable(true);

        Button YbtnCD2 = dialog.findViewById(R.id.YbtnCD2);
        YbtnCD2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hst.clear();
                saveData_H();
                brAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
    }

    private void showDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.web_custom_dialog);
        dialog.setCancelable(true);

        Button YbtnCD = dialog.findViewById(R.id.YbtnCD);
        YbtnCD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hst.clear();
                saveData_H();
                brAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });


        Button CbtnCD = dialog.findViewById(R.id.CbtnCD);
        CbtnCD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void loadData_H() { //vzima info ot SP i go prevejda na ArrayList <HistoryItem> s pomoshta na json file
        SharedPreferences SP = getSharedPreferences("Browser_History", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = SP.getString("history", null);
        hst = gson.fromJson(json, web_HistoryItemList.class);

        if (hst == null) {
            hst = new web_HistoryItemList();
        }
    }

    private void saveData_H() { //vzima info ot ArrayList <HistoryItem> i go prevejda na SP s pomoshta na json file
        SharedPreferences SP = getSharedPreferences("Browser_History", MODE_PRIVATE);
        SharedPreferences.Editor editor = SP.edit();
        Gson gson = new Gson();
        String json = gson.toJson(hst);
        editor.putString("history", json);
        editor.apply();
    }


}
