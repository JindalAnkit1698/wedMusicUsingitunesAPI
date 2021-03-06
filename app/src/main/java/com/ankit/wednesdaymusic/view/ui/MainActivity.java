package com.ankit.wednesdaymusic.view.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hbb20.CountryCodePicker;
import com.ankit.wednesdaymusic.R;
import com.ankit.wednesdaymusic.databinding.ActivityMainBinding;
import com.ankit.wednesdaymusic.model.Track;
import com.ankit.wednesdaymusic.model.TrackResponse;
import com.ankit.wednesdaymusic.utility.Constants;
import com.ankit.wednesdaymusic.utility.Utility;
import com.ankit.wednesdaymusic.view.adapter.TrackRecyclerViewAdapter;
import com.ankit.wednesdaymusic.base.BaseActivity;
import com.ankit.wednesdaymusic.viewmodel.MainViewModel;
import com.ankit.wednesdaymusic.viewmodel.TrackViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnEditorAction;
import segmented_control.widget.custom.android.com.segmentedcontrol.SegmentedControl;
import androidx.recyclerview.widget.GridLayoutManager;



public class MainActivity extends BaseActivity {


    ActivityMainBinding activityMainBinding;


    @BindView(R.id.recyclerViewTrack)
    RecyclerView rvTrackList;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout srlLoading;

    @BindView(R.id.pickerCountry)
    CountryCodePicker pkCountry;

    @BindView(R.id.segmentedCategories)
    SegmentedControl smCategories;

    @BindView(R.id.editTextTerm)
    EditText etTerm;

    String countryCode = Constants.DEFAULT_COUNTRY_CODE;

    int mediaTypePosition = Constants.DEFAULT_MEDIA_POSITION;

    String term = Constants.DEFAULT_TERM;

    /**
     * {@link ArrayList} to hold the resulting track items from API call
     */
    ArrayList<Track> trackArrayList = new ArrayList<>();

    /**
     * {@link RecyclerView} adapter to bind the resulting data to the activity view
     */
    TrackRecyclerViewAdapter trackRecyclerViewAdapter;

    /**
     * {@link androidx.lifecycle.ViewModel} to hold {@link Track} data
     */
    TrackViewModel trackViewModel;

    /**
     * {@link androidx.lifecycle.ViewModel} to hold {@link MainActivity} configuration data
     */
    MainViewModel mainViewModel;

    /**
     * Saving the {@link MainActivity} as the last visited activity
     */
    @Override
    public void onResume() {
        getSharedPreferenceManager().setLastActivity(getClass().getName());
        super.onResume();
    }

    @Override
    protected int layoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Obtain binding object using the Data Binding library
        activityMainBinding = (ActivityMainBinding) getActivityMainBinding();

        setViewModel();

        setUI();

        callSearch();

    }

    /**
     * Method to get the view models to handle data for this UI
     */
    public void setViewModel(){

        trackViewModel = ViewModelProviders.of(this).get(TrackViewModel.class);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        activityMainBinding.setViewModel(mainViewModel);

    }

    /**
     * Method to set default values of the activity UI
     */
    public void setUI(){

        // Set term value and bind it to the search EditText widget
        mainViewModel.setSearchTerm(getSharedPreferenceManager().getTerm());

        // Set last search date and bind it to the date TextView widget
        mainViewModel.setLastSearch(Utility.formatDate(getSharedPreferenceManager().getLastSearchDate()));

        // Set selected country and preference
        pkCountry.setCountryForNameCode(getSharedPreferenceManager().getCountryCode());
        pkCountry.setCountryPreference(String.format("%s,%s", Utility.getDeviceCountryCode(MainActivity.this), Constants.DEFAULT_COUNTRY_CODE));

        // Set media type and bind it to the SegmentedControl widget
        smCategories.setSelectedSegment(getSharedPreferenceManager().getMediaTypePosition());

        // Calling attemptSearch method when user swipes the track list to attempt calling Search API
        srlLoading.setOnRefreshListener(this::attemptSearch);

        observeCountryCodeValue();

        setupCategoryFilterListener();

        setupRecyclerView();
    }

    /**
     * Calling method to observe any changes on the country code value
     */
    private void observeCountryCodeValue(){
        mainViewModel.getCountry().observe(this, countryCode -> {
            this.countryCode = countryCode;
            attemptSearch();
        });

    }

    /**
     * Listening to filter category changes
     */
    private void setupCategoryFilterListener(){

        smCategories.addOnSegmentClickListener(segmentViewHolder -> {

            mediaTypePosition = segmentViewHolder.getAbsolutePosition();
            attemptSearch();

        });

    }

    /**
     * Set up RecyclerView adapter and layout manager
     */
    private void setupRecyclerView() {
        if (trackRecyclerViewAdapter == null) {

            trackRecyclerViewAdapter = new TrackRecyclerViewAdapter(MainActivity.this, trackArrayList);
            // setting grid layout manager to implement grid view.
            // in this method '2' represents number of columns to be displayed in grid view.
            GridLayoutManager layoutManager=new GridLayoutManager(this,2);

            // at last set adapter to recycler view.
            rvTrackList.setLayoutManager(layoutManager);
           // rvTrackList.setLayoutManager(new LinearLayoutManager(this));
            rvTrackList.setAdapter(trackRecyclerViewAdapter);
            rvTrackList.setItemAnimator(new DefaultItemAnimator());
            rvTrackList.setNestedScrollingEnabled(true);
        } else {
            trackRecyclerViewAdapter.notifyDataSetChanged();
        }
    }


    @OnEditorAction(R.id.editTextTerm)
    public boolean onEditorAction(int actionId){
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            attemptSearch();
            return true;
        }
        return false;
    }


    private void attemptSearch(){
        Utility.hideKeyboard(MainActivity.this, etTerm);
        term = etTerm.getText().toString();

        if(TextUtils.isEmpty(term)) {
            Utility.setEditTextError(etTerm, getString(R.string.required));
            return;
        }

        saveData();

        mainViewModel.setLastSearch(Utility.formatDate(getSharedPreferenceManager().getLastSearchDate()));

        callSearch();

    }


    public void saveData(){

        // Save new value of country code locally
        getSharedPreferenceManager().setCountryCode(countryCode);

        // Save new value of search term locally
        getSharedPreferenceManager().setTerm(term);

        // Save new value of media type position locally
        getSharedPreferenceManager().setMediaTypePosition(mediaTypePosition);

        // Save new value of last search date locally
        getSharedPreferenceManager().setLastSearchDate(Utility.getCurrentDateTime());
    }

    /**
     * Method to call the searchTrack function from network repository. This function will call the iTunes Search API
     */
    public void callSearch(){
        trackArrayList.clear();
        trackViewModel.searchTrack(getTrackRepository(), getSharedPreferenceManager().getTerm(), getSharedPreferenceManager().getCountryCode(), Utility.getMediaType(MainActivity.this, getSharedPreferenceManager().getMediaTypePosition()));
        observeTrackDataResponse();
    }

    /**
     * Observe API call response and update {@link RecyclerView} list for data changes
     */
    private void observeTrackDataResponse(){
        mainViewModel.setRefreshing(true);
        trackViewModel.getTrackRepository().observe(this, trackResponseResult -> {
            if(trackResponseResult.getResponseBody().isSuccessful()){
                List<Track> tracks = ((TrackResponse) trackResponseResult.getResponse()).getResults();
                mainViewModel.setIsResultEmpty(tracks.size() <= 0);
                trackArrayList.addAll(tracks);
            }
            else{
                // Show a message feedback to user when api response is not successful due to network error
                if(!Utility.isNetworkAvailable(MainActivity.this)) {
                    Toast.makeText(MainActivity.this, getString(R.string.no_network_available), Toast.LENGTH_SHORT).show();
                    trackArrayList.clear();
                }
            }

            trackRecyclerViewAdapter.notifyDataSetChanged();
            mainViewModel.setRefreshing(false);
        });

    }

}
