package pl.wroclaw.asi.sesjalinuksowa.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pl.wroclaw.asi.sesjalinuksowa.models.EventDay;
import pl.wroclaw.asi.sesjalinuksowa.models.Lecture;
import pl.wroclaw.asi.sesjalinuksowa.models.NavigationDrawerListItem;
import pl.wroclaw.asi.sesjalinuksowa.models.Organizer;
import pl.wroclaw.asi.sesjalinuksowa.models.Partner;
import pl.wroclaw.asi.sesjalinuksowa.models.Speaker;

/**
 * Created by fares on 08.04.15.
 */
public abstract class ActivityBase extends ActionBarActivity {

    protected static List<Speaker> speakers;
    protected static List<EventDay> eventDays;
    protected static List<Partner> partners;
    protected static List<Organizer> organizers;

    public static List<Speaker> getSpeakers(List<Integer> ids){
        List<Speaker> selectedSpeakers = new ArrayList<>();
        for (Speaker s : speakers)
            for (int id : ids)
                if (s.getId() == id)
                    selectedSpeakers.add(s);
        return selectedSpeakers;
    }

    // URL's
    protected static final String JSON_SPEAKERS_URL = "http://tramwaj.asi.pwr.wroc.pl/~wojciech_czerpak/speakers.json";
    protected static final String JSON_LECTURES_URL = "http://tramwaj.asi.pwr.wroc.pl/~wojciech_czerpak/lectures.json";
    protected static final String JSON_PARTNERS_URL = "http://tramwaj.asi.pwr.wroc.pl/~wojciech_czerpak/partners.json";
//    protected static final String JSON_ORGANIZERS_URL = "http://rajdy.samaelson.pl/api/sponsors/";

    public static void setSpeakers(List<Speaker> speakers) {
        ActivityBase.speakers = speakers;
    }
    public static void setEventDays(List<EventDay> eventDays) {
        ActivityBase.eventDays = eventDays;
    }
    public static void setEventDaysFromAllLectures(List<Lecture> allLectures) {
        Set<String> days = new HashSet<>();
        if (eventDays == null)
            eventDays = new ArrayList<>();
        // get set of days
        for (Lecture e : allLectures)
            days.add(e.getDay());
        // for every day find events
        for (String day : days){
            EventDay ed = new EventDay();
            List<Lecture> dayLectures = new ArrayList<>();

            for (Lecture e : allLectures)
                if (day.equals(e.getDay()))
                    dayLectures.add(e);

            ed.setLectures(dayLectures);
            // add new day to final dayList
            eventDays.add(ed);
        }

        for (EventDay ed : eventDays){
            Collections.sort(ed.getLectures());
        }
    }
    public static void setPartners(List<Partner> partners) {
        ActivityBase.partners = partners;
    }
    public static void setOrganizers(List<Organizer> organizers) {
        ActivityBase.organizers = organizers;
    }

    public static void startMainActivity(Context context) {
        context.startActivity(new Intent(context, ActivityMain.class));
    }
}
