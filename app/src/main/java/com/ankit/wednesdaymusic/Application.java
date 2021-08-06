package com.ankit.wednesdaymusic;

import android.content.Context;


public class Application extends android.app.Application {


    private static Application instance;

    public static Context getContext(){
        return instance;
    }

    @Override
    public void onCreate() {

        instance = this;

        super.onCreate();

    }

}
