package com.wolfie.kidspend2.model.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Helper extends SQLiteOpenHelper {

    private static final String TAG = "Helper";

    public Helper(Context context) {
        super(context, MetaData.DATABASE_NAME, null, MetaData.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE " + MetaData.SPENDS_TABLE + " ("
                               + MetaData.SPENDS_ID + " INTEGER PRIMARY KEY,"
                               + MetaData.SPENDS_GIRL + " TEXT NOT NULL,"
                               + MetaData.SPENDS_TYPE + " TEXT NOT NULL,"
                               + MetaData.SPENDS_AMOUNT + " INTEGER NOT NULL,"
                               + MetaData.SPENDS_CREATED + " TEXT NOT NULL );");
        } catch (SQLException e) {
            Log.d(TAG, "SQLite exception: " + e.getLocalizedMessage());
        }
    }

    public void dropTables(SQLiteDatabase db) {
        try {
            db.execSQL("DROP TABLE " + MetaData.SPENDS_TABLE);
        } catch (SQLException e) {
            Log.d(TAG, "SQLite exception: " + e.getLocalizedMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "upgrading database from version " + oldVersion + " to " + newVersion);
        if (oldVersion == 1 && newVersion == 2) {
            // Execute sql to migrate from oldVersion to newVersion
        }
        onCreate(db);
    }
}
