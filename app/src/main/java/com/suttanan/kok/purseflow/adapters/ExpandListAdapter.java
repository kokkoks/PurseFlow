package com.suttanan.kok.purseflow.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.suttanan.kok.purseflow.R;
import com.suttanan.kok.purseflow.others.Transaction;
import com.suttanan.kok.purseflow.others.TransactionType;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by K.K.K on 5/14/2016.
 */
public class ExpandListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private HashMap<String, ArrayList<Transaction>> hashdatas;
    private  List<String> dateStrings;

    public ExpandListAdapter(Context context, HashMap<String, ArrayList<Transaction>> hashdatas,
                             List<String> dateStrings){
        this.context = context;
        this.hashdatas = hashdatas;
        this.dateStrings = dateStrings;
    }

    @Override
    public int getGroupCount() {
        return dateStrings.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<Transaction> chList = hashdatas.get(dateStrings.get(groupPosition));
        return chList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return dateStrings.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<Transaction> chList = hashdatas.get(dateStrings.get(groupPosition));
        return chList.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.new_information_group_item, null);
        }
        TextView date = (TextView) convertView.findViewById(R.id.information_group_dateTextView);
        TextView value = (TextView) convertView.findViewById(R.id.information_group_value);

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        float num = sumGroupValue(groupPosition);
        value.setText(df.format(num) + " THB");
        setTextColor(num, value);
        date.setText(dateStrings.get(groupPosition));
        return convertView;
    }

    private void setTextColor(float num, TextView value) {
        if(num < 0 ){
            value.setTextColor(Color.parseColor("#FF4000"));
        } else {
            value.setTextColor(Color.parseColor("#01DF74"));
        }
    }

    private float sumGroupValue(int groupPosition) {
        ArrayList<Transaction> transactions = hashdatas.get(dateStrings.get(groupPosition));
        float sum = 0;
        for(int i = 0; i < transactions.size(); i++){
            if(transactions.get(i).getType().equals(String.valueOf(TransactionType.EXPENSES))) {
                sum -= transactions.get(i).getValue();
            } else {
                sum += transactions.get(i).getValue();
            }
        }
        return sum;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Transaction child = (Transaction) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.new_information_child_item, null);
        }
        TextView category = (TextView) convertView.findViewById(R.id.information_child_category);
        TextView value = (TextView) convertView.findViewById(R.id.information_child_value);

        category.setText(child.getCategory());

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        value.setText(df.format(child.getValue()) + " THB");

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
