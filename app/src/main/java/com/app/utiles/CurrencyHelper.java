package com.app.utiles;

import android.graphics.drawable.Drawable;
import android.util.Log;

import com.app.R;
import com.app.models.CurrencyModel;
import com.app.models.Rates;
import com.app.utiles.resources.ResourceManager;

import java.util.ArrayList;

public class CurrencyHelper {

    private static final String TAG = "CurrencyHelper";

    public Drawable getFlag(String name) {
        Drawable flag = ResourceManager.getDrawable(R.drawable.ic_aud_flag);
        if (name.equals("AUD"))
            return ResourceManager.getDrawable(R.drawable.ic_aud_flag);
        else if (name.equals("BGN"))
            return ResourceManager.getDrawable(R.drawable.ic_bgn_flag);
        else if (name.equals("BRL"))
            return ResourceManager.getDrawable(R.drawable.ic_brl_flag);
        else if (name.equals("CAD"))
            return ResourceManager.getDrawable(R.drawable.ic_cad_flag);
        else if (name.equals("CHF"))
            return ResourceManager.getDrawable(R.drawable.ic_chf_flag);
        else if (name.equals("CNY"))
            return ResourceManager.getDrawable(R.drawable.ic_cny_flag);
        else if (name.equals("DKK"))
            return ResourceManager.getDrawable(R.drawable.ic_dkk_flag);
        else if (name.equals("GBP"))
            return ResourceManager.getDrawable(R.drawable.ic_gbp_flag);
        else if (name.equals("HKD"))
            return ResourceManager.getDrawable(R.drawable.ic_hkd_flag);
        else if (name.equals("HUF"))
            return ResourceManager.getDrawable(R.drawable.ic_huf_flag);
        else if (name.equals("IDR"))
            return ResourceManager.getDrawable(R.drawable.ic_idr_flag);
        else if (name.equals("ILS"))
            return ResourceManager.getDrawable(R.drawable.ic_ils_flag);
        else if (name.equals("INR"))
            return ResourceManager.getDrawable(R.drawable.ic_inr_flag);
        else if (name.equals("ISK"))
            return ResourceManager.getDrawable(R.drawable.ic_isk_flag);
        else if (name.equals("JPY"))
            return ResourceManager.getDrawable(R.drawable.ic_jpy_flag);
        else if (name.equals("KRW"))
            return ResourceManager.getDrawable(R.drawable.ic_krw_flag);
        else if (name.equals("MXN"))
            return ResourceManager.getDrawable(R.drawable.ic_mxn_flag);
        else if (name.equals("MYR"))
            return ResourceManager.getDrawable(R.drawable.ic_myr_flag);
        else if (name.equals("NOK"))
            return ResourceManager.getDrawable(R.drawable.ic_nok_flag);
        else if (name.equals("NZD"))
            return ResourceManager.getDrawable(R.drawable.ic_nzd_flag);
        else if (name.equals("PHP"))
            return ResourceManager.getDrawable(R.drawable.ic_php_flag);
        else if (name.equals("PLN"))
            return ResourceManager.getDrawable(R.drawable.ic_pln_flag);
        else if (name.equals("RON"))
            return ResourceManager.getDrawable(R.drawable.ic_ron_flag);
        else if (name.equals("RUB"))
            return ResourceManager.getDrawable(R.drawable.ic_rub_flag);
        else if (name.equals("SEK"))
            return ResourceManager.getDrawable(R.drawable.ic_sek_flag);
        else if (name.equals("SGD"))
            return ResourceManager.getDrawable(R.drawable.ic_sgd_flag);
        else if (name.equals("THB"))
            return ResourceManager.getDrawable(R.drawable.ic_thb_flag);
        else if (name.equals("TRY"))
            return ResourceManager.getDrawable(R.drawable.ic_try_flag);
        else if (name.equals("USD"))
            return ResourceManager.getDrawable(R.drawable.ic_usd_flag);
        else if (name.equals("ZAR"))
            return ResourceManager.getDrawable(R.drawable.ic_zar_flag);
        return flag;
    }

    ArrayList<CurrencyModel> currencyModels = new ArrayList<>();

    public void setCurrencyData(Rates rates) {
        currencyModels.clear();
        if (rates.getAUD() != -1)
            currencyModels.add(new CurrencyModel("AUD", getFlag("AUD"), rates.getAUD()));
        if (rates.getBGN() != -1)
            currencyModels.add(new CurrencyModel("BGN", getFlag("BGN"), rates.getBGN()));
        if (rates.getBRL() != -1)
            currencyModels.add(new CurrencyModel("BRL", getFlag("BRL"), rates.getBRL()));
        if (rates.getCAD() != -1)
            currencyModels.add(new CurrencyModel("CAD", getFlag("CAD"), rates.getCAD()));
        if (rates.getCHF() != -1)
            currencyModels.add(new CurrencyModel("CHF", getFlag("CHF"), rates.getCHF()));
        if (rates.getCNY() != -1)
            currencyModels.add(new CurrencyModel("CNY", getFlag("CNY"), rates.getCNY()));
        if (rates.getCZK() != -1)
            currencyModels.add(new CurrencyModel("CZK", getFlag("CZK"), rates.getCZK()));
        if (rates.getDKK() != -1)
            currencyModels.add(new CurrencyModel("DKK", getFlag("DKK"), rates.getDKK()));
        if (rates.getGBP() != -1)
            currencyModels.add(new CurrencyModel("GBP", getFlag("GBP"), rates.getGBP()));
        if (rates.getHKD() != -1)
            currencyModels.add(new CurrencyModel("HKD", getFlag("HKD"), rates.getHKD()));
        if (rates.getHRK() != -1)
            currencyModels.add(new CurrencyModel("HRK", getFlag("HRK"), rates.getHRK()));
        if (rates.getHUF() != -1)
            currencyModels.add(new CurrencyModel("HUF", getFlag("HUF"), rates.getHUF()));
        if (rates.getIDR() != -1)
            currencyModels.add(new CurrencyModel("IDR", getFlag("IDR"), rates.getIDR()));
        if (rates.getILS() != -1)
            currencyModels.add(new CurrencyModel("ILS", getFlag("ILS"), rates.getILS()));
        if (rates.getINR() != -1)
            currencyModels.add(new CurrencyModel("INR", getFlag("INR"), rates.getINR()));
        if (rates.getISK() != -1)
            currencyModels.add(new CurrencyModel("ISK", getFlag("ISK"), rates.getISK()));
        if (rates.getJPY() != -1)
            currencyModels.add(new CurrencyModel("JPY", getFlag("JPY"), rates.getJPY()));
        if (rates.getKRW() != -1)
            currencyModels.add(new CurrencyModel("KRW", getFlag("KRW"), rates.getKRW()));
        if (rates.getMXN() != -1)
            currencyModels.add(new CurrencyModel("MXN", getFlag("MXN"), rates.getMXN()));
        if (rates.getMYR() != -1)
            currencyModels.add(new CurrencyModel("MYR", getFlag("MYR"), rates.getMYR()));
        if (rates.getNOK() != -1)
            currencyModels.add(new CurrencyModel("NOK", getFlag("NOK"), rates.getNOK()));
        if (rates.getNZD() != -1)
            currencyModels.add(new CurrencyModel("NZD", getFlag("NZD"), rates.getNZD()));
        if (rates.getPHP() != -1)
            currencyModels.add(new CurrencyModel("PHP", getFlag("PHP"), rates.getPHP()));
        if (rates.getPLN() != -1)
            currencyModels.add(new CurrencyModel("PLN", getFlag("PLN"), rates.getPLN()));
        if (rates.getRON() != -1)
            currencyModels.add(new CurrencyModel("RON", getFlag("RON"), rates.getRON()));
        if (rates.getRUB() != -1)
            currencyModels.add(new CurrencyModel("RUB", getFlag("RUB"), rates.getRUB()));
        if (rates.getSEK() != -1)
            currencyModels.add(new CurrencyModel("SEK", getFlag("SEK"), rates.getSEK()));
        if (rates.getSGD() != -1)
            currencyModels.add(new CurrencyModel("SGD", getFlag("SGD"), rates.getSGD()));
        if (rates.getTHB() != -1)
            currencyModels.add(new CurrencyModel("THB", getFlag("THB"), rates.getTHB()));
        if (rates.getTRY() != -1)
            currencyModels.add(new CurrencyModel("TRY", getFlag("TRY"), rates.getTRY()));
        if (rates.getUSD() != -1)
            currencyModels.add(new CurrencyModel("USD", getFlag("USD"), rates.getUSD()));
        if (rates.getZAR() != -1)
            currencyModels.add(new CurrencyModel("ZAR", getFlag("ZAR"), rates.getZAR()));
        if (rates.getEUR() != -1)
            currencyModels.add(new CurrencyModel("EUR", getFlag("EUR"), rates.getEUR()));

    }

    public ArrayList<CurrencyModel> getCurrencyModels() {
        return currencyModels;
    }
}
