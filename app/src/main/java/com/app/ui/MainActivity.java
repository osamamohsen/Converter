package com.app.ui;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.annotations.Nullable;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.app.R;
import com.app.adapter.CurrencyAdapter;
import com.app.databinding.ActivityMainBinding;
import com.app.models.QtySubmit;
import com.app.utiles.Constants;
import com.app.utiles.WrapContentLinearLayoutManager;
import com.app.viewmodels.MainViewModel;
import com.app.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

public class MainActivity extends DaggerAppCompatActivity {


    @Inject
    ViewModelProviderFactory providerFactory;

    MainViewModel viewModel;
    ActivityMainBinding activityMainBinding;
    LinearLayoutManager linearLayoutManager;
    CurrencyAdapter currencyAdapter;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this, providerFactory).get(MainViewModel.class);
        activityMainBinding.setMainViewModel(viewModel);
        activityMainBinding.rvMain.setHasFixedSize(true);
        activityMainBinding.rvMain.setItemViewCacheSize(30);
        activityMainBinding.rvMain.setDrawingCacheEnabled(true);
        activityMainBinding.rvMain.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        linearLayoutManager = new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        activityMainBinding.rvMain.setLayoutManager(linearLayoutManager);

        setEvent();
    }


    private void setEvent() {
        viewModel.mMutableLiveData.observe((LifecycleOwner) this, new Observer<Object>() {
            @Override
            public void onChanged(@Nullable Object o) {
                String action = (String) o;
                if (action.equals(Constants.CURRENCY)) {
                    //adapter not set before for recycler
                    if (!viewModel.setBefore) {
                        //set data first time
                        currencyAdapter = new CurrencyAdapter(viewModel.currencies, viewModel.base, viewModel.qty);
                        activityMainBinding.rvMain.setAdapter(currencyAdapter);
                        setEventAdapter();//listen for event of recycler
                        viewModel.setBefore = true;//recycler had been set and update list if you come again
                    } else {
                        //set new data
                        currencyAdapter.setData(viewModel.currencies, viewModel.qty);
                    }
                }
            }
        });
    }

    private static final String TAG = "MainActivity";

    private void setEventAdapter() {
        currencyAdapter.mMutableLiveData.observe((LifecycleOwner) this, new Observer<Object>() {
            @Override
            public void onChanged(@androidx.annotation.Nullable Object aVoid) {
                QtySubmit qtySubmit = (QtySubmit) aVoid;
                if (qtySubmit.getType().equals(Constants.SUBMIT)) {
                    //move my select to current editable
                    viewModel.currencyModelSelect = viewModel.currencies.get(qtySubmit.getPosition());
                    viewModel.currencyModelSelect.setPrice(viewModel.qty * viewModel.currencyModelSelect.getPrice());
                    //fetch unit price for currency
                    viewModel.qty = viewModel.currencyModelSelect.getPrice() * qtySubmit.getQty();
                    viewModel.base = viewModel.currencyModelSelect.getName();
                    viewModel.pos = qtySubmit.getPosition();
                    //swap between my select and position 0
                    currencyAdapter.swap(qtySubmit.getPosition());
                    //move to top recycler
                    activityMainBinding.rvMain.smoothScrollToPosition(0);
                    linearLayoutManager.scrollToPositionWithOffset(0, 0);
                } else if (qtySubmit.getType().equals(Constants.TEXT_CHANGE)) {
                    //change unit price
                    viewModel.qty = qtySubmit.getQty();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (viewModel != null) {
            viewModel.reset();
        }
        super.onDestroy();
    }
}
