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
import com.app.service.TimerService;
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
        startService(new Intent(this, TimerService.class));
        activityMainBinding.setMainViewModel(viewModel);
        activityMainBinding.rvMain.setHasFixedSize(true);
        activityMainBinding.rvMain.setItemViewCacheSize(30);
        activityMainBinding.rvMain.setDrawingCacheEnabled(true);
        activityMainBinding.rvMain.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        linearLayoutManager = new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        activityMainBinding.rvMain.setLayoutManager(linearLayoutManager);

        setEvent();
        stopService(new Intent(this, TimerService.class));
    }

    boolean setBefore = false;

    private void setEvent() {
        viewModel.mMutableLiveData.observe((LifecycleOwner) this, new Observer<Object>() {
            @Override
            public void onChanged(@Nullable Object o) {
                String action = (String) o;
                if (action.equals(Constants.CURRENCY)) {
                    if (!setBefore) {
                        currencyAdapter = new CurrencyAdapter(viewModel.currencies, viewModel.base, viewModel.qty);
                        activityMainBinding.rvMain.setAdapter(currencyAdapter);
                        setEventAdapter();
                        setBefore = true;
                    } else {
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
                    viewModel.currencyModelSelect = viewModel.currencies.get(qtySubmit.getPosition());
                    viewModel.currencyModelSelect.setPrice(viewModel.qty * viewModel.currencyModelSelect.getPrice());
                    viewModel.qty = viewModel.currencyModelSelect.getPrice() * qtySubmit.getQty();
                    viewModel.base = viewModel.currencyModelSelect.getName();
                    viewModel.pos = qtySubmit.getPosition();
                    currencyAdapter.swap(qtySubmit.getPosition());
                    activityMainBinding.rvMain.smoothScrollToPosition(0);
                    linearLayoutManager.scrollToPositionWithOffset(0, 0);
                } else if (qtySubmit.getType().equals(Constants.TEXT_CHANGE)) {
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
