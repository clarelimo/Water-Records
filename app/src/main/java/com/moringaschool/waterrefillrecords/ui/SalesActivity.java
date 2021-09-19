package com.moringaschool.waterrefillrecords.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moringaschool.waterrefillrecords.R;
import com.moringaschool.waterrefillrecords.WaterRefillRecordsArrayAdapter;
import com.moringaschool.waterrefillrecords.adapters.SalesListAdapter;
import com.moringaschool.waterrefillrecords.network.ApiClient;
import com.moringaschool.waterrefillrecords.network.ApiInterface;
import com.moringaschool.waterrefillrecords.modules.Sales;

import java.sql.Timestamp;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalesActivity extends AppCompatActivity {
    @BindView(R.id.shopNameTextView) TextView mShopNameTextView;
    @BindView(R.id.errorTextView) TextView mErrorTextView;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    private SalesListAdapter mAdapter;

    List<Sales> sales;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        ButterKnife.bind(this);

        ApiInterface client = ApiClient.getClient();

        Call<List<Sales>> call = client.getSales();

        call.enqueue(new Callback<List<Sales>>() {
            @Override
            public void onResponse(Call<List<Sales>> call, Response<List<Sales>> response) {
                if(response.isSuccessful()){
                    sales = response.body();
                    mAdapter = new SalesListAdapter(sales,SalesActivity.this);
                    mRecyclerView.setAdapter(mAdapter);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SalesActivity.this);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);
                    showSales();
                }else{
                    showUnsuccessfulMessage();
                }
            }

            @Override
            public void onFailure(Call<List<Sales>> call, Throwable t) {
                hideProgressBar();
                showFailureMessage();
            }
        });
    }

    private void showFailureMessage() {
        mErrorTextView.setText("Something went wrong. Please check your Internet connection and try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showUnsuccessfulMessage() {
        mErrorTextView.setText("Something went wrong. Please try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showSales() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }
}
