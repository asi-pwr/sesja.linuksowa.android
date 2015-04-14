package pl.wroclaw.asi.sesjalinuksowa.models;

import java.util.List;

/**
 * Created by fares on 08.04.15.
 */
public class Lecture implements Comparable<Lecture>{

    public static final int BIG_HALL = 0;
    public static final int SMALL_HALL = 1;

    public static final int FIRST_DAY = 0;
    public static final int SECOND_DAY = 1;

    private String startHour;
    private String endHour;
    private String day;
    private String title;
    private int place;
    private String description;
    private List<Integer> speakersIds;
    private int id;

    public Lecture(String startHour, String endHour, String day,
                   String title, int place, String description,
                   int id, List<Integer> speakersIds) {
        this.startHour = startHour;
        this.endHour = endHour;
        this.day = day;
        this.title = title;
        this.place = place;
        this.description = description;
        this.id = id;
        this.speakersIds = speakersIds;
    }


    public String getStartHour() {
        return startHour;
    }
    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getEndHour() {
        return endHour;
    }
    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public String getDay() {
        return day;
    }
    public void setDay(String day) {
        this.day = day;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public int getPlace() {
        return place;
    }
    public void setPlace(int place) {
        this.place = place;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public List<Integer> getSpeakersIds() {
        return speakersIds;
    }
    public void setSpeakersIds(List<Integer> speakersIds) {
        this.speakersIds = speakersIds;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int compareTo(Lecture another) {
        String[] startThis = startHour.split(":");
        String[] startAnother = another.getStartHour().split(":");
        for (int i = 0; i < startThis.length; i++ ){
            if (Integer.parseInt(startThis[i]) > Integer.parseInt(startAnother[i])){
                return 1;
            }
            else if (Integer.parseInt(startThis[i]) < Integer.parseInt(startAnother[i])){
                return -1;
            }
        }
        return 0;
    }
}
