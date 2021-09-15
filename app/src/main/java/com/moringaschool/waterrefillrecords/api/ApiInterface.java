package com.moringaschool.waterrefillrecords.api;

import com.moringaschool.waterrefillrecords.SalesActivity;
import com.moringaschool.waterrefillrecords.modules.Expenses;
import com.moringaschool.waterrefillrecords.modules.Sales;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("sales")
    Call<List<Sales>> getSales();

    @GET("expenses")
    Call<List<Expenses>> getExpenses();
}
