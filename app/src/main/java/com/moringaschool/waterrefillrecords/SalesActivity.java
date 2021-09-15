package com.moringaschool.waterrefillrecords;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.moringaschool.waterrefillrecords.api.ApiClient;
import com.moringaschool.waterrefillrecords.api.ApiInterface;
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
    @BindView(R.id.listView)  ListView mListView;
    @BindView(R.id.errorTextView) TextView mErrorTextView;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;
//    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    List<Sales> salesList;
    List<Timestamp> dates;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        ButterKnife.bind(this);

        WaterRefillRecordsArrayAdapter adapter = new WaterRefillRecordsArrayAdapter(this, android.R.layout.simple_list_item_1, salesList);
        mListView.setAdapter(adapter);

        Intent intent = getIntent();
        String shop_name = intent.getStringExtra("shopName");
        mShopNameTextView.setText("This Week's Sales for: " + shop_name+ " Shop");

        ApiInterface client = ApiClient.getClient();

        Call<List<Sales>> call = client.getSales();

        call.enqueue(new Callback<List<Sales>>() {
            @Override
            public void onResponse(Call<List<Sales>> call, Response<List<Sales>> response) {
                if(response.isSuccessful()){
                    salesList = response.body();

                }else{

                }
            }

            @Override
            public void onFailure(Call<List<Sales>> call, Throwable t) {

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

    private void showRestaurants() {
        mListView.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }
}
