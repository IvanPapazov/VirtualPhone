package com.os0.navigation;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

public class web_BrowserHistoryAdapter extends BaseAdapter {

    private Context mContext;
    private web_HistoryItemList hst;

    public web_BrowserHistoryAdapter(Context context, web_HistoryItemList hst) {
        mContext = context;
        this.hst = hst;
    }

    @Override
    public int getCount() {
        return hst.size();
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
            v = vi.inflate(R.layout.web_history_row, null);
        }
        TextView urlRowH = (TextView) v.findViewById(R.id.urlRowH);
        TextView timeRowH = (TextView) v.findViewById(R.id.dateRowH);
        urlRowH.setText(hst.get(i).getUrl());
        timeRowH.setText(hst.get(i).getTime());


        ImageView btnRowH = v.findViewById(R.id.btnRowH);
        btnRowH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDel1(i);
            }
        });

        return v;
    }

    private void showDialogDel1(int i) {
        Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.web_custom_dialog2);
        dialog.setCancelable(true);
        dialog.show();

        Button YbtnCD2 = dialog.findViewById(R.id.YbtnCD2);
        YbtnCD2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hst.remove(i);

                SharedPreferences SP = mContext.getSharedPreferences("Browser_History", MODE_PRIVATE);
                SharedPreferences.Editor editor = SP.edit();
                Gson gson = new Gson();
                String json = gson.toJson(hst);
                editor.putString("history", json);
                editor.apply();
                notifyDataSetChanged();
                dialog.dismiss();

            }
        });

        Button cancelBtn = dialog.findViewById(R.id.CbtnCD2);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

}
