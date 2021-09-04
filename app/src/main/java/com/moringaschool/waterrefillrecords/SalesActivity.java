package com.moringaschool.waterrefillrecords;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SalesActivity extends AppCompatActivity {
    @BindView(R.id.shopNameTextView)TextView mShopNameTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String shop_name = intent.getStringExtra("shop name");
        mShopNameTextView.setText("This Week's Sales for: " + shop_name+ " Shop");
    }
}
