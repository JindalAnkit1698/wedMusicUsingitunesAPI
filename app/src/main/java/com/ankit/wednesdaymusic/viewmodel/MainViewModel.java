package com.ankit.wednesdaymusic.viewmodel;

import android.view.View;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hbb20.CountryCodePicker;


public class MainViewModel extends ViewModel {

    /**
     * Observable value whether to show refreshing state of {@link SwipeRefreshLayout}
     */
    private ObservableField<Boolean> isRefreshing = new ObservableField<>();

    /**
     * Observable value whether to show category filter
     */
    private ObservableField<Boolean> isCategoryFilterVisible = new ObservableField<>();

    /**
     * Observable value to determine if api call result returns with empty data
     */
    private ObservableField<Boolean> isResultEmpty = new ObservableField<>();


    private ObservableField<String> lastSearch = new ObservableField<>();


    private ObservableField<String> searchTerm = new ObservableField<>();


    private MutableLiveData<String> country = new MutableLiveData<>();

    public MainViewModel(){

    }

    @BindingAdapter({"refreshVisibility"})
    public static void setRefreshVisibility(SwipeRefreshLayout view, boolean isRefreshing) {
        view.setRefreshing(isRefreshing);
    }

    @BindingAdapter({"animatedVisibility"})
    public static void setAnimatedVisibility(View view, boolean isVisible) {
        view.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter({"listVisibility"})
    public static void setListVisibility(View view, boolean isEmpty) {
        view.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
    }

    @BindingAdapter({"emptyViewVisibility"})
    public static void setEmptyViewVisibility(View view, boolean isEmpty) {
        view.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("countryValue")
    public static void setCountryValue(final CountryCodePicker picker, MutableLiveData country) {
        picker.setOnCountryChangeListener(() -> country.postValue(picker.getSelectedCountryNameCode()));
    }

    public void onCategoryFilterClick(boolean isVisible){
        isCategoryFilterVisible.set(!isVisible);
    }

    public ObservableField<Boolean> isRefreshing() {
        return isRefreshing;
    }

    public void setRefreshing(boolean progressVisible) {
        this.isRefreshing.set(progressVisible);
    }

    public ObservableField<Boolean> getIsCategoryFilterVisible() {
        return isCategoryFilterVisible;
    }

    public ObservableField<String> getLastSearch() {
        return lastSearch;
    }

    public void setLastSearch(String lastSearch) {
        this.lastSearch.set(lastSearch);
    }

    public ObservableField<String> getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm.set(searchTerm);
    }

    public MutableLiveData<String> getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country.postValue(country);
    }

    public ObservableField<Boolean> getIsResultEmpty() {
        return isResultEmpty;
    }

    public void setIsResultEmpty(boolean isResultEmpty) {
        this.isResultEmpty.set(isResultEmpty);
    }
}
