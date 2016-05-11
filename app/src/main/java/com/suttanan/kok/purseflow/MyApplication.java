package com.suttanan.kok.purseflow;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

/**
 * Created by KOKKOK on 5/10/2016.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize the SDK before executing any other operations,
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
}
