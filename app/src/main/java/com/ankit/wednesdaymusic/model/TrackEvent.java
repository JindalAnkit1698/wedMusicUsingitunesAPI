package com.ankit.wednesdaymusic.model;


public class TrackEvent {

    private Track track;

    public TrackEvent(Track track) {
       this.track = track;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }
}
