package com.example.a24a10357roeihakmon206387128_task1;

import android.app.Application;

import com.example.a24a10357roeihakmon206387128_task1.Utilities.SharedPreferencesManager;

public class App extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        SharedPreferencesManager.init(this);
    }
}
