package com.ankit.wednesdaymusic.data.remote;

import com.ankit.wednesdaymusic.model.TrackResponse;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface TrackApi {


    @GET("/search")
    Single<Response<TrackResponse>> searchTracks(@Query("term") String term, @Query("country") String country, @Query("media") String media);

}
