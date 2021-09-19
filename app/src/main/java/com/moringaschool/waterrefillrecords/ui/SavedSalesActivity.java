package com.moringaschool.waterrefillrecords.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moringaschool.waterrefillrecords.Constants;
import com.moringaschool.waterrefillrecords.R;
import com.moringaschool.waterrefillrecords.adapters.FirebaseSalesViewHolder;
import com.moringaschool.waterrefillrecords.modules.Sales;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedSalesActivity extends AppCompatActivity {
    private DatabaseReference mSalesReference;
    private FirebaseRecyclerAdapter<Sales, FirebaseSalesViewHolder> mFirebaseAdapter;

    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.errorTextView) TextView mErrorTextView;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        ButterKnife.bind(this);
        
        mSalesReference = FirebaseDatabase.getInstance().getReference(Constants.PREFERENCES_SALES_KEY);
        setUpFireBaseAdapter();
        hideProgressbar();
        showSales();
    }

    private void showSales() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void hideProgressbar() {
        mProgressBar.setVisibility(View.GONE);
    }

    private void setUpFireBaseAdapter() {
        FirebaseRecyclerOptions<Sales> options = new FirebaseRecyclerOptions.Builder<Sales>()
                .setQuery(mSalesReference, Sales.class)
                .build();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<Sales, FirebaseSalesViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseSalesViewHolder holder, int position, @NonNull Sales model) {
                holder.bindSales(model);
            }

            @NonNull
            @Override
            public FirebaseSalesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.sales_list_item, parent, false);
                return new FirebaseSalesViewHolder(view);
            }
        };

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mFirebaseAdapter!= null) {
            mFirebaseAdapter.stopListening();
        }
    }
}