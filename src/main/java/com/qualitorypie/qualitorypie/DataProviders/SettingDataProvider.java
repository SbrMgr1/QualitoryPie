package com.qualitorypie.qualitorypie.DataProviders;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SettingDataProvider {
    private String device_id;
    private SharedPreferences sp;

    public SettingDataProvider(Activity activity) {
        sp = activity.getSharedPreferences("device_id", Context.MODE_PRIVATE);
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public void clear() {

    }
}
