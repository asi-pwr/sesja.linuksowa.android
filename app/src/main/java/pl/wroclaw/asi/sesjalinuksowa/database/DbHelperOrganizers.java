package pl.wroclaw.asi.sesjalinuksowa.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import pl.wroclaw.asi.sesjalinuksowa.models.Organizer;

/**
 * Created by fares on 09.04.15.
 */
public class DbHelperOrganizers extends DbHelper {

    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String MAIL = "mail";
    public static final String IMG_URL = "imgurl";
    public static final String FUNCTION = "function";
    public static final String ID = "id";

    public static final String TABLE_NAME = "Organizers";
    public static final String CREATE_TABLE = SQL_CREATE_TABLE + " " + TABLE_NAME + " (" +
            ID + " " + SQL_INTEGER + " " + SQL_PRIMARY_KEY + ", " +
            NAME + " " + SQL_TEXT + ", " + SURNAME + " " + SQL_TEXT + ", " +
            IMG_URL + " " + SQL_TEXT + ", " + MAIL + " " + SQL_TEXT + ", " +
            FUNCTION + " " + SQL_TEXT + ")";



    public DbHelperOrganizers(Context context) {
        super(context);
    }

    // PUBLIC METHODS TO EXTRACT DATA
    public static Organizer getOrganizer(Context context, int OrganizerId){
        SQLiteDatabase db = DbHelper.getDbHelper(context).getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, ID + "=" + OrganizerId, null, null, null, null);
        cursor.moveToFirst();
        Organizer Organizer = OrganizerFromCursor(cursor);
        cursor.close();
        return Organizer;
    }
    public static ArrayList<Organizer> getAllOrganizers(Context context){
        SQLiteDatabase db = DbHelper.getDbHelper(context).getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        return getOrganizersFromCursor(cursor);
    }

    // PUBLIC METHODS TO SAVE DATA
    public static void insertOrganizer(Context context, Organizer Organizer){
        SQLiteDatabase db = DbHelper.getDbHelper(context).getWritableDatabase();
        db.insert(TABLE_NAME, null, OrganizerToContentValue(Organizer));
    }
    public static void insertAllOrganizers(Context context, ArrayList<Organizer> allOrganizers){
        SQLiteDatabase db = DbHelper.getDbHelper(context).getWritableDatabase();
        db.beginTransaction();
        try {
            for( Organizer e : allOrganizers)
                db.insert(TABLE_NAME, null, OrganizerToContentValue(e));
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
    public static void deleteOrganizer(Context context, int OrganizerId){
        SQLiteDatabase db = DbHelper.getDbHelper(context).getWritableDatabase();
        db.delete(TABLE_NAME, ID + "=" + OrganizerId, null);
    }


    // PRIVATE METHODS
    private static ArrayList<Organizer> getOrganizersFromCursor(Cursor cursor){
        ArrayList<Organizer> Organizers = new ArrayList<>();
        if (cursor.moveToFirst()){
            Organizers.add(OrganizerFromCursor(cursor));
            while(cursor.moveToNext()){
                Organizers.add(OrganizerFromCursor(cursor));
            }
        }
        cursor.close();
        return Organizers;
    }

    private static ContentValues OrganizerToContentValue(Organizer Organizer) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, Organizer.getName());
        contentValues.put(SURNAME, Organizer.getSurname());
        contentValues.put(MAIL, Organizer.getMail());
        contentValues.put(IMG_URL, Organizer.getImgUrl());
        contentValues.put(FUNCTION, Organizer.getFunction());
        return contentValues;
    }

    private static Organizer OrganizerFromCursor(Cursor cursor) {
        String name = cursor.getString(cursor.getColumnIndex(NAME));
        String surname = cursor.getString(cursor.getColumnIndex(SURNAME));
        String mail = cursor.getString(cursor.getColumnIndex(MAIL));
        String imgUrl = cursor.getString(cursor.getColumnIndex(IMG_URL));
        String function = cursor.getString(cursor.getColumnIndex(FUNCTION));
        return new Organizer(name, surname, mail, imgUrl, function);
    }
}
