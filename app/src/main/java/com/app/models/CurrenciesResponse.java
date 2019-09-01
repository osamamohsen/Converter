
package com.app.models;

import com.google.gson.annotations.SerializedName;


public class CurrenciesResponse {

    @SerializedName("base")
    private String mBase;
    @SerializedName("date")
    private String mDate;
    @SerializedName("rates")
    private Rates mRates;

    public String getBase() {
        return mBase;
    }

    public void setBase(String base) {
        mBase = base;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public Rates getRates() {
        return mRates;
    }

    public void setRates(Rates rates) {
        mRates = rates;
    }

}
