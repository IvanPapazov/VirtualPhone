package com.os0.navigation;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class ChronicAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> arr;

    public ChronicAdapter(Context context, ArrayList<String> arrayChronic) {
        mContext = context;
        arr = arrayChronic;
    }

    @Override
    public int getCount() {
        return arr.size();
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
            v = vi.inflate(R.layout.chronic_row, null);
        }
        TextView rowChr = (TextView) v.findViewById(R.id.rowChr);
        rowChr.setText(arr.get(i));

        return v;
    }
}
