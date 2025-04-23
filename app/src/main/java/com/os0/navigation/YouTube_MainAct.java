package com.os0.navigation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


 /*class Video{
    private String url;
    private String thumbnail;

    public Video(String u,String t){
        url = u;
        thumbnail = t;
    }

    public String getUrl(){
        return url;
    }

    public String getThumbnail(){
        return thumbnail;
    }
}

class VideoList {
     ArrayList<Video> list = new ArrayList<>();
     public void add(String u,String t){
         list.add(new Video(u,t));
     }

     public int size(){
         return list.size();
     }
}*/

public class YouTube_MainAct extends AppCompatActivity {
    Button add;
    LinearLayout linearLayout;
    private String url;
    private ImageView image;
    private LinearLayout layout;
    private ArrayList<String> urls;
    private ArrayList<String> thumbnails;
    private View.OnLongClickListener listener;

    private void loadDataYoutube() {
        SharedPreferences sp = getSharedPreferences("youtubeplayer shared preference", MODE_PRIVATE);
        Gson gson = new Gson();

        String json = sp.getString("urlsItems", null);
        String json2 = sp.getString("usItems", null);
        Type tp = new TypeToken<ArrayList<String>>() {
        }.getType();
        if (json != null && !json.equals("") && json2 != null && !json2.equals("")) {
            urls = gson.fromJson(json, tp);
            thumbnails = gson.fromJson(json2, tp);
        }
    }

    private void saveData() {
        SharedPreferences sp = getSharedPreferences("youtubeplayer shared preference", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String urlss = gson.toJson(urls);
        String uss = gson.toJson(thumbnails);
        editor.putString("urlsItems", urlss);
        editor.putString("usItems", uss);

        editor.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube_activity_main);

        layout = (LinearLayout) findViewById(R.id.YouTubeLinearL);
        EditText text = (EditText) findViewById(R.id.YouTubeLinkEditText);
        Button add = (Button) findViewById(R.id.YouTubeAddBtn);

        urls = new ArrayList<>();
        thumbnails = new ArrayList<>();
        listener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(YouTube_MainAct.this).setTitle("Delete confirmation").setMessage("Are you sure you want to delete this video?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                urls.remove(view.getTag());
                                thumbnails.remove("https://img.youtube.com/vi/" + view.getTag() + "/0.jpg");
                                layout.removeView(view);
                                saveData();
                                loadDataYoutube();
                            }
                        }).setNegativeButton("NO", null).show();
                return true;
            }
        };
        //VideoList videoList = new VideoList();

        loadDataYoutube();
        for (int i = 0; i < urls.size(); i++) {
            ImageView image = new ImageView(this);

            image.setLayoutParams(new android.view.ViewGroup.LayoutParams(500, 500));
            image.setMaxHeight(2000);
            image.setMaxWidth(2000);
            image.setTag(urls.get(i));
            image.setOnLongClickListener(listener);
            // String u = "https://img.youtube.com/vi/"+urls.get(i)+"/0.jpg";

            Glide.with(this).load(thumbnails.get(i)).into(image);
            image.setPadding(10, 0, 0, 0);
            image.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(YouTube_MainAct.this, "The video is starting!",
                                    Toast.LENGTH_SHORT).show();


                            String localURL = view.getTag().toString();

                            Intent intent = new Intent(YouTube_MainAct.this, YouTube_Screen.class);
                            intent.putExtra("url", localURL);
                            startActivity(intent);

                        }

                    }
            );
            layout.addView(image);

        }

        add.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        try {
                            image = new ImageView(YouTube_MainAct.this);
                            if (text.getText().toString().contains("="))
                                url = (text.getText().toString().split("=")[1].substring(0, 11));
                            else {
                                url = (text.getText().toString().substring(17));
                            }
                            image.setLayoutParams(new android.view.ViewGroup.LayoutParams(500, 500));
                            image.setMaxHeight(2000);
                            image.setMaxWidth(2000);
                            image.setTag(url);
                            String u = "https://img.youtube.com/vi/" + url + "/0.jpg";
                            Glide.with(YouTube_MainAct.this).load(u).into(image);
                            image.setPadding(10, 0, 0, 0);
                            text.setText("");
                            urls.add(url);
                            thumbnails.add(u);
                            //  videoList.add(url,u);
                            saveData();
                            loadDataYoutube();
                        } catch (Exception e) {

                            new AlertDialog.Builder(YouTube_MainAct.this).setTitle("Warning").setMessage("Invaid URL address!!!")
                                  .setNegativeButton("OK", null).show();
                            text.setText("");
                        }


                        image.setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(YouTube_MainAct.this, "The video is starting!",
                                                Toast.LENGTH_SHORT).show();


                                        String localURL = view.getTag().toString();

                                        Intent intent = new Intent(YouTube_MainAct.this, YouTube_Screen.class);
                                        intent.putExtra("url", localURL);
                                        startActivity(intent);

                                    }

                                }
                        );
                        image.setOnLongClickListener(listener);
                        layout.addView(image);
                    }
                }
        );

    }
}