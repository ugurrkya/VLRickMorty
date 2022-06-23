package com.ugurkaya.vlrickmorty;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.multidex.MultiDex;


public class AnalyticsApplication extends Application {


    private static AnalyticsApplication analyticsApplication;



    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onCreate() {
        super.onCreate();
        if (analyticsApplication == null) {
            analyticsApplication = this;
        }


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static AnalyticsApplication getInstance() {
        return analyticsApplication;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean hasNetwork() {
        return analyticsApplication.isNetworkConnected();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean isNetworkConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}