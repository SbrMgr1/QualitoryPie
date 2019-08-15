package com.qualitorypie.qualitorypie.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.qualitorypie.qualitorypie.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NetWatcher extends BroadcastReceiver {
    private static final String TAG = "NetWatcher";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "network changed.");

        String action = intent.getAction();
        //here, check that the network connection is available. If yes, start your service. If not, stop your service.
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            Log.d(TAG, "wifi connected");


            PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);
            Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            Notification notification = new Notification.Builder(context)
                    .setTicker(context.getString(R.string.app_name))
                    .setContentTitle(context.getString(R.string.app_name))
                    .setContentText("Synchronising with server.")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pIntent)
                    .setSound(uri)
                    .build();

            notification.flags = Notification.FLAG_AUTO_CANCEL;

            NotificationManager nManager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);

            nManager.notify(0, notification);

        } else {
            Log.d(TAG, "wifi disconnected");
        }
    }
}
