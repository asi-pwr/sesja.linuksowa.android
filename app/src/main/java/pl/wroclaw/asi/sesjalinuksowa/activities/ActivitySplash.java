package pl.wroclaw.asi.sesjalinuksowa.activities;


import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

import pl.wroclaw.asi.sesjalinuksowa.R;
import pl.wroclaw.asi.sesjalinuksowa.network.DownloadJsons;
import pl.wroclaw.asi.sesjalinuksowa.network.NetworkManager;
import pl.wroclaw.asi.sesjalinuksowa.utils.LoadFromDb;
import pl.wroclaw.asi.sesjalinuksowa.utils.UtilPreferences;

/**
 * Created by fares on 09.04.15.
 */
public class ActivitySplash extends ActivityBase {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // check flag firstLaunch
        if (!isDataDownloaded()) {
            // if first launch download all content and save to disk
            if (NetworkManager.getConnectivityStatus(this) == NetworkManager.TYPE_NOT_CONNECTED){
                ActivityBase.startMainActivity(this);
                finish();
            }
            else {
                Map<String, String> urlMap = new HashMap<>();
                urlMap.put(DownloadJsons.KEY_SPEAKERS, JSON_SPEAKERS_URL);
                urlMap.put(DownloadJsons.KEY_LECTURES, JSON_LECTURES_URL);
                urlMap.put(DownloadJsons.KEY_PARTNERS, JSON_PARTNERS_URL);
//                urlMap.put(DownloadJsons.KEY_ORGANIZERS, JSON);
                // DOWNLOAD DATA FROM WEB data and save isDataDownloaded (true) flag when done
                DownloadJsons firstDownloadTask = new DownloadJsons(this);
                firstDownloadTask.execute(urlMap);
            }
        }
        else {
            // LOAD DATA FROM DATABASE
            LoadFromDb readFromDataBaseTask = new LoadFromDb(this);
            readFromDataBaseTask.execute();
        }
    }

    private boolean isDataDownloaded() {
        return UtilPreferences.readFromPrefsBoolean(this, UtilPreferences.PREFS_IS_DATA_DOWNLOADED, false);
//        return false;
    }

}
