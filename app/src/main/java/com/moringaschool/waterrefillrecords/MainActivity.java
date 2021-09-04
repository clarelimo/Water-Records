package com.moringaschool.waterrefillrecords;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.getStartedButton) Button mGetStartedButton;
    @BindView(R.id.shopNameEditText) EditText mShopNameEditText;
    @BindView(R.id.appNameTextView) TextView mAppNameTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mGetStartedButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String location = mShopNameEditText.getText().toString();
                Intent intent = new Intent(MainActivity.this, SalesActivity.class);
                intent.putExtra("shop name", location);
                startActivity(intent);
            }
        });
    }
}