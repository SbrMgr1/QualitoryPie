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



import com.qualitorypie.qualitorypie.Services.MyFirebaseMessagingService;
import java.net.URISyntaxException;
import java.util.EventListener;


import helpers.ApiHelper;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class BaseActivity extends AppCompatActivity {
    private final String TAG = "BaseActivity";

    private BroadcastReceiver fcm_action_receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, intent.getStringExtra("note_type"));
        }
    };

    private BroadcastReceiver fcm_new_token_note_receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "new token received");
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver(
                fcm_action_receiver, new IntentFilter(MyFirebaseMessagingService.FCM_ACTION)
        );
        LocalBroadcastManager.getInstance(this).registerReceiver(
                fcm_new_token_note_receiver, new IntentFilter(MyFirebaseMessagingService.FCM_NEW_TOKEN)
        );


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mySocket.connect();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mySocket.disconnect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(fcm_action_receiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(fcm_new_token_note_receiver);
    }

    /**
     * Starting socket connection
     * connect socket only onCreate
     */
    protected Socket mySocket;
    {
        try {
            mySocket = IO.socket(ApiHelper.get().node_connect_path);
            mySocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... objects) {
                    Log.d(TAG,"connected");
                }
            });
            mySocket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... objects) {
                    Log.d(TAG,"disconnected");
                }
            });
            mySocket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
                @Override
                public void call(Object... objects) {
                    Log.d(TAG,"connection not success");
                }
            });
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
