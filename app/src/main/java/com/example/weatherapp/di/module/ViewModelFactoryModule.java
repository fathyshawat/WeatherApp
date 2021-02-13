package com.example.weatherapp.di.module;

import androidx.lifecycle.ViewModelProvider;

import com.example.weatherapp.viewmodel.ViewModelFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}