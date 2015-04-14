package pl.wroclaw.asi.sesjalinuksowa.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import pl.wroclaw.asi.sesjalinuksowa.models.Partner;

/**
 * Created by fares on 08.04.15.
 */
public class DbHelperPartners extends DbHelper {

    public static final String NAME = "name";
    public static final String DESCRIPTION = "desc";
    public static final String WEB_URL = "weburl";
    public static final String IMG_URL = "imgurl";
    public static final String TYPE = "type";
    public static final String ID = "id";

    public static final String TABLE_NAME = "Partners";
    public static final String CREATE_TABLE = SQL_CREATE_TABLE + " " + TABLE_NAME + " (" +
            ID + " " + SQL_INTEGER + " " + SQL_PRIMARY_KEY + ", " +
            NAME + " " + SQL_TEXT + ", " + WEB_URL + " " + SQL_TEXT + ", " +
            IMG_URL + " " + SQL_TEXT + ", " + TYPE + " " + SQL_TEXT + ", " +
            DESCRIPTION + " " + SQL_TEXT + ")";



    public DbHelperPartners(Context context) {
        super(context);
    }

    // PUBLIC METHODS TO EXTRACT DATA
    public static Partner getPartner(Context context, int PartnerId){
        SQLiteDatabase db = DbHelper.getDbHelper(context).getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, ID + "=" + PartnerId, null, null, null, null);
        cursor.moveToFirst();
        Partner Partner = PartnerFromCursor(cursor);
        cursor.close();
        return Partner;
    }
    public static ArrayList<Partner> getAllPartners(Context context){
        SQLiteDatabase db = DbHelper.getDbHelper(context).getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        return getPartnersFromCursor(cursor);
    }

    // PUBLIC METHODS TO SAVE DATA
    public static void insertPartner(Context context, Partner Partner){
        SQLiteDatabase db = DbHelper.getDbHelper(context).getWritableDatabase();
        db.insert(TABLE_NAME, null, PartnerToContentValue(Partner));
    }
    public static void insertAllPartners(Context context, ArrayList<Partner> allPartners){
        SQLiteDatabase db = DbHelper.getDbHelper(context).getWritableDatabase();
        db.beginTransaction();
        try {
            for( Partner e : allPartners)
                db.insert(TABLE_NAME, null, PartnerToContentValue(e));
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
    public static void deletePartner(Context context, int PartnerId){
        SQLiteDatabase db = DbHelper.getDbHelper(context).getWritableDatabase();
        db.delete(TABLE_NAME, ID + "=" + PartnerId, null);
    }


    // PRIVATE METHODS
    private static ArrayList<Partner> getPartnersFromCursor(Cursor cursor){
        ArrayList<Partner> Partners = new ArrayList<>();
        if (cursor.moveToFirst()){
            Partners.add(PartnerFromCursor(cursor));
            while(cursor.moveToNext()){
                Partners.add(PartnerFromCursor(cursor));
            }
        }
        cursor.close();
        return Partners;
    }

    private static ContentValues PartnerToContentValue(Partner partner) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, partner.getName());
        contentValues.put(DESCRIPTION, partner.getDescription());
        contentValues.put(WEB_URL, partner.getWebUrl());
        contentValues.put(IMG_URL, partner.getImgUrl());
        contentValues.put(TYPE, partner.getType());
        return contentValues;
    }

    private static Partner PartnerFromCursor(Cursor cursor) {
        String name = cursor.getString(cursor.getColumnIndex(NAME));
        String description = cursor.getString(cursor.getColumnIndex(DESCRIPTION));
        String webUrl = cursor.getString(cursor.getColumnIndex(WEB_URL));
        String imgUrl = cursor.getString(cursor.getColumnIndex(IMG_URL));
        int type = cursor.getInt(cursor.getColumnIndex(TYPE));
        return new Partner(name, description, webUrl, imgUrl, type);
    }
}
