package com.example.weatherapp.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.weatherapp.R;
import com.example.weatherapp.base.BaseActivity;
import com.example.weatherapp.databinding.WeatherCityLocationBinding;
import com.example.weatherapp.model.citylocationmodel.CitiesLocationWeather;
import com.example.weatherapp.model.citylocationmodel.CityWeatherInfo;
import com.example.weatherapp.utils.Resource;
import com.example.weatherapp.utils.Validation;
import com.example.weatherapp.view.adapter.CityLocationAdapter;
import com.example.weatherapp.viewmodel.CityLocationViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.example.weatherapp.utils.Constants.LAT;
import static com.example.weatherapp.utils.Constants.LNG;

public class WeatherCityLocationActivity extends BaseActivity<WeatherCityLocationBinding, CityLocationViewModel> {

    private String lat, lng;
    private CityLocationAdapter adapter;
    private List<CityWeatherInfo> items = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lat = getIntent().getStringExtra(LAT);
        lng = getIntent().getStringExtra(LNG);

        adapter = new CityLocationAdapter(this,items);
        dataBinding.recycler.setAdapter(adapter);
        dataBinding.recycler.setLayoutManager(new LinearLayoutManager(this));
        getCityData();
    }

    @Override
    protected int layoutRes() {
        return R.layout.weather_city_location;
    }

    @Override
    protected Class<CityLocationViewModel> getViewModel() {
        return CityLocationViewModel.class;
    }

    private void getCityData() {
        if (!Validation.isNetworkAvailable(this)) {
            Toast.makeText(this, R.string.check_internet_connection, Toast.LENGTH_SHORT).show();
            return;
        }
        dataBinding.progress.setVisibility(View.VISIBLE);
        viewModel.getCityWeather(lat, lng);
        viewModel.getCityLocationLiveData().observe(this, new Observer<Resource<CitiesLocationWeather>>() {
            @Override
            public void onChanged(Resource<CitiesLocationWeather> citiesLocationWeatherResource) {
                dataBinding.progress.setVisibility(View.GONE);
                if (citiesLocationWeatherResource.status == Resource.Status.SUCCESS) {
                    dataBinding.setData(citiesLocationWeatherResource.data);
                    if (citiesLocationWeatherResource.data != null) {
                        adapter.updateAll(citiesLocationWeatherResource.data.getCityWeatherInfoLs());
                    }
                } else {
                    Toast.makeText(WeatherCityLocationActivity.this, R.string.somthing_wrong, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
