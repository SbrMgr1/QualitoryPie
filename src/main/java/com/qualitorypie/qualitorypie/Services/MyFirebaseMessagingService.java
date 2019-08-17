package com.qualitorypie.qualitorypie.Services;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.qualitorypie.qualitorypie.DataProviders.SettingDataProvider;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static final String  FCM_ACTION = "com.qualitorypie.qualitorypie.Services.CLOUD_MESSAGING";
    public static final String  FCM_NEW_TOKEN = "com.qualitorypie.qualitorypie.Services.FCM_NEW_TOKEN";

    private String SENDER_TAG = "fierbase-local-sender";
    private LocalBroadcastManager broadcaster;


    @Override
    public void onCreate() {
        broadcaster = LocalBroadcastManager.getInstance(this);

    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Map<String, String> fcm_msg =  remoteMessage.getData();

        Intent message_intent = new Intent(FCM_ACTION);
        for (Map.Entry<String, String> entry : fcm_msg.entrySet()) {
            message_intent.putExtra(entry.getKey(),entry.getValue());
        }

        broadcaster.sendBroadcast(message_intent);
        super.onMessageReceived(remoteMessage);
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);

        Intent token_intent = new Intent(FCM_NEW_TOKEN);
        token_intent.putExtra("new_device_id",token);
        broadcaster.sendBroadcast(token_intent);
    }
}
