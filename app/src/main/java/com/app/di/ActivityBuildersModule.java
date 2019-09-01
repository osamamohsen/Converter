package com.app.di;

import com.app.ui.MainActivity;
import com.app.di.main.MainModule;
import com.app.di.main.MainViewModelsModule;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

//potential client to inject into
@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
            modules = {MainViewModelsModule.class , MainModule.class }
    )
    abstract MainActivity contributeAuthActivity();

}
