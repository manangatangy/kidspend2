package com.wolfie.kidspend2.model.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wolfie.kidspend2.model.Girl;
import com.wolfie.kidspend2.model.Spend;

import java.util.ArrayList;
import java.util.List;

import static com.wolfie.kidspend2.model.database.MetaData.SPENDS_GIRL;

/**
 * Wraps a database to provide the basic CRUD operations for the spends table.
 * These methods are best called in a background thread, using a loader.
 */
public class Source {

    private SQLiteDatabase mDatabase;

    public Source(SQLiteDatabase database) {
        mDatabase = database;
    }

    public boolean insert(Spend spend) {
        long result = mDatabase.insert(MetaData.SPENDS_TABLE, null, makeContentValues(spend));
        return result != -1;
    }

    public boolean update(Spend spend) {
        int result = mDatabase.update(MetaData.SPENDS_TABLE, makeContentValues(spend),
                                      MetaData.SPENDS_ID + "=" + spend.getId(), null);
        return result != 0;
    }

    public boolean delete(Spend spend) {
        int result =  mDatabase.delete(MetaData.SPENDS_TABLE, MetaData.SPENDS_ID + "=" + spend.getId(), null);
        return result != 0;
    }

    public void deleteAll() {
        mDatabase.delete(MetaData.SPENDS_TABLE, null, null);
    }

    public @NonNull List<Spend> read(@Nullable Girl girl) {
        String selection = (girl == null) ? null :
                           (MetaData.SPENDS_GIRL + " = '" + girl.name() + "'");
        List<Spend> spends = new ArrayList<>();
        Cursor cursor = mDatabase.query(MetaData.SPENDS_TABLE, MetaData.SPENDS_ALL_COLUMNS,
                                        selection,
                                        null, null, null, MetaData.SPENDS_ID);
        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Spend spend = Spend.from(cursor);
                spends.add(spend);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return spends;
    }

    private ContentValues makeContentValues(Spend spend) {
        ContentValues values = new ContentValues();
        values.put(SPENDS_GIRL, spend.getGirl());
        values.put(MetaData.SPENDS_TYPE, spend.getSpendType());
        values.put(MetaData.SPENDS_AMOUNT, spend.getAmount());
        values.put(MetaData.SPENDS_CREATED, spend.getCreated());
        return values;
    }

}
