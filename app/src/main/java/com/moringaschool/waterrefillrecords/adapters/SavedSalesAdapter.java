package com.moringaschool.waterrefillrecords.adapters;

import com.moringaschool.waterrefillrecords.modules.Sale;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moringaschool.waterrefillrecords.R;
import com.moringaschool.waterrefillrecords.ui.AddSalesActivity;
import com.moringaschool.waterrefillrecords.ui.AddSalesActivity_ViewBinding;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedSalesAdapter extends RecyclerView.Adapter<SavedSalesAdapter.SavedSalesViewHolder> {
    private List<Sale> mSales;
    private Context mContext;

    public SavedSalesAdapter(List<Sale> sales, Context context){
        mContext = context;
        mSales = sales;
    }

    @Override
    public SavedSalesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_item,parent,false);
        SavedSalesViewHolder viewHolder = new SavedSalesViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SavedSalesViewHolder holder, int position) {
        holder.bindSales(mSales.get(position));
    }

    @Override
    public int getItemCount() {
        return mSales.size();
    }

    public class SavedSalesViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.savedDate) TextView date;
        @BindView(R.id.savedTotalSales) TextView total;
        @BindView(R.id.machineImageView) ImageView mImageLabel;
//        @BindView(R.id.savedBalance) TextView balance;
//        @BindView(R.id.savedLitresSold) TextView litres;
//        @BindView(R.id.savedBottles) TextView bottles;
        private Context mContext;

        public SavedSalesViewHolder(View viewItem){
            super(viewItem);
            ButterKnife.bind(this, viewItem);
            mContext = viewItem.getContext();
        }

        public void bindSales(Sale sales){
            date.setText(sales.getDate());
            total.setText(Integer.toString(sales.getTotalSales()));
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(sales.getMachineImage(), bmOptions);

            mImageLabel.setImageBitmap(bitmap);
//            balance.setText(Integer.toString(sales.getBalance()));
//            litres.setText(Integer.toString(sales.getLitresSold()));
//            bottles.setText(Integer.toString(sales.getEmptyBottlesSold()));
        }
    }
}