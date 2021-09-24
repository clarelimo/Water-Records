package com.moringaschool.waterrefillrecords.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.moringaschool.waterrefillrecords.Constants;
import com.moringaschool.waterrefillrecords.R;
import com.moringaschool.waterrefillrecords.adapters.FirebaseSalesViewHolder;
import com.moringaschool.waterrefillrecords.adapters.SavedSalesAdapter;
import com.moringaschool.waterrefillrecords.modules.Sale;
import com.moringaschool.waterrefillrecords.modules.Sales;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedSalesActivity extends AppCompatActivity {
    private DatabaseReference mSalesReference;
    FirebaseStorage mStorage;
    private List<Sale> mSales;
    private SavedSalesAdapter salesListAdapter;
    @BindView(R.id.savedRecyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savedsales);
        ButterKnife.bind(this);

        mSalesReference = FirebaseDatabase.getInstance().getReference(Constants.PREFERENCES_SALES_KEY);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mStorage = FirebaseStorage.getInstance();
        mSales = new ArrayList<>();
        salesListAdapter = new SavedSalesAdapter(mSales, this);
        mRecyclerView.setAdapter(salesListAdapter);

        setUpFireBaseAdapter();
    }

    private void setUpFireBaseAdapter() {
        mSalesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    mSales.add(dataSnapshot.getValue(Sale.class));
                }

                for (Sale sale: mSales) {
                    Log.d("The date is ", "Date: " + sale.getDate());
                    Log.d("The sales for today ", "Sales: " + sale.getTotalSales()); //log
                }

                salesListAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
