package com.inventors.jd.keeper;

import android.app.Application;

import com.inventors.jd.keeper.receivers.ConnectivityReceiver;

/**
 * Created by jd on 16-Aug-18.
 */

public class MyApplication extends Application {

    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}
