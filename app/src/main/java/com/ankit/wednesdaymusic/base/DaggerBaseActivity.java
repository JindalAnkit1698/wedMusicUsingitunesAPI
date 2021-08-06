package com.ankit.wednesdaymusic.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ankit.wednesdaymusic.data.local.SharedPreferenceManager;
import com.ankit.wednesdaymusic.data.remote.TrackRepository;
import com.ankit.wednesdaymusic.di.component.ApplicationComponent;
import com.ankit.wednesdaymusic.di.component.DaggerApplicationComponent;
import com.ankit.wednesdaymusic.di.module.ContextModule;

import javax.inject.Inject;


public abstract class DaggerBaseActivity extends AppCompatActivity {

    @Inject
    SharedPreferenceManager sharedPreferenceManager;


    @Inject
    TrackRepository trackRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ApplicationComponent component = DaggerApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
        sharedPreferenceManager = component.initializeSharedPreferenceManager();
        trackRepository = component.getTrackRepository();

    }

    public SharedPreferenceManager getSharedPreferenceManager() {
        return sharedPreferenceManager;
    }

    public TrackRepository getTrackRepository() {
        return trackRepository;
    }

}
