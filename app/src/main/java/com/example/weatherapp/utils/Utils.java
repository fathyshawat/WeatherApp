package com.example.weatherapp.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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


    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        String m;
        if (String.valueOf(month).length() == 1)
            m = "0" + month;
        else
            m = String.valueOf(month);

        String date = year + "-" + m + "-" + day;
        return date;
    }

    public static String increaseDate(String dt, int value) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, value);
        dt = sdf.format(c.getTime());

        return dt;
    }

    public static Date date(String dtStart) {

        Date date = null;
        DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = (Date) formatter2.parse(dtStart);
        } catch (ParseException e) {
            e.printStackTrace();

        }
        return date;
    }

    public static String getDayOfWeek(String date){
        return (String) android.text.format.DateFormat.format("EEEE",Utils.date(date));
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
