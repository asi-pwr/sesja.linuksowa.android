package pl.wroclaw.asi.sesjalinuksowa.models;

import java.util.List;

/**
 * Created by fares on 11.04.15.
 */
public class EventDay {

    // Day name
    private String name;
    private int dayID;
    private List<Lecture> lectures;
    private List<LectureSection> lectureSections;

    // ---------------- GETTERS / SETTERS ----------------
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getDayID() {
        return dayID;
    }
    public void setDayID(int dayID) {
        this.dayID = dayID;
    }

    public List<Lecture> getLectures() {
        return lectures;
    }
    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    public List<LectureSection> getLectureSections() {
        return lectureSections;
    }
    public void setLectureSections(List<LectureSection> lectureSections) {
        this.lectureSections = lectureSections;
    }
    // ---------------- -------------------- ----------------
}