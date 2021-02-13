package com.example.weatherapp.utils;

import android.content.Context;
import android.util.Log;


import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Utils {

    private static String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("city.list.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static Observable<HashMap<String, Integer>> saveCountries(Context context) {
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();

        return Observable
                .defer(new Callable<ObservableSource<? extends HashMap<String, Integer>>>() {
                    @Override
                    public ObservableSource<? extends HashMap<String, Integer>> call() throws Exception {
                        try {
                            JSONArray mJArray = new JSONArray(loadJSONFromAsset(context));

                            for (int i = 0; i < mJArray.length(); i++) {
                                JSONObject jo_inside = mJArray.getJSONObject(i);
                                int id = jo_inside.getInt("id");
                                String city = jo_inside.getString("name").toLowerCase();

                                if (hashMap.containsKey(city))
                                    continue;
                                hashMap.put(city, id);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return Observable.just(hashMap);
                    }
                });

    }

    /**
     * convert name of cities to ids to use it in Api like
     * cairo,roma --> 15235,54215
     */
    public static String getIdsOfCities(String cities, HashMap<String, Integer> hashMap) {


        StringBuilder ids = new StringBuilder();
        String[] myCities = cities.split(",");
        for (int i = 0; i < myCities.length; i++) {

            ids.append(hashMap.get(myCities[i].trim().toLowerCase()));
            ids.append(",");
        }

        String idsCities = String.valueOf(ids);

        if (idsCities != null && idsCities.length() > 0 && idsCities.charAt(idsCities.length() - 1) == ',') {
            idsCities = idsCities.substring(0, idsCities.length() - 1);
        }
        return idsCities;
    }

    public static int getIdsOfCities(String cities) {
        String[] myCities = cities.split(",");
        return myCities.length;
    }


}
