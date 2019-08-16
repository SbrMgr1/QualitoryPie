package com.qualitorypie.qualitorypie.Services;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static final String  FCM_ACTION = "com.qualitorypie.qualitorypie.Services.CLOUD_MESSAGING";
    private String SENDER_TAG = "fierbase-local-sender";
    private LocalBroadcastManager broadcaster;

    @Override
    public void onCreate() {
        broadcaster = LocalBroadcastManager.getInstance(this);

    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(SENDER_TAG, "Broadcasting message");
        Intent intent = new Intent(FCM_ACTION);
        // intent.putExtra("key", "Sample Data");
        // put your all data using put extra
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        super.onMessageReceived(remoteMessage);


    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.d(SENDER_TAG, "Refreshed token: " + token);
    }
}
