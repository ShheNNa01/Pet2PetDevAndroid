package com.example.frontpet2pet.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.frontpet2pet.data.models.response.AuthResponse;
import com.example.frontpet2pet.data.models.response.UserResponse;

public class SharedPrefsManager {
    private static final String PREF_NAME = "Pet2PetPrefs";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_ROLE_ID = "role_id";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

    private static SharedPrefsManager instance;
    private final SharedPreferences prefs;

    private SharedPrefsManager(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized void init(Context context) {
        if (instance == null) {
            instance = new SharedPrefsManager(context);
        }
    }

    public static SharedPrefsManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("SharedPrefsManager must be initialized first");
        }
        return instance;
    }

    public void saveUserData(AuthResponse response) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_TOKEN, response.getAccessToken());
        editor.putBoolean(KEY_IS_LOGGED_IN, true);

        if (response.getUser() != null) {
            editor.putInt(KEY_USER_ID, response.getUser().getUserId());
            editor.putString(KEY_USER_NAME, response.getUser().getUserName());
            if (response.getUser().getRoleId() != null) {
                editor.putInt(KEY_ROLE_ID, response.getUser().getRoleId());
            }
        }

        editor.apply();
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public String getToken() {
        return prefs.getString(KEY_TOKEN, null);
    }

    public int getUserId() {
        return prefs.getInt(KEY_USER_ID, -1);
    }

    public String getUserName() {
        return prefs.getString(KEY_USER_NAME, "");
    }

    public Integer getRoleId() {
        return prefs.contains(KEY_ROLE_ID) ? prefs.getInt(KEY_ROLE_ID, -1) : null;
    }

    public void clearSession() {
        prefs.edit().clear().apply();
    }
}