package pl.wroclaw.asi.sesjalinuksowa.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by fares on 10.04.15.
 */
public abstract class UtilPreferences extends Activity {

    // URL's, SHARED_PREFS name's
    public static final String PREFS_NAME = "rajd_app";
    public static final String PREFS_IS_SECOND_FRAGMENT_OPENED = "isSecondFragmentOpened";
    public static final String PREFS_IS_DATA_DOWNLOADED = "isDataDownloaded";

    // ---------------- PREFERENCES READ/WRITE ----------------
    public static void saveToPrefsString(Context context, String keyName, String valueToSave) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(keyName, valueToSave);
        editor.apply();
    }
    public static String readFromPrefsString(Context context, String keyName, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(keyName, defaultValue);
    }

    public static void saveToPrefsBoolean(Context context, String keyName, Boolean valueToSave) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(keyName, valueToSave);
        editor.apply();
    }
    public static Boolean readFromPrefsBoolean(Context context, String keyName, Boolean defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(keyName, defaultValue);
    }
}