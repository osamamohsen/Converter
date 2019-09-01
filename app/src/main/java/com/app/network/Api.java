package com.app.network;

import com.app.models.CurrenciesResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    //https://revolut.duckdns.org/latest?base=EUR
    @GET("latest")
    Observable<CurrenciesResponse> getCurrencies(@Query("base") String base);
}
