package com.example.weatherapp.di.component;

import android.app.Application;

import com.example.weatherapp.BaseApplication;
import com.example.weatherapp.di.module.ActivityBindingModule;
import com.example.weatherapp.di.module.NetworkModule;
import com.example.weatherapp.di.module.ViewModelFactoryModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

@Singleton
@Component(modules = {AndroidInjectionModule.class, ActivityBindingModule.class,
        NetworkModule.class, ViewModelFactoryModule.class})
public interface ApplicationComponent extends AndroidInjector<DaggerApplication> {


    void inject(BaseApplication application);


    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
    }

}
