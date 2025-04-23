package com.os0.navigation;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class web_Bookmarks_activity extends AppCompatActivity {
    private Button btnBAdd;
    private Button btnBBack;
    private web_BookmarksItemList bkm;
    private web_BrowserBookmarkAdapter bkmAdapter;
    private ListView listViewBookmarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_activity_bookmarks);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        loadData_B();
        listViewBookmarks = findViewById(R.id.listviewBookmarks);
        bkmAdapter = new web_BrowserBookmarkAdapter(this, bkm);
        listViewBookmarks.setAdapter(bkmAdapter);

        btnBAdd = findViewById(R.id.btnBAdd);
        btnBAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialogAdd();
            }
        });

        btnBBack = findViewById(R.id.btnBBack);
        btnBBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(web_Bookmarks_activity.this, web_BrowserMain.class);
                startActivity(intent);
            }
        });

        ImageView btnBDel = findViewById(R.id.btnBDel);
        btnBDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDelAll();
            }
        });

    }

    private void showDialogDelAll() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.web_custom_dialog5);
        dialog.setCancelable(true);
        dialog.show();

        Button CbtnCD5 = dialog.findViewById(R.id.CbtnCD5);
        CbtnCD5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });

        Button YbtnCD = dialog.findViewById(R.id.YbtnCD5);
        YbtnCD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bkm.clear();
                SaveData_B();
                bkmAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
    }

    private void showDialogAdd() {

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.web_custom_dialog3);
        dialog.setCancelable(true);
        dialog.show();

        Button DbtnCD3 = dialog.findViewById(R.id.DbtnCD3);
        Button CbtnCD3 = dialog.findViewById(R.id.CbtnCD3);
        EditText name = dialog.findViewById(R.id.BnameD3);
        EditText url = dialog.findViewById(R.id.BurlD3);

        CbtnCD3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

        DbtnCD3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = url.getText().toString();
                if (s.length() >= 7) {
                    if (!s.substring(0, 4).equals("www.")) s = "www." + s;
                } else s = "www." + s;
                System.out.println(s + " " + name.getText().toString());
                if (Patterns.WEB_URL.matcher(s).matches()) {
                    if (name.getText().toString().equals("")) {
                        name.setText(s);
                    }
                    web_BookmarksItem b = new web_BookmarksItem(name.getText().toString(), s);
                    bkm.add(b);
                    SaveData_B();
                    bkmAdapter.notifyDataSetChanged();
                    dialog.dismiss();

                } else
                    Toast.makeText(web_Bookmarks_activity.this, "Oh no! It appears that this url address is not an existing one. Thus your bookmark hasn't been saved.", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void loadData_B() {
        SharedPreferences SP = getSharedPreferences("Browser_Bookmarks", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = SP.getString("bookmarks", null);
        Type type = new TypeToken<web_BookmarksItemList>() {
        }.getType();
        bkm = gson.fromJson(json, type);
        if (bkm == null) bkm = new web_BookmarksItemList();
    }

    private void SaveData_B() {
        SharedPreferences SP = getSharedPreferences("Browser_Bookmarks", MODE_PRIVATE);
        SharedPreferences.Editor editor = SP.edit();
        Gson gson = new Gson();
        String json = gson.toJson(bkm);
        editor.putString("bookmarks", json);
        editor.apply();
    }


}