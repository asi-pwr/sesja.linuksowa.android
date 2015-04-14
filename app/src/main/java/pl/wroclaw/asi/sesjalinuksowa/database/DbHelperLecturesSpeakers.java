package pl.wroclaw.asi.sesjalinuksowa.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import pl.wroclaw.asi.sesjalinuksowa.models.Partner;

/**
 * Created by fares on 09.04.15.
 */
public class DbHelperLecturesSpeakers extends DbHelper {

    public static final String ID_LECTURE = "idlecture";
    public static final String ID_SPEAKER = "idspeaker";
    public static final String ID = "id";

    public static final String TABLE_NAME = "Lectures_Speakers";
    public static final String CREATE_TABLE = SQL_CREATE_TABLE + " " + TABLE_NAME + " (" +
            ID + " " + SQL_INTEGER + " " + SQL_PRIMARY_KEY + ", " +
            ID_LECTURE + " " + SQL_INTEGER + ", " + ID_SPEAKER + " " + SQL_INTEGER + ")";



    public DbHelperLecturesSpeakers(Context context) {
        super(context);
    }

    // PUBLIC METHODS TO EXTRACT DATA
    public static void insertBind(Context context, int lectureId, int speakerId){
        SQLiteDatabase db = DbHelper.getDbHelper(context).getWritableDatabase();
        db.insert(TABLE_NAME, null, bindToContentValue(lectureId, speakerId));
    }


    // PUBLIC METHODS TO SAVE DATA
    public static List<Integer> getSpeakersForLecture(Context context, int lectureId){
        SQLiteDatabase db = DbHelper.getDbHelper(context).getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, ID_LECTURE + "=" + lectureId, null, null, null, null);
        cursor.moveToFirst();
        return getIdsFromCursor(cursor, ID_SPEAKER);
    }

    public static List<Integer> getLecturesForSpeaker(Context context, int speakerId){
        SQLiteDatabase db = DbHelper.getDbHelper(context).getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, ID_SPEAKER + "=" + speakerId, null, null, null, null);
        cursor.moveToFirst();
        return getIdsFromCursor(cursor, ID_LECTURE);
    }


    // PUBLIC METHODS TO DELETE DATA
    public static void clearTable(Context context){
        SQLiteDatabase db = DbHelper.getDbHelper(context).getWritableDatabase();
        db.execSQL(SQL_DELETE_FROM + " " + TABLE_NAME);
    }
    public static void deleteBind(Context context, int lectureId, int speakerId){
        SQLiteDatabase db = DbHelper.getDbHelper(context).getWritableDatabase();
        db.delete(TABLE_NAME, ID_LECTURE + "=" + lectureId + " and "
                + ID_SPEAKER + "=" + speakerId, null);
    }

    // PRIVATE METHODS
    private static List<Integer> getIdsFromCursor(Cursor cursor, String columnName){
        List<Integer> ids = new ArrayList<>();
        if (cursor.moveToFirst()){
            ids.add(getIdFromCursor(cursor, columnName));
            while(cursor.moveToNext()){
                ids.add(getIdFromCursor(cursor, columnName));
            }
        }
        cursor.close();
        return ids;
    }

    private static Integer getIdFromCursor(Cursor cursor, String columnName){
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static ContentValues bindToContentValue(int lectureId, int speakerId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_LECTURE, lectureId);
        contentValues.put(ID_SPEAKER, speakerId);
        return contentValues;
    }
}
