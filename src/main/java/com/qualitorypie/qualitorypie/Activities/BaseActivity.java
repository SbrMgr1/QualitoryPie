package com.qualitorypie.qualitorypie.Activities;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.qualitorypie.qualitorypie.Services.MyFirebaseMessagingService;


public class BaseActivity extends AppCompatActivity {
    private final String TAG = "BaseActivity";


    private BroadcastReceiver fcm_action_receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG,intent.getStringExtra("note_type"));
        }
    };

    private BroadcastReceiver fcm_new_token_note_receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG,"new token received");
        }
    };

    @Override
    protected void onStart() {
        Log.d(TAG,"onStart");
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver(
                fcm_action_receiver, new IntentFilter(MyFirebaseMessagingService.FCM_ACTION)
        );
        LocalBroadcastManager.getInstance(this).registerReceiver(
                fcm_new_token_note_receiver, new IntentFilter(MyFirebaseMessagingService.FCM_NEW_TOKEN)
        );
    }

    @Override
    protected void onStop() {
        Log.d(TAG,"onStop");
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(fcm_action_receiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(fcm_new_token_note_receiver);
    }
}
