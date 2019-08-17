package com.qualitorypie.qualitorypie.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.qualitorypie.qualitorypie.DataProviders.UserDataProvider;
import com.qualitorypie.qualitorypie.R;

import helpers.BaseHelper;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIMEOUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("where is", BaseHelper.getCurrentMethodName(new Object() {
        }));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                UserDataProvider userDataProvider = new UserDataProvider(MainActivity.this);
//                Log.d("here",String.valueOf(userDataProvider.getEmail()));
                if (userDataProvider.getEmail() == null) {
                    Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                    finish();
                } else {
                    Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(homeIntent);
                    finish();
                }


            }
        }, SPLASH_TIMEOUT);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
