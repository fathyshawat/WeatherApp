package com.example.weatherapp.base;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity<D extends ViewDataBinding, V extends ViewModel> extends DaggerAppCompatActivity {


    @Inject
    protected ViewModelProvider.Factory viewModelFactory;
    protected D dataBinding;
    protected V viewModel;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, layoutRes());
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModel());
    }

    @LayoutRes
    protected abstract int layoutRes();

    protected abstract Class<V> getViewModel();


}
