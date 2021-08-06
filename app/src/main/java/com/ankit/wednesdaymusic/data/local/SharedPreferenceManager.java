package com.ankit.wednesdaymusic.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ankit.wednesdaymusic.utility.Constants;


public class SharedPreferenceManager {

    /**
     * Preference KEY for last activity visited
     */
    private static final String PREF_KEY_LAST_ACTIVITY = "PREF_KEY_LAST_ACTIVITY";

    /**
     * Preference KEY for last track selected
     */
    private static final String PREF_KEY_LAST_TRACK_SAVED = "PREF_KEY_LAST_TRACK_SAVED";

    /**
     * Preference KEY for last date of calling Search API
     */
    private static final String PREF_KEY_LAST_SEARCH_DATE = "PREF_KEY_LAST_SEARCH_DATE";

    /**
     * Preference KEY for selected media type position
     */
    private static final String PREF_KEY_SEARCH_MEDIA_TYPE_POSITION = "PREF_KEY_SEARCH_MEDIA_TYPE_POSITION";


    private static final String PREF_KEY_SEARCH_COUNTRY_CODE = "PREF_KEY_SEARCH_COUNTRY_CODE";

    /**
     * Preference KEY for search term value
     */
    private static final String PREF_KEY_SEARCH_TERM = "PREF_KEY_SEARCH_TERM";

    /**
     * SharedPreferences instance
     */
    private SharedPreferences sharedPreferences;

    public SharedPreferenceManager(Context context){
        sharedPreferences = getSharedPreference(context);
    }



    private static SharedPreferences getSharedPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }


    public String getLastActivity() {
        return sharedPreferences.getString(PREF_KEY_LAST_ACTIVITY, "");
    }


    public void setLastActivity(String activity) {
        sharedPreferences.edit().putString(PREF_KEY_LAST_ACTIVITY, activity).apply();
    }


    public String getLastTrackSaved() {
        return sharedPreferences.getString(PREF_KEY_LAST_TRACK_SAVED, "");
    }

    public void setLastTrackSaved(String track) {
        sharedPreferences.edit().putString(PREF_KEY_LAST_TRACK_SAVED, track).apply();
    }


    public String getLastSearchDate() {
        return sharedPreferences.getString(PREF_KEY_LAST_SEARCH_DATE, "");
    }


    public void setLastSearchDate(String date) {
        sharedPreferences.edit().putString(PREF_KEY_LAST_SEARCH_DATE, date).apply();
    }


    public int getMediaTypePosition() {
        return sharedPreferences.getInt(PREF_KEY_SEARCH_MEDIA_TYPE_POSITION, Constants.DEFAULT_MEDIA_POSITION);
    }


    public void setMediaTypePosition(int position) {
        sharedPreferences.edit().putInt(PREF_KEY_SEARCH_MEDIA_TYPE_POSITION, position).apply();
    }


    public String getCountryCode() {
        return sharedPreferences.getString(PREF_KEY_SEARCH_COUNTRY_CODE, Constants.DEFAULT_COUNTRY_CODE);
    }


    public void setCountryCode(String countryCode) {
        sharedPreferences.edit().putString(PREF_KEY_SEARCH_COUNTRY_CODE, countryCode).apply();
    }


    public String getTerm() {
        return sharedPreferences.getString(PREF_KEY_SEARCH_TERM, Constants.DEFAULT_TERM);
    }


    public void setTerm(String term) {
        sharedPreferences.edit().putString(PREF_KEY_SEARCH_TERM, term).apply();
    }
}
