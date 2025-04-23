package com.os0.navigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;


public class SchedulerMainActivity extends AppCompatActivity {

    private ArrayList<ScheduleItem> items;
    private ArrayAdapter<ScheduleItem> adapter;
    private boolean editMode = false;

    private void init() {
        // Initialize items list
        items = new ArrayList<>();
        loadData();
        // Initialize adapter and override a method to customize ListView format
        adapter = new ArrayAdapter<ScheduleItem>(this, android.R.layout.simple_list_item_2, android.R.id.text1, items) {
            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                // Set titles and dates to corresponding elements of ListView
                TextView scheduleTitle = view.findViewById(android.R.id.text1);
                TextView scheduleDate =  view.findViewById(android.R.id.text2);
                ScheduleItem item = getItem(position);
                assert item != null;
                scheduleTitle.setText(item.getTitle());
                scheduleDate.setText(item.getFormattedDate());
                return view;
            }
        };
        ListView listView = findViewById(R.id.scheduleListView);
        listView.setAdapter(adapter);
    }
    private void loadData() {
        SharedPreferences sp = getSharedPreferences("schedule shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        if(sp.contains("schedule list")) {
            String json = sp.getString("schedule list", null);
            Type type = new TypeToken<ArrayList<ScheduleItem>>(){}.getType();
            items = gson.fromJson(json, type);
        } else {
            // Only if app is started for the first time ever
            saveData();
        }
    }
    private void saveData() {
        SharedPreferences sp = getSharedPreferences("schedule shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(items);
        editor.putString("schedule list", json);
        editor.apply();
    }
    private void removeItem(int pos) {
        new AlertDialog.Builder(this).setTitle("Deletion confirmation")
                .setMessage("Are you sure you want to delete this schedule list item?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Yes", (dialog, whichButton) -> {
                    // Remove item from list
                    items.remove(pos);
                    // Update shared preferences
                    saveData();
                    loadData();
                    // Update ListView
                    adapter.clear();
                    adapter.addAll(items);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(SchedulerMainActivity.this, "Schedule item deleted successfully!", Toast.LENGTH_LONG).show();
                }).setNegativeButton("No", null).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler_main);
        init();

        // Create new schedule item
        findViewById(R.id.schedulerCreateNew).setOnClickListener(view -> {
            Intent intent = new Intent(this, CreateScheduleItemActivity.class);
            // List to JSON String
            Gson gson = new Gson();
            String json = gson.toJson(items);
            // Send to activity and start
            intent.putExtra("json", json);
            startActivityForResult(intent, 1000);
        });

        // Delete schedule item
        ListView lv = findViewById(R.id.scheduleListView);
        lv.setLongClickable(true);
        lv.setClickable(true);
        lv.setOnItemLongClickListener((adapterView, view, pos, id) -> {
            removeItem(pos);
            return true;
        });
        lv.setOnItemClickListener((adapterView, view, pos, id) -> {
            editMode = true;
            lv.getItemAtPosition(pos);
            Intent intent = new Intent(SchedulerMainActivity.this, CreateScheduleItemActivity.class);
            Gson gson = new Gson();
            String json = gson.toJson(items);
            intent.putExtra("json", json);
            intent.putExtra("pos", pos);
            intent.putExtra("editMode", editMode);
            startActivityForResult(intent, 1001);
        });
    }

    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Only if we are redirected by the Done button
        if(resultCode >= 1000) {
            Gson gson = new Gson();
            String json = data.getStringExtra("jsonBack");
            Type type=new TypeToken<ArrayList<ScheduleItem>>(){}.getType();
            items = gson.fromJson(json, type);
            // Sort Ascending
            Objects.requireNonNull(items).sort(Comparator.comparing(ScheduleItem::getDate));
            saveData();
            loadData();
            // Update ListView
            adapter.clear();
            adapter.addAll(items);
            adapter.notifyDataSetChanged();
            editMode = false;
        }
    }
}