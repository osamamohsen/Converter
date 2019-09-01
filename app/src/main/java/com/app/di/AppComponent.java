package com.app.di;

import android.app.Application;

import com.app.app.BaseApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

//destroy all singleton when destroy application , clear memory
@Singleton
//must modules of Android support injection module
@Component(
        modules = {
                AndroidSupportInjectionModule.class,
                ActivityBuildersModule.class,
                AppModule.class,
                ViewModelFactoryModule.class
        }
)
// BaseApplication : consider an client for application , AppComponent consider service
public interface AppComponent extends AndroidInjector<BaseApplication> {

    //Build Object of appComponent
    @Component.Builder
    interface Builder{

        //Binding application in create of AppComponent
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
