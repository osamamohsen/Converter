package com.app.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.app.models.CurrenciesResponse;
import com.app.network.Api;
import com.app.ui.MainActivity;
import com.app.utiles.Constants;
import com.app.utiles.espresso.EspressoIdlingResource;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class TimerService extends IntentService {
    private static final String TAG = "TimerService";
    public static final int NOTIFICATION_ID = 234;


    private Handler mHandler;

    @Inject
    Api api;

    public TimerService() {
        super("SampleService");
        Log.e(TAG, "TimerService: "+"welcome" );
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate: "+"STAT" );
        AndroidInjection.inject(this);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mHandler = new Handler();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(api != null)
            Log.e(TAG, "onHandleIntent: not null" );
        else
            Log.e(TAG, "onHandleIntent: null" );
    }

//                    mHandler.post(new Runnable() {
//        @Override
//        public void run() {
//            Toast.makeText(getApplicationContext(), "Service Completed" , Toast.LENGTH_SHORT).show();
//        }
//    });
}