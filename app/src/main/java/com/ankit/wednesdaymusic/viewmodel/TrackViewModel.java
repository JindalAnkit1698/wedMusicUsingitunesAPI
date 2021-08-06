package com.ankit.wednesdaymusic.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ankit.wednesdaymusic.data.remote.TrackRepository;
import com.ankit.wednesdaymusic.model.ResponseResult;

public class TrackViewModel extends ViewModel {


    private MutableLiveData<ResponseResult> mutableLiveData;


    public void searchTrack(TrackRepository trackRepository, String term, String country, String mediaType){
        mutableLiveData = trackRepository.searchTrack(term, country, mediaType);
    }


    public LiveData<ResponseResult> getTrackRepository() {
        return mutableLiveData;
    }

}
