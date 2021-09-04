package com.moringaschool.waterrefillrecords;

import android.content.Context;
import android.widget.ArrayAdapter;

public class WaterRefillRecordsArrayAdapter extends ArrayAdapter {
    private Context mContext;
    private String[] mDates;
    private int[] mSales;
    public WaterRefillRecordsArrayAdapter(Context mContext, int resource, String[] mDates, int[] mSales) {
        super(mContext, resource);
        this.mContext = mContext;
        this.mDates = mDates;
        this.mSales = mSales;
    }
    @Override
    public Object getItem(int position) {
        String date = mDates[position];
        int sale = mSales[position];
        return String.format("%s \nKsh: %s", date, sale);
    }

    @Override
    public int getCount() {
        return mDates.length;
    }
}
