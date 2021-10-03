package com.metacoders.e_proshashonadmin;

import android.app.Application;

import com.metacoders.e_proshashonadmin.Const.Const;
import com.onesignal.OneSignal;

public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(Const.ONESIGNAL_APP_ID);
        OneSignal.sendTag("user_type", "ADMIN");
    }
}