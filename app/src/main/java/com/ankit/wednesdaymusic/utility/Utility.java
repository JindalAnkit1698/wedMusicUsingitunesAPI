package com.ankit.wednesdaymusic.utility;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Html;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.ankit.wednesdaymusic.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Utility {


    public static String getCurrentDateTime(){

        Date today = Calendar.getInstance().getTime();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        formatter.setTimeZone(TimeZone.getDefault());

        return formatter.format(today);
    }


    public static String formatDate(String dateValue){

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        formatter.setLenient(false);

        Date date = null;
        try {
            date = formatter.parse(dateValue);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DateFormat convertedFormat = new SimpleDateFormat("MMM dd, yyyy hh:mma", Locale.getDefault());
        convertedFormat.setLenient(false);
        if (date != null) {
            return convertedFormat.format(date).replace("AM", "am").replace("PM", "pm");
        }

        return dateValue;
    }


    public static void hideKeyboard(Activity activity, EditText editText){
        editText.clearFocus();
        InputMethodManager in = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (in != null) {
            in.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }


    public static void setEditTextError(EditText editText, String error){
        editText.setError(Html.fromHtml("<font color='red'>" + error + "</font>"));
    }

    public static String getMediaType(Activity activity, int position){
        return activity.getResources().getStringArray(R.array.category_selection_keys)[position];
    }


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm != null ? cm.getActiveNetworkInfo() : null;
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

    }


    public static String getDeviceCountryCode(Context context){
        return context.getResources().getConfiguration().locale.getCountry();
    }

}
