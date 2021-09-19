package com.moringaschool.waterrefillrecords.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moringaschool.waterrefillrecords.Constants;
import com.moringaschool.waterrefillrecords.R;
import com.moringaschool.waterrefillrecords.modules.Sales;

import java.sql.Timestamp;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddSalesActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @BindView(R.id.litresSold) EditText mlitresSold;
    @BindView(R.id.bottlesSold) EditText mBottlesSold;
    @BindView(R.id.totalSales) EditText mTotalSales;
    @BindView(R.id.balance) EditText mBalance;
    @BindView(R.id.addSalesButton) Button mAddSalesButton;
    private Sales mSale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sales);
        ButterKnife.bind(this);

        mSharedPreferences = getSharedPreferences(Constants.PREFERENCES_SALES_KEY, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mAddSalesButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mAddSalesButton){
            createNewSale();
            DatabaseReference restaurantRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.PREFERENCES_SALES_KEY);
            restaurantRef.push().setValue(mSale);
            Intent intent = new Intent(AddSalesActivity.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(AddSalesActivity.this, "Saved", Toast.LENGTH_SHORT).show();
        }
    }

    private void createNewSale() {
        String liresSold = mlitresSold.getText().toString().trim();
        String bottlesSold = mBottlesSold.getText().toString().trim();
        String totalSales = mTotalSales.getText().toString().trim();
        String balance = mBalance.getText().toString().trim();

        mSale= new Sales(new Timestamp(new Date().getDate()), Integer.parseInt(totalSales), Integer.parseInt(liresSold), Integer.parseInt(bottlesSold), Integer.parseInt(balance));
        mSale.setLitresSold(Integer.parseInt(liresSold));
        mSale.setEmptyBottlesSold( Integer.parseInt(bottlesSold));
        mSale.setCashSales(Integer.parseInt(totalSales));
        mSale.setBalance(Integer.parseInt(balance));
    }
}