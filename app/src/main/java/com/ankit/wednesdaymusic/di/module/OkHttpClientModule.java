package com.ankit.wednesdaymusic.di.module;

import android.content.Context;

import com.ankit.wednesdaymusic.utility.Utility;
import com.ncornette.cache.OkCacheControl;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;



@Module (includes = ContextModule.class)
public class OkHttpClientModule {

    @Provides
    public OkCacheControl.NetworkMonitor provideNetworkMonitor(Context context){
        return () -> Utility.isNetworkAvailable(context);
    }

    @Provides
    public Cache provideCache(Context context){
        // Create cache object
        return new Cache(context.getCacheDir(), 10 * 1024 * 1024); // 10 MB
    }

    @Provides
    public OkHttpClient provideOkHttpClient(Cache cache, OkCacheControl.NetworkMonitor networkMonitor){
        return OkCacheControl.on(new OkHttpClient.Builder())
                .overrideServerCachePolicy(1, TimeUnit.MINUTES)
                .forceCacheWhenOffline(networkMonitor) // force to use cached data when offline
                .apply()
                .cache(cache)
                .build();
    }
}
