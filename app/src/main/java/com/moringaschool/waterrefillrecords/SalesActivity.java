package com.moringaschool.waterrefillrecords;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SalesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        Intent intent = getIntent();
        String location = intent.getStringExtra("shop name");
    }
}
