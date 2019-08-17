package com.qualitorypie.qualitorypie.Activities;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.qualitorypie.qualitorypie.Services.MyFirebaseMessagingService;


public class BaseActivity extends AppCompatActivity {
    private final String TAG = "BaseActivity";


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG,"now in message in activity");
        }
    };

    @Override
    protected void onStart() {
        Log.d(TAG,"onStart");
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mMessageReceiver, new IntentFilter(MyFirebaseMessagingService.FCM_ACTION)
        );
    }

    @Override
    protected void onStop() {
        Log.d(TAG,"onStop");
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }
}
