package com.app.di.main;

import com.app.network.Api;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {
    @Provides
    static Api provideMainApi(Retrofit retrofit){
        return retrofit.create(Api.class);
    }
}
