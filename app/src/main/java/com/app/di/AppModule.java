package com.app.di;

import com.app.BuildConfig;
import com.app.utiles.Constants;
import com.app.network.RestrictedSocketFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    @Singleton
    @Provides
    static Retrofit provideRetrofitInstance(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        int bufferSize = 256*1024;
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                //.addInterceptor(interceptor)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .socketFactory(new RestrictedSocketFactory(bufferSize));
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        if (BuildConfig.DEBUG) {
            // do something for a debug build
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        }else
            logging.setLevel(HttpLoggingInterceptor.Level.NONE);
        okHttpClientBuilder.addInterceptor(logging);

        return new Retrofit.Builder().baseUrl(Constants.BASE_URL)
//                .addConverterFactory(MoshiConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(okHttpClientBuilder.build())
//                .client(OkHttpProvider.getOkHttpInstance())

                .build();
    }


}
