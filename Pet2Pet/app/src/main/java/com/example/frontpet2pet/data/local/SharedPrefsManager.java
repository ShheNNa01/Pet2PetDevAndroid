package com.example.frontpet2pet.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.frontpet2pet.data.models.response.AuthResponse;
import com.example.frontpet2pet.data.models.response.UserResponse;

public class SharedPrefsManager {
    private static final String PREF_NAME = "Pet2PetPrefs";
    private static final String KEY_TOKEN = "token";
    private static SharedPrefsManager instance;
    private final SharedPreferences prefs;
    private static final String KEY_ROLE_ID = "rol_id";

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

    public void saveToken(String token) {
        prefs.edit().putString(KEY_TOKEN, token).apply();
    }

    public String getToken() {
        return prefs.getString(KEY_TOKEN, null);
    }

    public void clearSession() {
        prefs.edit().clear().apply();
    }

    public void saveUserData(AuthResponse response) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_TOKEN, response.getAccessToken());

        // Guardar role_id solo si existe
        if (response.getUser() != null && response.getUser().getRoleId() != null) {
            editor.putInt(KEY_ROLE_ID, response.getUser().getRoleId());
        } else {
            editor.remove(KEY_ROLE_ID);  // Eliminar si no hay rol
        }

        editor.apply();
    }

    public Integer getRoleId() {
        if (!prefs.contains(KEY_ROLE_ID)) {
            return null;
        }
        return prefs.getInt(KEY_ROLE_ID, -1);
    }

    public boolean isAdmin() {
        Integer roleId = getRoleId();
        return roleId != null && (roleId == UserResponse.Roles.ADMIN ||
                roleId == UserResponse.Roles.SUPER_ADMIN);
    }

    public boolean isSuperAdmin() {
        Integer roleId = getRoleId();
        return roleId != null && roleId == UserResponse.Roles.SUPER_ADMIN;
    }
}