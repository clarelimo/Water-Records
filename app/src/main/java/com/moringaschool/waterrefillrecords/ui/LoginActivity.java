package com.moringaschool.waterrefillrecords.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.moringaschool.waterrefillrecords.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.loginButton) Button mLoginButton;
    @BindView(R.id.registerTextView) TextView mRegisterTextView;
    @SuppressLint("NonConstantResourceId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mLoginButton.setOnClickListener(this);
        mRegisterTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
//        if (v == mLoginButton) {
//            Intent intent = new Intent(LoginActivity.this, SalesActivity.class);
//            startActivity(intent);
//        }

        if(v == mRegisterTextView){
            Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
            startActivity(intent);
            finish();
        }
    }
}