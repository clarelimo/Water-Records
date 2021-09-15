package com.moringaschool.waterrefillrecords.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moringaschool.waterrefillrecords.R;
import com.moringaschool.waterrefillrecords.modules.Sales;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SalesListAdapter extends RecyclerView.Adapter<SalesListAdapter.SalesViewHolder> {
    private List<Sales> mSales;
    private Context mContext;

    public SalesListAdapter(List<Sales> sales, Context context){
        mContext = context;
        mSales = sales;
    }

    @Override
    public SalesListAdapter.SalesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sales_list_item,parent,false);
        SalesViewHolder viewHolder = new SalesViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SalesListAdapter.SalesViewHolder holder, int position) {
        holder.bindSales(mSales.get(position));
    }

    @Override
    public int getItemCount() {
        return mSales.size();
    }

    public class SalesViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.amountTextView) TextView mAmountTextView;
        @BindView(R.id.dateTextView) TextView mDateTextView;
        @BindView(R.id.salesImageView) ImageView msalesImageView;
        private Context mContext;

        public SalesViewHolder(View viewItem){
            super(viewItem);
            ButterKnife.bind(this, viewItem);
            mContext = viewItem.getContext();
        }

        public void bindSales(Sales sales){
            mDateTextView.setText(sales.getDate().toString());
            mAmountTextView.setText("Ksh. "+ sales.getCashSales());
        }
    }
}
