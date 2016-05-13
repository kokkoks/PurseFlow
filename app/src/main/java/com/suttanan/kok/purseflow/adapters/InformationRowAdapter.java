package com.suttanan.kok.purseflow.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.suttanan.kok.purseflow.R;
import com.suttanan.kok.purseflow.others.Transaction;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by K.K.K on 5/7/2016.
 */
public class InformationRowAdapter extends BaseAdapter {
    private Context mContext;
    private String[] dateKeys;
    private HashMap<String, ArrayList<Transaction>> hashdatas;
    private int[] resId;

    public InformationRowAdapter(Context mContext, String[] dateKeys) {
        this.mContext = mContext;
        this.dateKeys = dateKeys;
    }

    public InformationRowAdapter(Context mContext, String[] dateKeys, HashMap<String, ArrayList<Transaction>> hash, int[] resId) {
        this.mContext = mContext;
        this.dateKeys = dateKeys;
        this.resId = resId;
        this.hashdatas = hash;
    }

    @Override
    public int getCount() {
        return dateKeys.length;
    }

    @Override
    public Object getItem(int position) {
        return dateKeys[position];
    }

    @Override
    public long getItemId(int position) {
        return resId[position];
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            view = mInflater.inflate(R.layout.information_row, parent, false);

            LinearLayout rootLinearLayout = (LinearLayout) view.findViewById(R.id.information_row_linearLayout);

//            LinearLayout linearLayout = new LinearLayout(view.getContext());
//            linearLayout.setOrientation(LinearLayout.VERTICAL);
//
//            ArrayList<Transaction> arrayList = hashdatas.get(dateKeys[position]);
//
//            for (int i = 0; i < arrayList.size(); i++) {
//                TextView text = new TextView(view.getContext());
//                text.setTextColor(Color.parseColor("#000000"));
//                text.setText(arrayList.get(i).getCategory());
//                linearLayout.addView(text);
//            }
//
//            rootLinearLayout.addView(linearLayout);

        }

        TextView dateTextView = (TextView) view.findViewById(R.id.information_textView);
        dateTextView.setText(dateKeys[position]);

//        LinearLayout rootLinearLayout = (LinearLayout) view.findViewById(R.id.information_row_linearLayout);
//        LinearLayout linearLayout = new LinearLayout(view.getContext());
//        linearLayout.setOrientation(LinearLayout.VERTICAL);
//        for(int i = 0; i <5; i++){
//            TextView text  = new TextView(view.getContext());
//            text.setTextColor(Color.parseColor("#000000"));
//            text.setText("test " + i);
//            linearLayout.addView(text);
//        }
//
//        rootLinearLayout.addView(linearLayout);

        return view;
    }
}
