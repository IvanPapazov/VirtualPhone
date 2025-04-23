package com.os0.navigation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class camera_MediaObjectAdapter extends ArrayAdapter{

    private ArrayList<String> filePaths;
    private ArrayList<Boolean> mediaTypeIndicators;
    private Activity mContext;

    public camera_MediaObjectAdapter(Activity context, ArrayList<String> filePaths, ArrayList<Boolean> mediaTypeIndicators) {
        super(context, R.layout.camera_row_item, filePaths);
        this.mContext = context;
        this.filePaths = filePaths;
        this.mediaTypeIndicators = mediaTypeIndicators;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        LayoutInflater inflater = mContext.getLayoutInflater();
        if (convertView == null) {
            row = inflater.inflate(R.layout.camera_row_item, null, true);
        }
        ImageView image = (ImageView) row.findViewById(R.id.imageViewFlag);
        ImageView  playVideo = (ImageView) row.findViewById(R.id.playVideo);
        RelativeLayout deleteBtn = (RelativeLayout) row.findViewById(R.id.deleteMeidaBtn);
        deleteBtn.setTag(position);
        //TODO fix the delete button
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Delete media");
                builder.setMessage("Are you sure you want to delete this media file?");
                builder.setCancelable(false);

                builder.setNegativeButton("Cancel" ,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences = getContext().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
                        Gson gson = new Gson();
                        String json = sharedPreferences.getString("media objects list", null);
                        Type type = new TypeToken<ArrayList<camera_MediaObject>>() {
                        }.getType();
                        ArrayList<camera_MediaObject> mediaObjects = gson.fromJson(json, type);
                        mediaObjects.remove(position);
                        File file = new File(filePaths.get(position));
                        if(file.exists())
                            file.delete();

                        if (mediaObjects == null) {
                            mediaObjects = new ArrayList<camera_MediaObject>();
                        }

                        Integer index = (Integer) view.getTag();
                        filePaths.remove(position);
                        mediaTypeIndicators.remove(position);

                        sharedPreferences = getContext().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        gson = new Gson();
                        json = gson.toJson(mediaObjects);
                        editor.putString("media objects list", json);
                        editor.apply();

                        notifyDataSetChanged();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        if(mediaTypeIndicators.get(position)) {
            image.setVisibility(View.VISIBLE);
            playVideo.setVisibility(View.GONE);
            image.setImageURI(Uri.parse(filePaths.get(position)));
        } else {
            image.setVisibility(View.GONE);
            playVideo.setVisibility(View.VISIBLE);
        }

        return  row;
    }
}
