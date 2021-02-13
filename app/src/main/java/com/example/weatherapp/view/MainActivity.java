package com.example.weatherapp.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.example.weatherapp.R;
import com.example.weatherapp.databinding.MainScreenBinding;
import com.example.weatherapp.utils.DialogUtil;
import com.example.weatherapp.utils.Validation;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import static com.example.weatherapp.utils.Constants.FINE_LOCATION;
import static com.example.weatherapp.utils.Constants.GPS_ENABLING;
import static com.example.weatherapp.utils.Constants.LAT;
import static com.example.weatherapp.utils.Constants.LNG;

public class MainActivity extends AppCompatActivity {

    private MainScreenBinding dataBinding;


    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation;
    private LocationCallback locationCallback;
    private LocationManager locationManager;

    private String lat, lng;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.main_screen);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        dataBinding.cities.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WeatherCitiesActivity.class);
            startActivity(intent);
        });
        dataBinding.location.setOnClickListener(v -> {
            if (!Validation.isNetworkAvailable(MainActivity.this)) {
                Toast.makeText(this, R.string.check_internet_connection, Toast.LENGTH_SHORT).show();
                return;
            }
            locationPermission();

        });

    }


    private void locationPermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, FINE_LOCATION);
        } else {
            enableGps();
        }
    }

    @SuppressLint("MissingPermission")
    private void getDeviceLocation() {
        mFusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@androidx.annotation.NonNull Task<Location> task) {
                        if (task.isSuccessful()) {

                            mLastKnownLocation = task.getResult();

                            if (mLastKnownLocation != null) {
                                lat = String.valueOf(mLastKnownLocation.getLatitude());
                                lng = String.valueOf(mLastKnownLocation.getLongitude());
                                moveToCityLocationActivity(lat, lng);

                            } else {
                                final LocationRequest locationRequest = LocationRequest.create();
                                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                                locationCallback = new LocationCallback() {
                                    @Override
                                    public void onLocationResult(LocationResult locationResult) {
                                        super.onLocationResult(locationResult);
                                        if (locationResult == null) {
                                            return;
                                        }


                                        mLastKnownLocation = locationResult.getLastLocation();
                                        lat = String.valueOf(mLastKnownLocation.getLatitude());
                                        lng = String.valueOf(mLastKnownLocation.getLongitude());

                                        moveToCityLocationActivity(lat, lng);

                                    }
                                };
                                mFusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);

                            }
                        } else {
                            Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }


    private void moveToCityLocationActivity(String lat, String lng) {
        DialogUtil.hideProgressDialog();
        Intent intent = new Intent(this, WeatherCityLocationActivity.class);
        intent.putExtra(LAT, lat);
        intent.putExtra(LNG, lng);
        startActivity(intent);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GPS_ENABLING) {
            if (resultCode == RESULT_OK) {
                DialogUtil.showProgressDialog(MainActivity.this);
                getDeviceLocation();
            }
        }
    }


    public void enableGps() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        SettingsClient settingsClient = LocationServices.getSettingsClient(MainActivity.this);
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());
        task.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                getDeviceLocation();
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@androidx.annotation.NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException resolve = (ResolvableApiException) e;
                    try {
                        resolve.startResolutionForResult(MainActivity.this, GPS_ENABLING);
                    } catch (IntentSender.SendIntentException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @androidx.annotation.NonNull String[] permissions, @androidx.annotation.NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == FINE_LOCATION) {
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                getDeviceLocation();
            } else {
                enableGps();
            }
        } else {
            finish();
        }
    }

}
