package com.os0.navigation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Calendar;

public class web_BrowserMain extends AppCompatActivity {

    private Button menu;
    private Button history;
    private Button bookmarks;
    private boolean visible = false;
    private TextView stext;
    private Button searchbtn;
    private Button backOnSite;
    private Button forwardOnSite;
    private WebView wb;
    private web_HistoryItemList historyArr;
    private web_BookmarksItemList bookmarksArr;
    private Calendar currentTime;
    private String s;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_main_browser);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        menu = findViewById(R.id.btnMenuBrowser);
        history = findViewById(R.id.btnHistory);
        history.setVisibility(View.GONE);
        bookmarks = findViewById(R.id.btnBookmarks);
        bookmarks.setVisibility(View.GONE);
        stext = findViewById(R.id.txtURLAdress);
        searchbtn = findViewById(R.id.btnEnter);
        backOnSite = findViewById(R.id.btnSiteBefore);
        forwardOnSite = findViewById(R.id.btnSiteForward);
        wb = findViewById(R.id.webview);
        wb.setWebViewClient(new WebViewClient() {
        });
        WebSettings webSettings = wb.getSettings();
        webSettings.setJavaScriptEnabled(true);

        SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                        wb.reload();
                    }
                }, 3000);
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!visible) {
                    history.setVisibility(View.VISIBLE);
                    bookmarks.setVisibility(View.VISIBLE);
                    visible = true;
                } else {
                    history.setVisibility((View.GONE));
                    bookmarks.setVisibility(View.GONE);
                    visible = false;
                }
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                visitHistory();
            }
        });

        bookmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                visitBookmarks();
            }
        });

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPage();
            }
        });

        stext.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    loadPage();
                    return true;
                }
                return false;
            }
        });

        backOnSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (wb.canGoBack()) wb.goBack();
            }
        });

        forwardOnSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (wb.canGoForward()) wb.goForward();
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER: {
                loadPage();
                return true;
            }
            default:
                return super.onKeyDown(keyCode, event);
        }
    }

    private void visitHistory() {
        Intent intent = new Intent(this, web_History_activity.class);
        startActivity(intent);
    }

    private void visitBookmarks() {
        Intent intent = new Intent(this, web_Bookmarks_activity.class);
        startActivity(intent);
    }

    private void loadPage() {
        s = stext.getText().toString();
        if (s.length() > 15) {
            if (!s.substring(0, 12).equals("https://www.") || !s.substring(0, 11).equals("http://www."))
                s = "https://www." + s;
        } else s = "https://www." + s;
        if (Patterns.WEB_URL.matcher(s).matches()) {
            loadAndSaveData_H();
            wb.loadUrl(s);
        } else {
            wb.loadUrl("https://www.lifewire.com/how-to-troubleshoot-an-error-in-a-url-2624454");
            Toast.makeText(web_BrowserMain.this, "Oh no! Your url address appears to not have been found!", Toast.LENGTH_LONG).show();
        }

    }

    private void loadData_H() { //vzima info ot SP i go prevejda na ArrayList <HistoryItem> s pomoshta na json file
        SharedPreferences SP = getSharedPreferences("Browser_History", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = SP.getString("history", null);
        Type type = new TypeToken<web_HistoryItemList>() {
        }.getType();
        historyArr = gson.fromJson(json, type);

        if (historyArr == null) {
            historyArr = new web_HistoryItemList();
        }
    }

    private void saveData_H() { //vzima info ot ArrayList <HistoryItem> i go prevejda na SP s pomoshta na json file
        SharedPreferences SP = getSharedPreferences("Browser_History", MODE_PRIVATE);
        SharedPreferences.Editor editor = SP.edit();
        Gson gson = new Gson();
        String json = gson.toJson(historyArr);
        editor.putString("history", json);
        editor.apply();
    }

    private void loadAndSaveData_H() { //method za po-butzo deistvane + dobavqne na promenliva chas kum HistoryItem
        loadData_H();
        time();
        web_HistoryItem h = new web_HistoryItem(time, s);
        historyArr.add(0, h);
        saveData_H();
    }

    private void time() {
        currentTime = Calendar.getInstance();
        time = currentTime.get(Calendar.HOUR_OF_DAY) + ":" + currentTime.get(Calendar.MINUTE) + ":" + currentTime.get(Calendar.SECOND) + " "
                + currentTime.get(Calendar.DAY_OF_MONTH) + "." + (currentTime.get(Calendar.MONTH) + 1) + "." + currentTime.get(Calendar.YEAR);
    }

    private void loadData_B() {
        SharedPreferences SP = getSharedPreferences("Browser_Bookmarks", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = SP.getString("bookmarks", null);
        Type type = new TypeToken<web_BookmarksItemList>() {
        }.getType();
        bookmarksArr = gson.fromJson(json, type);
        if (bookmarksArr == null) bookmarksArr = new web_BookmarksItemList();
    }

    private void SaveData_B() {
        SharedPreferences SP = getSharedPreferences("Browser_Bookmarks", MODE_PRIVATE);
        SharedPreferences.Editor editor = SP.edit();
        Gson gson = new Gson();
        String json = gson.toJson(bookmarksArr);
        editor.putString("bookmarks", json);
        editor.apply();
    }

    private void loadAndSaveData_B() {
        loadData_B();
        String name = "";
        web_BookmarksItem b = new web_BookmarksItem(name, s);
        bookmarksArr.add(0, b);
        SaveData_B();
    }
}