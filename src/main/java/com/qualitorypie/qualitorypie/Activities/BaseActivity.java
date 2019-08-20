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
import android.widget.Toast;


import com.qualitorypie.qualitorypie.Services.MyFirebaseMessagingService;
import java.net.URISyntaxException;
import java.util.EventListener;


import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class BaseActivity extends AppCompatActivity {
    private final String TAG = "BaseActivity";
    Socket mySocket;
    {
        try{
            mySocket = IO.socket("http://192.168.254.7:3030");
        }catch (URISyntaxException e){
            e.printStackTrace();
        }
    }


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
        getSocket().connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(fcm_action_receiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(fcm_new_token_note_receiver);
    }
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://192.168.254.7:3030");
//            Toast.makeText(this,"here",Toast.LENGTH_LONG).show();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return mSocket;
    }

    }
