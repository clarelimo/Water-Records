package com.moringaschool.waterrefillrecords;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SalesActivity extends AppCompatActivity {
    private TextView mShopNameTextView;
    private ListView mListView;
    private String[] dates = {"Mon 1st","Tue 2nd", "Wed 3rd", "Thur 4th", "Fri 5th", "Sat 6th", "Sun 7th"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        ButterKnife.bind(this);

        mListView = (ListView) findViewById(R.id.listView);
        mShopNameTextView = (TextView) findViewById(R.id.shopNameTextView);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, dates);
        mListView.setAdapter(adapter);

        Intent intent = getIntent();
        String shop_name = intent.getStringExtra("shopName");
        mShopNameTextView.setText("This Week's Sales for: " + shop_name+ " Shop");
    }
}
