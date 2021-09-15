package com.moringaschool.waterrefillrecords.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.moringaschool.waterrefillrecords.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String TAG = MainActivity.class.getSimpleName();
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.getStartedButton) Button mGetStartedButton;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.shopNameEditText) EditText mShopNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mGetStartedButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mGetStartedButton) {
            String shopName = mShopNameEditText.getText().toString();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.putExtra("shopName", shopName);
            startActivity(intent);
            Toast.makeText(MainActivity.this, shopName, Toast.LENGTH_LONG).show();
        }
    }

}