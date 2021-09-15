package com.moringaschool.waterrefillrecords;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.moringaschool.waterrefillrecords.modules.Sales;

import java.sql.Timestamp;
import java.util.List;

public class WaterRefillRecordsArrayAdapter extends ArrayAdapter {
    private Context mContext;
    private List<Sales> mSales;
    public WaterRefillRecordsArrayAdapter(Context mContext, int resource, List<Sales> mSales) {
        super(mContext, resource);
        this.mContext = mContext;
        this.mSales = mSales;
    }
    @Override
    public Object getItem(int position) {
        Sales sale = mSales.get(position);
        Timestamp date =  sale.getDate();
        return String.format("%s \nKsh: %s", date, sale);
    }

    @Override
    public int getCount() {
        return mSales.size();
    }
}
