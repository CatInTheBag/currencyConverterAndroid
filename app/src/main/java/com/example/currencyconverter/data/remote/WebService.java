package com.example.currencyconverter.data.remote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WebService {
    @GET("latest")
    Call<List<CurrecnyModelTwo>> getCurrentCurrencyRate(@Query("base") String baseCurrency);
}
