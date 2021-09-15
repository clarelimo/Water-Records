package com.moringaschool.waterrefillrecords;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.moringaschool.waterrefillrecords.api.ApiClient;
import com.moringaschool.waterrefillrecords.api.ApiInterface;
import com.moringaschool.waterrefillrecords.modules.Sales;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

public class SalesActivity extends AppCompatActivity {
    @BindView(R.id.shopNameTextView) TextView mShopNameTextView;
    @BindView(R.id.listView)  ListView mListView;
    private String[] dates = {"Mon 1st","Tue 2nd", "Wed 3rd", "Thur 4th", "Fri 5th", "Sat 6th", "Sun 7th"};
    private int[] sales = {1000,2000,3000,4000,5000,6000,7000};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        ButterKnife.bind(this);

        WaterRefillRecordsArrayAdapter adapter = new WaterRefillRecordsArrayAdapter(this, android.R.layout.simple_list_item_1, dates, sales);
        mListView.setAdapter(adapter);

        Intent intent = getIntent();
        String shop_name = intent.getStringExtra("shopName");
        mShopNameTextView.setText("This Week's Sales for: " + shop_name+ " Shop");

        ApiInterface client = ApiClient.getClient();

        Call<List<Sales>> call = client.getSales();

    }
}
