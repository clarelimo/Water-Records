package com.moringaschool.waterrefillrecords;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SalesActivity extends AppCompatActivity {
    @BindView(R.id.shopNameTextView)TextView mShopNameTextView;
    protected String[] daysOfTheWeek = {"Mon 1st","Tue 2nd", "Wed 3rd", "Thur 4th", "Fri 5th", "Sat 6th", "Sun 7th"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String shop_name = intent.getStringExtra("shopName");
        mShopNameTextView.setText("This Week's Sales for: " + shop_name+ " Shop");
    }
}
