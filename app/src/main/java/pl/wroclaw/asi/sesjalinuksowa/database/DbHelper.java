package pl.wroclaw.asi.sesjalinuksowa.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by fares on 08.04.15.
 */
public class DbHelper extends SQLiteOpenHelper {
    public static final String SQL_CONFLICT = "ON CONFLICT REPLACE";
    public static final String SQL_CREATE_TABLE = "CREATE TABLE ";
    public static final String SQL_FOREIGN_KEY = "FOREIGN KEY";
    public static final String SQL_INTEGER = "INTEGER";
    public static final String SQL_NOT_NULL = "NOT NULL";
    public static final String SQL_PRIMARY_KEY = "PRIMARY KEY";
    public static final String SQL_REAL = "REAL";
    public static final String SQL_REFERENCES = "REFERENCES";
    public static final String SQL_TEXT = "TEXT";
    public static final String SQL_UNIQUE = "UNIQUE";
    public static final String SQL_DELETE_FROM = "DELETE FROM";
    public static final String SQL_ON_CONFLICT_REPLACE = "ON CONFLICT REPLACE";
    public static final String TAG = "DbHelper";
    private static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS ";
    private static int databaseVersion = 3;
    private static DbHelper DbHelper = null;

    public DbHelper(Context context) {
        super(context, "JPWR.db", null, databaseVersion);
    }

    public static synchronized DbHelper getDbHelper(Context context) {
        if (DbHelper == null) {
            DbHelper = new DbHelper(context);
        }
        return DbHelper;
    }

    public static int getDatabaseVersion() {
        return databaseVersion;
    }

    public static void setDatabaseVersion(int databaseVersion) {
        DbHelper.databaseVersion = databaseVersion;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbHelperSpeakers.CREATE_TABLE);
        db.execSQL(DbHelperLectures.CREATE_TABLE);
        db.execSQL(DbHelperPartners.CREATE_TABLE);
        db.execSQL(DbHelperOrganizers.CREATE_TABLE);
        db.execSQL(DbHelperLecturesSpeakers.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "db update");
        db.execSQL(SQL_DROP_TABLE + " " + DbHelperSpeakers.TABLE_NAME);
        db.execSQL(SQL_DROP_TABLE + " " + DbHelperLectures.TABLE_NAME);
        db.execSQL(SQL_DROP_TABLE + " " + DbHelperPartners.TABLE_NAME);
        db.execSQL(SQL_DROP_TABLE + " " + DbHelperOrganizers.TABLE_NAME);
        db.execSQL(SQL_DROP_TABLE + " " + DbHelperLecturesSpeakers.CREATE_TABLE);
        onCreate(db);
    }
}
