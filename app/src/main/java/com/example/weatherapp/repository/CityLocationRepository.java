package com.example.weatherapp.repository;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.weatherapp.model.citylocationmodel.CitiesLocationWeather;
import com.example.weatherapp.model.citylocationmodel.CityWeatherInfo;
import com.example.weatherapp.utils.Resource;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.example.weatherapp.repository.Urls.API_TOKEN;
import static com.example.weatherapp.repository.Urls.APPID;
import static com.example.weatherapp.repository.Urls.BASE_URL;
import static com.example.weatherapp.repository.Urls.LAT;
import static com.example.weatherapp.repository.Urls.LNG;
import static com.example.weatherapp.utils.Constants.CONVERT_TO_CELSIUS;

public class CityLocationRepository {


    private final RequestQueue requestQueue;
    private MutableLiveData<Resource<CitiesLocationWeather>> cityLocationLiveData = new MutableLiveData<>();

    @Inject
    CityLocationRepository(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    public MutableLiveData<Resource<CitiesLocationWeather>> getCityLocationWeather(String lat, String lng) {
        String url = BASE_URL + "forecast?" + APPID + API_TOKEN + "&" + LAT + lat + "&" + LNG + lng;
        JsonObjectRequest
                request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            List<CityWeatherInfo> cityWeatherInfoLs = new ArrayList<>();


                            String cityName = response.getJSONObject("city").getString("name");
                            String countryName = response.getJSONObject("city").getString("country");
                            JSONArray jsonArray = response.getJSONArray("list");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject weather = jsonArray.getJSONObject(i);
                                double minTemp = weather.getJSONObject("main").getDouble("temp_min") - CONVERT_TO_CELSIUS;
                                double maxTemp = weather.getJSONObject("main").getDouble("temp_max") - CONVERT_TO_CELSIUS;
                                double windSpeed = weather.getJSONObject("wind").getDouble("speed");
                                String date = weather.getString("dt_txt");
                                String dt = date.split(" ")[0];
                                StringBuilder description = new StringBuilder();
                                JSONArray jsonArrayWeather = weather.getJSONArray("weather");
                                String icon = "";
                                for (int j = 0; j < jsonArrayWeather.length(); j++) {
                                    JSONObject descriptionObject = jsonArrayWeather.getJSONObject(j);
                                    description.append(descriptionObject.getString("description"));
                                    description.append(" ");
                                    icon = descriptionObject.getString("icon") + ".png";
                                }
                                cityWeatherInfoLs.add(new CityWeatherInfo(minTemp,maxTemp,windSpeed, icon, String.valueOf(description), dt));
                            }
                            CitiesLocationWeather citiesLocationWeather = new CitiesLocationWeather(cityName, countryName, cityWeatherInfoLs);
                            cityLocationLiveData.setValue(Resource.success(citiesLocationWeather));

                        } catch (JSONException e) {
                            e.printStackTrace();
                            cityLocationLiveData.setValue(Resource.error(e.getMessage(), null));

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                cityLocationLiveData.setValue(Resource.error(error.getMessage(), null));

            }
        });
        requestQueue.add(request);
        return cityLocationLiveData;
    }


}
