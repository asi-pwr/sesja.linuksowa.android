package pl.wroclaw.asi.sesjalinuksowa.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import pl.wroclaw.asi.sesjalinuksowa.models.Speaker;

/**
 * Created by fares on 08.04.15.
 */
public class DbHelperSpeakers extends DbHelper {
    
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String DESCRIPTION = "desc";
    public static final String IMG_URL = "imgurl";
    public static final String ID = "id";
    
    public static final String TABLE_NAME = "Speakers";
    public static final String CREATE_TABLE = SQL_CREATE_TABLE + " " + TABLE_NAME + " (" +
            ID + " " + SQL_INTEGER + " " + SQL_PRIMARY_KEY + ", " +
            NAME + " " + SQL_TEXT + ", " + SURNAME + " " + SQL_TEXT + ", " +
            DESCRIPTION + " " + SQL_TEXT + ", " + IMG_URL + " " + SQL_TEXT + ")";




    public DbHelperSpeakers(Context context) {
        super(context);
    }

    // PUBLIC METHODS TO EXTRACT DATA
    public static Speaker getSpeaker(Context context, int SpeakerId){
        SQLiteDatabase db = DbHelper.getDbHelper(context).getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, ID + "=" + SpeakerId, null, null, null, null);
        cursor.moveToFirst();
        Speaker Speaker = SpeakerFromCursor(cursor);
        cursor.close();
        return Speaker;
    }
    public static ArrayList<Speaker> getAllSpeakers(Context context){
        SQLiteDatabase db = DbHelper.getDbHelper(context).getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        return getSpeakersFromCursor(cursor);
    }
    
    // PUBLIC METHODS TO SAVE DATA
    public static void insertSpeaker(Context context, Speaker Speaker){
        SQLiteDatabase db = DbHelper.getDbHelper(context).getWritableDatabase();
        db.insert(TABLE_NAME, null, SpeakerToContentValue(Speaker));
    }
    public static void insertAllSpeakers(Context context, ArrayList<Speaker> allSpeakers){
        SQLiteDatabase db = DbHelper.getDbHelper(context).getWritableDatabase();
        db.beginTransaction();
        try {
            for( Speaker e : allSpeakers)
                db.insert(TABLE_NAME, null, SpeakerToContentValue(e));
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
    public static void deleteSpeaker(Context context, int SpeakerId){
        SQLiteDatabase db = DbHelper.getDbHelper(context).getWritableDatabase();
        db.delete(TABLE_NAME, ID + "=" + SpeakerId, null);
    }


    // PRIVATE METHODS
    private static ArrayList<Speaker> getSpeakersFromCursor(Cursor cursor){
        ArrayList<Speaker> speakers = new ArrayList<>();
        if (cursor.moveToFirst()){
            speakers.add(SpeakerFromCursor(cursor));
            while(cursor.moveToNext()){
                speakers.add(SpeakerFromCursor(cursor));
            }
        }
        cursor.close();
        return speakers;
    }

    private static ContentValues SpeakerToContentValue(Speaker speaker) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, speaker.getName());
        contentValues.put(SURNAME, speaker.getSurname());
        contentValues.put(DESCRIPTION, speaker.getDescription());
        contentValues.put(IMG_URL, speaker.getImgUrl());
        contentValues.put(ID, speaker.getId());
        return contentValues;
    }

    private static Speaker SpeakerFromCursor(Cursor cursor) {
        String name = cursor.getString(cursor.getColumnIndex(NAME));
        String surname = cursor.getString(cursor.getColumnIndex(SURNAME));
        String description = cursor.getString(cursor.getColumnIndex(DESCRIPTION));
        String imgUrl = cursor.getString(cursor.getColumnIndex(IMG_URL));
        int id = cursor.getInt(cursor.getColumnIndex(ID));
        return new Speaker(name, surname, description, imgUrl, id);
    }

}
