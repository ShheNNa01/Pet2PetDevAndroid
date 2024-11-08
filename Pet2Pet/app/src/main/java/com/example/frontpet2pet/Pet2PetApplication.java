// Pet2PetApplication.java
package com.example.frontpet2pet;

import android.app.Application;
import com.example.frontpet2pet.data.local.SharedPrefsManager;

public class Pet2PetApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPrefsManager.init(this);
    }
}

