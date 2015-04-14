package pl.wroclaw.asi.sesjalinuksowa.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import pl.wroclaw.asi.sesjalinuksowa.models.EventDay;
import pl.wroclaw.asi.sesjalinuksowa.models.Lecture;

/**
 * Created by fares on 08.04.15.
 */
public class DbHelperLectures extends DbHelper {

    public static final String ID = "id";
    public static final String START_HOUR = "starthour";
    public static final String END_HOUR = "endhour";
    public static final String DAY = "day";
    public static final String TITLE = "title";
    public static final String PLACE = "place";
    public static final String DESCRIPTION = "desc";

    public static final String TABLE_NAME = "Lectures";
    public static final String CREATE_TABLE = SQL_CREATE_TABLE + " " + TABLE_NAME + " (" +
            ID + " " + SQL_INTEGER + " " + SQL_PRIMARY_KEY + ", " +
            START_HOUR + " " + SQL_TEXT + ", " + END_HOUR + " " + SQL_TEXT + ", " +
            DAY + " " + SQL_TEXT + ", " + TITLE + " " + SQL_TEXT + ", " +
            PLACE + " " + SQL_TEXT + ", " + DESCRIPTION + " " + SQL_TEXT + ")";




    public DbHelperLectures(Context context) {
        super(context);
    }

    // PUBLIC METHODS TO EXTRACT DATA
    
    public static EventDay getEventDay(Context context, String day){
        SQLiteDatabase db = DbHelper.getDbHelper(context).getReadableDatabase();
        Cursor c = db.query(TABLE_NAME, null, DAY + "=\"" + day + "\"", null, null, null, null);
        EventDay ed = new EventDay();
        ed.setName(day);
        ed.setLectures(getLecturesFromCursor(c, context));
        return ed;
    }
    
    public static Lecture getLecture(Context context, int lectureId){
        SQLiteDatabase db = DbHelper.getDbHelper(context).getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, ID + "=" + lectureId, null, null, null, null);
        cursor.moveToFirst();
        Lecture Lecture = lectureFromCursor(cursor, context);
        cursor.close();
        return Lecture;
    }
    public static ArrayList<Lecture> getAllLecture(Context context){
        SQLiteDatabase db = DbHelper.getDbHelper(context).getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        return getLecturesFromCursor(cursor, context);
    }

    // PUBLIC METHODS TO SAVE DATA
    public static void insertLecture(Context context, Lecture lecture){
        SQLiteDatabase db = DbHelper.getDbHelper(context).getWritableDatabase();
        db.insert(TABLE_NAME, null, lectureToContentValue(lecture));
    }
    public static void insertAllLecture(Context context, ArrayList<Lecture> allLecture){
        SQLiteDatabase db = DbHelper.getDbHelper(context).getWritableDatabase();
        db.beginTransaction();
        try {
            for(Lecture e : allLecture) {
                db.insert(TABLE_NAME, null, lectureToContentValue(e));
                for (Integer id : e.getSpeakersIds()){
                    db.insert(DbHelperLecturesSpeakers.TABLE_NAME,
                            null,
                            DbHelperLecturesSpeakers.bindToContentValue(e.getId(), id));
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
        }
    }


    // PUBLIC METHODS TO DELETE DATA
    public static void clearTable(Context context){
        SQLiteDatabase db = DbHelper.getDbHelper(context).getWritableDatabase();
        db.execSQL(SQL_DELETE_FROM + " " + TABLE_NAME);
    }
    public static void deleteLecture(Context context, int lectureId){
        SQLiteDatabase db = DbHelper.getDbHelper(context).getWritableDatabase();
        db.delete(TABLE_NAME, ID + "=" + lectureId, null);
    }


    // PRIVATE METHODS
    private static ArrayList<Lecture> getLecturesFromCursor(Cursor cursor, Context context){
        ArrayList<Lecture> lecture = new ArrayList<>();
        if (cursor.moveToFirst()){
            lecture.add(lectureFromCursor(cursor, context));
            while(cursor.moveToNext()){
                lecture.add(lectureFromCursor(cursor, context));
            }
        }
        cursor.close();
        return lecture;
    }

    private static ContentValues lectureToContentValue(Lecture lecture) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, lecture.getId());
        contentValues.put(START_HOUR, lecture.getStartHour());
        contentValues.put(END_HOUR, lecture.getEndHour());
        contentValues.put(DAY, lecture.getDay());
        contentValues.put(TITLE, lecture.getTitle());
        contentValues.put(PLACE, lecture.getPlace());
        contentValues.put(DESCRIPTION, lecture.getDescription());
        return contentValues;
    }

    private static Lecture lectureFromCursor(Cursor cursor, Context context) {
        String startHour = cursor.getString(cursor.getColumnIndex(START_HOUR));
        String endHour = cursor.getString(cursor.getColumnIndex(END_HOUR));
        String day = cursor.getString(cursor.getColumnIndex(DAY));
        String title = cursor.getString(cursor.getColumnIndex(TITLE));
        int place = cursor.getInt(cursor.getColumnIndex(PLACE));
        String description = cursor.getString(cursor.getColumnIndex(DESCRIPTION));
        int id = cursor.getInt(cursor.getColumnIndex(ID));
        List<Integer> speakersIds = getSpeakersForLecture(context, id);
        return new Lecture(startHour, endHour, day, title, place, description, id, speakersIds);
    }

    private static List<Integer> getSpeakersForLecture(Context context, int id) {
        return DbHelperLecturesSpeakers.getSpeakersForLecture(context, id);
    }
}
