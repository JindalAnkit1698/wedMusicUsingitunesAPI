package com.ankit.wednesdaymusic.view.ui;

import android.content.Intent;
import android.os.Bundle;

import com.ankit.wednesdaymusic.base.DaggerBaseActivity;


public class HistoryHandlerActivity extends DaggerBaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        handleHistory(getSharedPreferenceManager().getLastActivity());

    }


    public void handleHistory(String lastActivity){
        Intent intent;

        Class<?> activityToOpen;

        try {
            activityToOpen = Class.forName(lastActivity);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            activityToOpen = MainActivity.class;
        }

        intent = new Intent(this, activityToOpen);
        intent.putExtra("from_history", true);
        startActivity(intent);
        finish();
    }
}
