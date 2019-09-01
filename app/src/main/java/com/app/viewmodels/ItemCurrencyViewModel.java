package com.app.viewmodels;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.app.models.CurrencyModel;
import com.app.models.QtySubmit;
import com.app.utiles.Constants;

import java.text.DecimalFormat;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableBoolean;


public class ItemCurrencyViewModel extends ParentViewModel {

    private static final String TAG = "ItemCurrencyViewModel";
    public ObservableBoolean isVisible = new ObservableBoolean(true);
    private CurrencyModel currencyModel;
    private String text;
    private int position;
    private Double qty;


    public ItemCurrencyViewModel(CurrencyModel currencyModel , int position,Double qty) {
        this.currencyModel = currencyModel;
        this.position = position;
        this.qty = qty;
    }

    private static DecimalFormat df2 = new DecimalFormat("##.####");


    public String getText(){
        if(position!=0)
            return String.valueOf(df2.format(currencyModel.getPrice()*qty));
        else
            return String.valueOf(df2.format(currencyModel.getPrice()));
    }

    @BindingAdapter("text")
    public static void setText(EditText editText, String text) {
        editText.setText(text);
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(position == 0) {
            if(count == 0) s="0.0";
            Log.e(TAG, "onTextChanged: "+count );
            mMutableLiveData.postValue(new QtySubmit(Constants.TEXT_CHANGE,position,Double.parseDouble(s.toString())));
        }
    }

    public CurrencyModel getCurrencyModel() {
        return currencyModel;
    }

    public void setCurrencyModel(CurrencyModel currencyModel) {
        this.currencyModel = currencyModel;
    }

    public Drawable getImageUrl(){
        return currencyModel.getDrawable();
    }

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView imageView, Drawable drawable) {
        imageView.setImageDrawable(drawable);
    }

    public void selectCurrency(){
        if(position != 0)
            mMutableLiveData.postValue(new QtySubmit(Constants.SUBMIT,position,qty));
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void reset() {
        unSubscribeFromObservable();
        compositeDisposable = null;
    }
}
