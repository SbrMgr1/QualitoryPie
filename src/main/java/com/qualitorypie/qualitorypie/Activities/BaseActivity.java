package com.qualitorypie.qualitorypie.Activities;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.qualitorypie.qualitorypie.Services.MyFirebaseMessagingService;


public class BaseActivity extends AppCompatActivity {
    private final String TAG = "BaseActivity";

    LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localBroadcastManager.registerReceiver(broadcast_receiver, new IntentFilter(MyFirebaseMessagingService.FCM_ACTION));
    }


    public BroadcastReceiver broadcast_receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                Log.d(TAG,"message received from local broadcaster");
            }
        }
    };
}
