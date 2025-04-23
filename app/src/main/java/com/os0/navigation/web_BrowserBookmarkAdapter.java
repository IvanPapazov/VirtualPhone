package com.os0.navigation;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class web_BrowserBookmarkAdapter extends BaseAdapter {

    private Context mContext;
    private web_BookmarksItemList bkm;
    private EditText name;
    private EditText url;
    web_BrowserBookmarkAdapter bkmAdapter;


    public web_BrowserBookmarkAdapter(Context c, web_BookmarksItemList bkm1) {
        mContext = c;
        bkm = bkm1;
    }

    @Override
    public int getCount() {
        return bkm.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.web_bookmark_row, null);
        }
        TextView urlRowB = (TextView) v.findViewById(R.id.urlRowB);
        TextView nameRowB = (TextView) v.findViewById(R.id.nameRowB);
        urlRowB.setText(bkm.get(i).getUrl());
        nameRowB.setText(bkm.get(i).getName());


        ImageView btnRowB = v.findViewById(R.id.btnRowB);
        btnRowB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDel1(i);
            }
        });

        ImageView btnEditRowB = v.findViewById(R.id.btnEditRowB);
        btnEditRowB.setTag(i);
        btnEditRowB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int) view.getTag();
                showDialogEdit(position);
            }
        });

        return v;
    }

    private void showDialogEdit(int i) {
        Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.web_custom_dialog3);
        dialog.setCancelable(true);

        Button DbtnCD3 = dialog.findViewById(R.id.DbtnCD3);
        DbtnCD3.setTag(i);
        Button CbtnCD3 = dialog.findViewById(R.id.CbtnCD3);
        name = (EditText) dialog.findViewById(R.id.BnameD3);
        url = (EditText) dialog.findViewById(R.id.BurlD3);

        name.setText(bkm.get(i).getName());
        url.setText(bkm.get(i).getUrl());

        CbtnCD3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        DbtnCD3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int) view.getTag();

                String localUrl = url.getText().toString();
                String localName = name.getText().toString();

                if (localUrl.length() >= 7) {
                    if (!localUrl.substring(0, 4).equals("www.")) {
                        localUrl = "www." + localUrl;
                    }
                } else {
                    localUrl = "www." + localUrl;
                }
                if (Patterns.WEB_URL.matcher(localUrl).matches()) {
                    if (localName.equals("")) {
                        localName = localUrl;
                    }
                    bkm.get(position).setName(localName);
                    bkm.get(position).setUrl(localUrl);
                    notifyDataSetChanged();
                    SaveData_B();
                    dialog.dismiss();

                } else Toast.makeText(mContext,"Oh no! It appears that this url address is not an existing one. Thus your bookmark hasn't been saved.", Toast.LENGTH_LONG).show();
            }
        });
        dialog.show();
    }

    private void showDialogDel1(int i) {
        Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.web_custom_dialog4);
        dialog.setCancelable(true);
        dialog.show();


        Button CbtnCD4 = dialog.findViewById(R.id.CbtnCD4);
        CbtnCD4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Button YbtnCD4 = dialog.findViewById(R.id.YbtnCD4);
        YbtnCD4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bkm.remove(i);

                SharedPreferences SP = mContext.getSharedPreferences("Browser_Bookmarks", MODE_PRIVATE);
                SharedPreferences.Editor editor = SP.edit();
                Gson gson = new Gson();
                String json = gson.toJson(bkm);
                editor.putString("bookmarks", json);
                editor.apply();
                notifyDataSetChanged();
                dialog.dismiss();
            }
        });
    }

    private void SaveData_B() {
        SharedPreferences SP = mContext.getSharedPreferences("Browser_Bookmarks", MODE_PRIVATE);
        SharedPreferences.Editor editor = SP.edit();
        Gson gson = new Gson();
        String json = gson.toJson(bkm);
        editor.putString("bookmarks", json);
        editor.apply();
    }


}
