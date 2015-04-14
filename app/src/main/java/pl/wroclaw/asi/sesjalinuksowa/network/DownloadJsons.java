package pl.wroclaw.asi.sesjalinuksowa.network;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.wroclaw.asi.sesjalinuksowa.activities.ActivityBase;
import pl.wroclaw.asi.sesjalinuksowa.activities.ActivitySplash;
import pl.wroclaw.asi.sesjalinuksowa.database.DbHelperLectures;
import pl.wroclaw.asi.sesjalinuksowa.database.DbHelperOrganizers;
import pl.wroclaw.asi.sesjalinuksowa.database.DbHelperPartners;
import pl.wroclaw.asi.sesjalinuksowa.database.DbHelperSpeakers;
import pl.wroclaw.asi.sesjalinuksowa.models.Lecture;
import pl.wroclaw.asi.sesjalinuksowa.models.Organizer;
import pl.wroclaw.asi.sesjalinuksowa.models.Partner;
import pl.wroclaw.asi.sesjalinuksowa.models.Speaker;
import pl.wroclaw.asi.sesjalinuksowa.utils.UtilNetworkConnection;
import pl.wroclaw.asi.sesjalinuksowa.utils.UtilParser;
import pl.wroclaw.asi.sesjalinuksowa.utils.UtilPreferences;

/**
 * Created by fares on 09.04.15.
 */
public class DownloadJsons extends AsyncTask<Map<String, String>, Void, Map<String, String>> {

    public static final String KEY_SPEAKERS = "speakers";
    public static final String KEY_LECTURES = "lectures";
    public static final String KEY_PARTNERS = "partners";
    public static final String KEY_ORGANIZERS = "organizers";

    protected UtilParser utilParser;
    protected UtilNetworkConnection utilNetworkConnection;

    protected List<Speaker> speakers;
    protected List<Lecture> lectures;
    protected List<Partner> partners;
    protected List<Organizer> organizers;

    protected Context context;

    public DownloadJsons(Context context) {
        this.context = context;
        this.utilParser = new UtilParser();
        this.utilNetworkConnection = new UtilNetworkConnection();
    }



    public List<Speaker> getSpeakers() {
        return speakers;
    }
    public void setSpeakers(List<Speaker> speakers) {
        this.speakers = speakers;
    }

    public List<Lecture> getLectures() {
        return lectures;
    }
    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    public List<Partner> getPartners() {
        return partners;
    }
    public void setPartners(List<Partner> partners) {
        this.partners = partners;
    }

    public List<Organizer> getOrganizers() {
        return organizers;
    }
    public void setOrganizers(List<Organizer> organizers) {
        this.organizers = organizers;
    }

    protected Map<String, String> downloadJsons(Map<String, String> params) {
        Map<String, String> downloadedJsons = new HashMap<>();
        if (params.get(KEY_SPEAKERS) != null)
            downloadedJsons.put(KEY_SPEAKERS, utilNetworkConnection.downloadJSON(params.get(KEY_SPEAKERS)));
        if (params.get(KEY_LECTURES) != null)
            downloadedJsons.put(KEY_LECTURES, utilNetworkConnection.downloadJSON(params.get(KEY_LECTURES)));
        if (params.get(KEY_PARTNERS) != null)
            downloadedJsons.put(KEY_PARTNERS, utilNetworkConnection.downloadJSON(params.get(KEY_PARTNERS)));
        if (params.get(KEY_ORGANIZERS) != null)
            downloadedJsons.put(KEY_ORGANIZERS, utilNetworkConnection.downloadJSON(params.get(KEY_ORGANIZERS)));
        return downloadedJsons;
    }

    protected void parseJsons(Map<String, String> params) {
        if (params.get(KEY_SPEAKERS) != null) {
            speakers = new ArrayList<>();
            speakers = UtilParser.parseJsonSpeakers(params.get(KEY_SPEAKERS));
            ActivityBase.setSpeakers(speakers);
        }
        if (params.get(KEY_LECTURES) != null) {
            lectures = new ArrayList<>();
            lectures = UtilParser.parseJsonLectures(params.get(KEY_LECTURES));
            ActivityBase.setEventDaysFromAllLectures(lectures);
        }
        if (params.get(KEY_PARTNERS) != null) {
            partners = new ArrayList<>();
            partners = UtilParser.parseJsonPartners(params.get(KEY_PARTNERS));
            ActivityBase.setPartners(partners);
        }
        if (params.get(KEY_ORGANIZERS) != null) {
            organizers = new ArrayList<>();
            organizers = UtilParser.parseJsonOrganizers(params.get(KEY_ORGANIZERS));
            ActivityBase.setOrganizers(organizers);
        }
    }


    protected void saveToDb() {
        DbHelperSpeakers.clearTable(context);
        DbHelperLectures.clearTable(context);
        DbHelperPartners.clearTable(context);
        DbHelperOrganizers.clearTable(context);

        if (speakers != null) {
            ArrayList<Speaker> speakerArrayList = new ArrayList<>(speakers);
            DbHelperSpeakers.insertAllSpeakers(context, speakerArrayList);
        }
        if (lectures != null) {
            ArrayList<Lecture> lectureArrayList = new ArrayList<>(lectures);
            DbHelperLectures.insertAllLecture(context, lectureArrayList);
        }
        if (partners != null) {
            ArrayList<Partner> partnerArrayList = new ArrayList<>(partners);
            DbHelperPartners.insertAllPartners(context, partnerArrayList);
        }
        if (organizers != null) {
            ArrayList<Organizer> organizerArrayList = new ArrayList<>(organizers);
            DbHelperOrganizers.insertAllOrganizers(context, organizerArrayList);
        }
    }

    @Override
    protected Map<String, String> doInBackground(Map<String, String>... params) {
        Map<String, String> mapJson;
        if (params[0].isEmpty())
            return null;
        mapJson = downloadJsons(params[0]);
        parseJsons(mapJson);
        saveToDb();
        UtilPreferences.saveToPrefsBoolean(context, UtilPreferences.PREFS_IS_DATA_DOWNLOADED, true);
        return null;
    }

    @Override
    protected void onPostExecute(Map<String, String> stringStringMap) {
        ActivityBase.startMainActivity(context);
        ((ActivitySplash) context).finish();
    }
}
