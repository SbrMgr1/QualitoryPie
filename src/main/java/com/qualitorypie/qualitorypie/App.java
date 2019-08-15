package com.qualitorypie.qualitorypie;

import android.app.Application;
import android.util.Log;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        createNotification();
    }

    private void createNotification() {
        Log.d("Inside App","i am here");
    }
}
