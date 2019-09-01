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
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ParentViewModel implements Subscription{
    Api api;
    private static final String TAG = "MainViewModel";
    public ArrayList<CurrencyModel> currencies = new ArrayList<>();
    private CurrencyHelper currencyHelper = new CurrencyHelper();
    private CurrenciesResponse currenciesResponse = new CurrenciesResponse();
    public Double qty = 1.00;
    public CurrencyModel currencyModelSelect = new CurrencyModel("EUR", ResourceManager.getDrawable(R.drawable.ic_eur_flag),qty);
    public String base = "EUR";
    public int pos = -1;
    boolean stopTest = true;

    @Inject
    public MainViewModel(Api api) {
        this.api = api;
        getCurrency();
//        startTimer();
    }

    public AtomicBoolean resumed = new AtomicBoolean();
    public AtomicBoolean stopped = new AtomicBoolean();


    public void startTimer() { //Create and starts timper
        resumed.set(true);
        stopped.set(false);
        Flowable.interval(10, TimeUnit.SECONDS)
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
                        Log.e("exc", throwable.getMessage());
                        mMutableLiveData.postValue(Constants.HIDE_LOADER);
                        mMutableLiveData.postValue(Constants.FAILURE);
                    }
                });
    }


    public CurrenciesResponse getCurrenciesResponses() {
        return currenciesResponse;
    }

    public void getCurrency() {
        EspressoIdlingResource.increment();
        compositeDisposable.add(api.getCurrencies(base)
                .subscribeOn(Schedulers.io())
                .debounce(1,TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CurrenciesResponse>() {
                            @Override
                            public void accept(final CurrenciesResponse resp) throws Exception {
                                if (resp != null && resp.getRates() != null) {
                                    Log.e(TAG, "accept: Done" );
                                    currencyHelper.setCurrencyData(resp.getRates());
                                    currencies = currencyHelper.getCurrencyModels();
                                    currencies.add(0,currencyModelSelect);
                                    if(pos != -1) currencies.remove(pos);
                                    mMutableLiveData.postValue(Constants.CURRENCY);
//                                    notifyChange();
                                    EspressoIdlingResource.decrement();
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.e("exc", throwable.getMessage());
                                mMutableLiveData.postValue(Constants.HIDE_LOADER);
                                mMutableLiveData.postValue(Constants.FAILURE);
                            }
                        })

        );

//        new Consumer<CurrenciesResponse>() {
//            @Override
//            public void accept(final CurrenciesResponse resp) throws Exception {
//                if (resp != null && resp.getRates() != null) {
//                    Log.e(TAG, "accept: Done" );
//                    currencyHelper.setCurrencyData(resp.getRates());
//                    currencies = currencyHelper.getCurrencyModels();
//                    currencies.add(0,currencyModelSelect);
//                    if(pos != -1) currencies.remove(pos);
//                    mMutableLiveData.postValue(Constants.CURRENCY);
////                                    notifyChange();
//                }
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                Log.e("exc", throwable.getMessage());
//                mMutableLiveData.postValue(Constants.HIDE_LOADER);
//                mMutableLiveData.postValue(Constants.FAILURE);
//            }
//        })
    }

    @Bindable
    public List<CurrencyModel> getCurrencyData() {
        return this.currencies;
    }



    public void reset() {
        stopped.set(true);
        resumed.set(false);
        unSubscribeFromObservable();
        compositeDisposable = null;
    }

    @Override
    public void request(long n) {

    }

    @Override
    public void cancel() {

    }
}
