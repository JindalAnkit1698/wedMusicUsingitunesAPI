package com.ankit.wednesdaymusic.data.remote;

import androidx.lifecycle.MutableLiveData;

import com.ankit.wednesdaymusic.model.ResponseResult;
import com.ankit.wednesdaymusic.model.TrackResponse;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;


public class TrackRepository {

    private CompositeDisposable disposable;



    private TrackApi trackApi;

    /**
     * Initializing the interface to implement
     */

    @Inject
    public TrackRepository(TrackApi trackApi, CompositeDisposable disposable){
        this.trackApi = trackApi;
        this.disposable = disposable;

    }


    public MutableLiveData<ResponseResult> searchTrack(String term, String country, String media){
        MutableLiveData<ResponseResult> trackData = new MutableLiveData<>();

        disposable.add(trackApi.searchTracks(term, country, media)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<TrackResponse>>(){

                    @Override
                    public void onSuccess(Response<TrackResponse> trackResponseResponse) {

                        trackData.setValue(new ResponseResult<>(trackResponseResponse.body(), trackResponseResponse.raw()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        trackData.setValue(null);
                    }
                })

        );


        return trackData;
    }

}
