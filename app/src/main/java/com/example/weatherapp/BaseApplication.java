package com.example.weatherapp;


import android.util.Log;

import com.example.weatherapp.di.component.ApplicationComponent;
import com.example.weatherapp.di.component.DaggerApplicationComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class BaseApplication extends DaggerApplication {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        component = DaggerApplicationComponent
                .builder()
                .application(this)
                .build();
        component.inject(this);
        return component;
    }
}
