package com.example.weatherapp.di.module;

import androidx.lifecycle.ViewModel;

import com.example.weatherapp.viewmodel.CityLocationViewModel;
import com.example.weatherapp.viewmodel.MultiCitiesViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MultiCitiesViewModel.class)
    abstract ViewModel bindsMultiCitiesViewModel(MultiCitiesViewModel multiCitiesViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(CityLocationViewModel.class)
    abstract ViewModel bindsCityLocationViewModel(CityLocationViewModel locationViewModel);


}
