package pl.wroclaw.asi.sesjalinuksowa.models;

/**
 * Created by fares on 10.04.15.
 */
public class LectureSection {
    private int position;
    private String hour;

    public int getPosition() {
        return position;
    }
    public void setPosition(int posstion) {
        this.position = posstion;
    }

    public String getHour() {
        return hour;
    }
    public void setHour(String hour) {
        this.hour = hour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LectureSection)) return false;

        LectureSection that = (LectureSection) o;

        if (position != that.position) return false;
        if (hour != null ? !hour.equals(that.hour) : that.hour != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = position;
        result = 31 * result + (hour != null ? hour.hashCode() : 0);
        return result;
    }
}
