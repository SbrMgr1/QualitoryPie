package com.qualitorypie.qualitorypie.DataProviders;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class UserDataProvider extends Application {

    private int user_id;
    private String username;
    private String email;
    private String user_token;

    private SharedPreferences sp;

    public UserDataProvider(Activity activity) {
        sp = activity.getSharedPreferences("user_info", Context.MODE_PRIVATE);
    }

    public int getUser_id() {
        return sp.getInt("user_id", 0);
    }

    public void setUser_id(int user_id) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("user_id", user_id);
        editor.commit();
    }

    public String getUsername() {
        return sp.getString("username", null);
    }

    public void setUsername(String username) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", username);
        editor.commit();
    }

    public String getEmail() {
        return sp.getString("email", null);
    }

    public void setEmail(String email) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("email", email);
        editor.commit();
    }

    public String getUser_token() {
        return sp.getString("user_token", null);
    }

    public void setUser_token(String user_token) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("user_token", user_token);
        editor.commit();
    }

    public void clearUserData() {

    }
}
