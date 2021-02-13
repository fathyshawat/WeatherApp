package com.example.weatherapp.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.example.weatherapp.R;

public class DialogUtil {
    private static Dialog dialog;

    public static void showProgressDialog(Context context) {
        if (dialog == null) {
            dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
            View view = ((Activity) context).getLayoutInflater().inflate(R.layout.progress_layout, null);
            dialog.setCancelable(false);
            dialog.setContentView(view);
            dialog.show();
        }
    }

    public static void hideProgressDialog() {
        if (dialog != null)
            dialog.dismiss();
    }


}
