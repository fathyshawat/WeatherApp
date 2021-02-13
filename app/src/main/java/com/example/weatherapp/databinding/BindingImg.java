package com.example.weatherapp.databinding;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.example.weatherapp.repository.Urls;
import com.squareup.picasso.Picasso;

public class BindingImg {


    @BindingAdapter("android:src")
    public static void setImageViewResource(ImageView imageView, String img) {
        String urlImg = Urls.IMG_URL + img;
        Picasso.get().load(urlImg).into(imageView);
    }
}
