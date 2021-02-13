package com.example.weatherapp.di.module;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = ViewModelModule.class)
public class NetworkModule {

    @Provides
    @Singleton
    public RequestQueue provideRequestQueue(Application application) {
        return Volley.newRequestQueue(application);
    }

}
