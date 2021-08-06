package com.ankit.wednesdaymusic.di.component;

import com.ankit.wednesdaymusic.data.local.SharedPreferenceManager;
import com.ankit.wednesdaymusic.data.remote.TrackRepository;
import com.ankit.wednesdaymusic.di.module.SharedPreferenceManagerModule;
import com.ankit.wednesdaymusic.di.module.TrackRepositoryModule;

import javax.inject.Singleton;

import dagger.Component;



@Singleton
@Component(modules = {SharedPreferenceManagerModule.class, TrackRepositoryModule.class})
public interface ApplicationComponent {

    SharedPreferenceManager initializeSharedPreferenceManager();

    TrackRepository getTrackRepository();

}
