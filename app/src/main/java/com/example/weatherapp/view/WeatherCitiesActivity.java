package com.example.weatherapp.view;


import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.weatherapp.R;
import com.example.weatherapp.base.BaseActivity;
import com.example.weatherapp.databinding.SelectionCitiesBinding;
import com.example.weatherapp.model.CitiesWeather;
import com.example.weatherapp.utils.DialogUtil;
import com.example.weatherapp.utils.Resource;
import com.example.weatherapp.utils.Utils;
import com.example.weatherapp.utils.Validation;
import com.example.weatherapp.view.adapter.CitiesAdapter;
import com.example.weatherapp.viewmodel.MultiCitiesViewModel;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class WeatherCitiesActivity extends BaseActivity<SelectionCitiesBinding, MultiCitiesViewModel> {


    private HashMap<String, Integer> citiesHashMap = new HashMap<>();
    private String cities, token;
    private List<CitiesWeather> citiesLs = new ArrayList<>();
    private CitiesAdapter adapter;
    private CompositeDisposable disposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        DialogUtil.showProgressDialog(this);
        Utils.saveCountries(this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.Observer<HashMap<String, Integer>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull HashMap<String, Integer> hashMap) {
                        citiesHashMap = hashMap;
                        DialogUtil.hideProgressDialog();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        DialogUtil.hideProgressDialog();

                    }

                    @Override
                    public void onComplete() {
                    }
                });

        adapter = new CitiesAdapter(citiesLs);
        dataBinding.citiesRecyclerView.setAdapter(adapter);
        dataBinding.citiesRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        dataBinding.submit.setOnClickListener(v -> {
            Utils.hideKeyboard(WeatherCitiesActivity.this);
            getCities();
        });


    }

    @Override
    protected int layoutRes() {
        return R.layout.selection_cities;
    }

    @Override
    protected Class<MultiCitiesViewModel> getViewModel() {
        return MultiCitiesViewModel.class;
    }


    private void getCities() {
        cities = Utils.getIdsOfCities(dataBinding.citiesEd.getText().toString().toLowerCase().trim(), citiesHashMap);
        if (!Validation.isNetworkAvailable(this)) {
            Toast.makeText(this, R.string.check_internet_connection, Toast.LENGTH_SHORT).show();
            return;
        } else if (dataBinding.citiesEd.getText().toString().isEmpty()) {
            dataBinding.citiesEd.setError(getString(R.string.write_cities));
            return;
        } else if (Utils.getIdsOfCities(cities) < 3) {
            Toast.makeText(this, R.string.min_of_cities, Toast.LENGTH_SHORT).show();
            return;
        } else if (Utils.getIdsOfCities(cities) > 7) {
            Toast.makeText(this, R.string.max_of_cities, Toast.LENGTH_SHORT).show();
            return;
        }
        dataBinding.progressBar.setVisibility(View.VISIBLE);
        token = getString(R.string.api_token);
        viewModel.getCities(cities, token);
        viewModel.getCitiesWeatherLiveData().
                observe(this, new Observer<Resource<List<CitiesWeather>>>() {
                    @Override
                    public void onChanged(Resource<List<CitiesWeather>> listResource) {
                        dataBinding.progressBar.setVisibility(View.GONE);
                        if (listResource.status == Resource.Status.SUCCESS) {
                            adapter.updateAll(listResource.data);
                        } else {
                            Toast.makeText(WeatherCitiesActivity.this, R.string.somthing_wrong, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}