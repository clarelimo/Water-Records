package com.moringaschool.waterrefillrecords.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moringaschool.waterrefillrecords.Constants;
import com.moringaschool.waterrefillrecords.R;
import com.moringaschool.waterrefillrecords.modules.Sales;
import com.moringaschool.waterrefillrecords.ui.SaleDetailActivity;
import org.parceler.Parcels;

import java.util.ArrayList;

public class FirebaseSalesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    View mView;
    Context mContext;

    public FirebaseSalesViewHolder(@NonNull View itemView) {
        super(itemView);
        this.mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindSales(Sales sales){
        TextView totalSales = mView.findViewById(R.id.amountTextView);
        TextView dateOfSale = mView.findViewById(R.id.dateTextView);
        TextView balance = mView.findViewById(R.id.balanceTextView);
        TextView bottlesSold = mView.findViewById(R.id.bottlesSoldTextView);
        TextView litresSold = mView.findViewById(R.id.litresSoldTextView);
        totalSales.setText("Kshs. "+ sales.getTotalSales());
        dateOfSale.setText(sales.getDate().toString());
        balance.setText(sales.getBalance());
        bottlesSold.setText(sales.getEmptyBottlesSold());
        litresSold.setText(sales.getLitresSold());

    }

    @Override
    public void onClick(View view) {
        final ArrayList<Sales> sales = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.PREFERENCES_SALES_KEY);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    sales.add(dataSnapshot.getValue(Sales.class));
                }

                int itemPosition = getLayoutPosition();

                Intent intent = new Intent(mContext, SaleDetailActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("sales", Parcels.wrap(sales));

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
