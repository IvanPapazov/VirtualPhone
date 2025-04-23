package com.os0.navigation;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class camera_Common extends AppCompatActivity {

    public static final String SHARED_PREFS = "shared preferences";
    public static SharedPreferences sharedPreferences;

    public static void saveData(Context context, ArrayList<camera_MediaObject> mediaObjects) {

        sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mediaObjects);
        editor.putString("media objects list", json);
        editor.apply();
    }

    // to be called in gallery
    public static ArrayList<camera_MediaObject> loadData(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("media objects list", null);
        Type type = new TypeToken<ArrayList<camera_MediaObject>>() {}.getType();
        ArrayList<camera_MediaObject> mediaObjects = gson.fromJson(json, type);

        if(mediaObjects == null) {
            mediaObjects = new ArrayList<camera_MediaObject>();
        }
        return mediaObjects;
    }
}
