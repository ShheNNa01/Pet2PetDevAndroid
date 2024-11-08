// Pet2PetApplication.java
package com.example.frontpet2pet;

import android.app.Application;
import android.content.Context;

import com.example.frontpet2pet.data.local.SharedPrefsManager;

public class Pet2PetApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        // Inicializar SharedPrefsManager
        SharedPrefsManager.init(this);
    }

    public static Context getAppContext() {
        return context;
    }
}

