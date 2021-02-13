package com.example.weatherapp.repository;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.weatherapp.model.CitiesWeather;
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
import static com.example.weatherapp.repository.Urls.ID;
import static com.example.weatherapp.utils.Constants.CONVERT_TO_CELSIUS;

public class MultiCitiesRepository {


    private final RequestQueue requestQueue;
    private MutableLiveData<Resource<List<CitiesWeather>>> citiesWeatherLiveData = new MutableLiveData<>();

    @Inject
    MultiCitiesRepository(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    public MutableLiveData<Resource<List<CitiesWeather>>> getMultiCities(String cities) {
        String url = BASE_URL + "group?" + APPID + API_TOKEN + "&" + ID + cities;
        JsonObjectRequest
                request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            List<CitiesWeather> citiesWeathersLs = new ArrayList<>();
                            JSONArray jsonArray = response.getJSONArray("list");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject weather = jsonArray.getJSONObject(i);
                                double minTemp = weather.getJSONObject("main").getDouble("temp_min") - CONVERT_TO_CELSIUS;
                                double maxTemp = weather.getJSONObject("main").getDouble("temp_max") - CONVERT_TO_CELSIUS;
                                double windSpeed = weather.getJSONObject("wind").getDouble("speed");
                                String name = weather.getString("name");
                                StringBuilder description = new StringBuilder();
                                JSONArray jsonArrayWeather = weather.getJSONArray("weather");
                                String icon = "";
                                for (int j = 0; j < jsonArrayWeather.length(); j++) {
                                    JSONObject descriptionObject = jsonArrayWeather.getJSONObject(j);
                                    description.append(descriptionObject.getString("description"));
                                    description.append(" ");
                                    icon = descriptionObject.getString("icon") + ".png";
                                }
                                citiesWeathersLs.add(new CitiesWeather(name, icon, minTemp, maxTemp,
                                        String.valueOf(description), windSpeed));
                            }
                            citiesWeatherLiveData.setValue(Resource.success(citiesWeathersLs));

                        } catch (JSONException e) {
                            e.printStackTrace();
                            citiesWeatherLiveData.setValue(Resource.error(e.getMessage(), null));

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                citiesWeatherLiveData.setValue(Resource.error(error.getMessage(), null));

            }
        });
        requestQueue.add(request);
        return citiesWeatherLiveData;
    }


}
