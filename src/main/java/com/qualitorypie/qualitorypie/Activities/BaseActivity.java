package com.qualitorypie.qualitorypie.Activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.qualitorypie.qualitorypie.Services.MyFirebaseMessagingService;

import java.net.URISyntaxException;


import helpers.ApiHelper;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class BaseActivity extends AppCompatActivity {
    private final String TAG = "BaseActivity";

    private LocationManager locationManager;
    private LocationListener locationListener;

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
        enable_gps_location();
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
                    Log.d(TAG, "connected");
                }
            });
            mySocket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... objects) {
                    Log.d(TAG, "disconnected");
                }
            });
            mySocket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
                @Override
                public void call(Object... objects) {
                    Log.d(TAG, "connection not success");
                }
            });
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    protected void enable_gps_location() {
//        add LocationManager and LocationListener properties to baseActivity property like following commented two lines codes
//        private LocationManager locationManager;
//        private LocationListener locationListener;
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d(TAG,"ggg"+String.valueOf(location.getLongitude()));
                Log.d(TAG,"ggg"+String.valueOf(location.getLatitude()));
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

        location_config();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                location_config();
                break;
            default:
                break;
        }
    }

    /**
     * location config for gps location
     */
    void location_config(){
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }
        locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
    }


}
