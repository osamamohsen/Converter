package com.app.viewmodels;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;

import com.app.R;
import com.app.adapter.CurrencyAdapter;
import com.app.models.CurrenciesResponse;
import com.app.models.CurrencyModel;
import com.app.utiles.Constants;
import com.app.network.Api;
import com.app.utiles.CurrencyHelper;
import com.app.utiles.espresso.EspressoIdlingResource;
import com.app.utiles.resources.ResourceManager;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.Observable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ParentViewModel {
    Api api;
    public ObservableBoolean progress = new ObservableBoolean(true);
    public ObservableField checkConnection = new ObservableField<>("please check your connection");
    public ObservableBoolean checkConnectionVisiblity = new ObservableBoolean(false);
    public ArrayList<CurrencyModel> currencies = new ArrayList<>();
    private CurrencyHelper currencyHelper = new CurrencyHelper();
    private CurrenciesResponse currenciesResponse = new CurrenciesResponse();
    public Double qty = 1.00;
    public CurrencyModel currencyModelSelect = new CurrencyModel("EUR", ResourceManager.getDrawable(R.drawable.ic_eur_flag),qty);
    public String base = "EUR";
    public int pos = -1;
    public boolean setBefore = false;

    @Inject
    public MainViewModel(Api api) {
        this.api = api;
        startTimer();
    }

    //control to stop service
    public AtomicBoolean resumed = new AtomicBoolean();
    public AtomicBoolean stopped = new AtomicBoolean();


    //call service every second
    public void startTimer() { //Create and starts timper
        resumed.set(true);
        stopped.set(false);
        Flowable.interval(1, TimeUnit.SECONDS)
                .takeWhile(tick -> !stopped.get())
                .filter(tick -> resumed.get())
                .map(o -> getCurrenciesResponses())
                .subscribe(new Consumer<CurrenciesResponse>() {
                    @Override
                    public void accept(final CurrenciesResponse resp) throws Exception {
                        getCurrency();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mMutableLiveData.postValue(Constants.HIDE_LOADER);
                        mMutableLiveData.postValue(Constants.FAILURE);
                    }
                });
    }


    public CurrenciesResponse getCurrenciesResponses() {
        return currenciesResponse;
    }

    public void getCurrency() {
        if(!setBefore) progress.set(true);
        checkConnectionVisiblity.set(false);
        compositeDisposable.add(api.getCurrencies(base)
                .subscribeOn(Schedulers.io())
                .debounce(1,TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CurrenciesResponse>() {
                            @Override
                            public void accept(final CurrenciesResponse resp) throws Exception {
                                progress.set(false);
                                checkConnectionVisiblity.set(false);
                                if (resp != null && resp.getRates() != null) {
                                    currencyHelper.setCurrencyData(resp.getRates());//set data
                                    currencies = currencyHelper.getCurrencyModels();//set countries models
                                    currencies.add(0,currencyModelSelect);//add current currency country
                                    if(pos != -1) currencies.remove(pos);//remove my current editable from list
                                    mMutableLiveData.postValue(Constants.CURRENCY);
//                                    notifyChange();
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                progress.set(false);
                                if(!setBefore) checkConnectionVisiblity.set(true);
                                mMutableLiveData.postValue(Constants.HIDE_LOADER);
                                mMutableLiveData.postValue(Constants.FAILURE);
                            }
                        })

        );

    }

    @Bindable
    public List<CurrencyModel> getCurrencyData() {
        return this.currencies;
    }



    //stop service
    public void reset() {
        stopped.set(true);
        resumed.set(false);
        unSubscribeFromObservable();
        compositeDisposable = null;
    }
}
