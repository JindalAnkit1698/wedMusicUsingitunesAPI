package com.ankit.wednesdaymusic.di.module;

import com.ankit.wednesdaymusic.data.remote.TrackApi;
import com.ankit.wednesdaymusic.data.remote.TrackRepository;
import com.ankit.wednesdaymusic.utility.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module(includes = OkHttpClientModule.class)
public class TrackRepositoryModule {

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    public TrackApi getTrackService(Retrofit retrofit){
        return retrofit.create(TrackApi.class);
    }


    @Provides
    public CompositeDisposable provideDisposable(){
        return new CompositeDisposable();
    }

    @Provides
    @Singleton
    public TrackRepository provideTrackRepository(TrackApi trackApi, CompositeDisposable disposable){
        return new TrackRepository(trackApi, disposable);
    }
}
