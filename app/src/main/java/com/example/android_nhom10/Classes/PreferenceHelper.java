package com.example.android_nhom10.Classes;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {
    private static final String PREFS_NAME="user_prefs";
    private static final String KEY_REMEMBER_ME="remember_me";
    private static final String KEY_USERNAME="username";
    private static final String KEY_PASSWORD="password";

    private SharedPreferences sharedPreferences;

    public PreferenceHelper(Context context) {
        sharedPreferences=context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
    }

    public void saveLoginDetails(String username, String password, boolean rememberMe) {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(KEY_REMEMBER_ME, rememberMe);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        editor.apply();
    }

    public void clearLoginDetails() {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.remove(KEY_REMEMBER_ME);
        editor.remove(KEY_USERNAME);
        editor.remove(KEY_PASSWORD);
        editor.apply();
    }

    public boolean isRemembered() {
        return sharedPreferences.getBoolean(KEY_REMEMBER_ME, false);
    }

    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, "");
    }

    public String getPassword() {
        return sharedPreferences.getString(KEY_PASSWORD, "");
    }
}