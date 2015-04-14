package pl.wroclaw.asi.sesjalinuksowa.utils;

import android.content.Context;
import android.os.AsyncTask;

import pl.wroclaw.asi.sesjalinuksowa.activities.ActivityBase;
import pl.wroclaw.asi.sesjalinuksowa.database.DbHelperLectures;
import pl.wroclaw.asi.sesjalinuksowa.database.DbHelperOrganizers;
import pl.wroclaw.asi.sesjalinuksowa.database.DbHelperPartners;
import pl.wroclaw.asi.sesjalinuksowa.database.DbHelperSpeakers;

/**
 * Created by fares on 09.04.15.
 */
public class LoadFromDb extends AsyncTask<Void, Void, Void> {

    private Context context;
    public LoadFromDb(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
//        ActivityBase.setLectures(DbHelperLectures.getAllLecture(context));
        ActivityBase.setEventDaysFromAllLectures(DbHelperLectures.getAllLecture(context));
        ActivityBase.setOrganizers(DbHelperOrganizers.getAllOrganizers(context));
        ActivityBase.setSpeakers(DbHelperSpeakers.getAllSpeakers(context));
        ActivityBase.setPartners(DbHelperPartners.getAllPartners(context));
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        ActivityBase.startMainActivity(context);
        ((ActivityBase) context).finish();
    }
}