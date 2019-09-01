package com.app.di;

import com.app.service.TimerService;
import com.app.ui.MainActivity;
import com.app.di.main.MainModule;
import com.app.di.main.MainViewModelsModule;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

//potention client to inject into
@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
            modules = {MainViewModelsModule.class , MainModule.class }
    )
    abstract MainActivity contributeAuthActivity();
    abstract TimerService contributeTimerService();

}
