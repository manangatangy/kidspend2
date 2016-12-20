package com.wolfie.kidspend2.model;

import android.database.Cursor;

import com.google.gson.annotations.Expose;
import com.wolfie.kidspend2.model.database.MetaData;

/**
 * The primary data class, held on the database and represented in the ListFragment.
 * Only the mId field is unique.
 */
public class Spend {

    private int mId = -1;
    @Expose
    private String mGirl;
    @Expose
    private String mSpendType;
    @Expose
    private int mAmount;
    @Expose
    private String mCreated;

    public Spend() {
    }

    private Spend(int id, String girl, String spendType, int amount, String created) {
        mId = id;
        mGirl = girl;
        mSpendType = spendType;
        mAmount = amount;
        mCreated = created;
    }

    public boolean isNew() {
        return mId == -1;
    }

    public static Spend create(String girl, String spendType, int amount, String created) {
        Spend spend = new Spend(-1, girl, spendType, amount, created);
        return spend;
    }

    public static Spend from(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(MetaData.SPENDS_ID));
        String girl = cursor.getString(cursor.getColumnIndex(MetaData.SPENDS_GIRL));
        String type = cursor.getString(cursor.getColumnIndex(MetaData.SPENDS_TYPE));
        int amount = cursor.getInt(cursor.getColumnIndex(MetaData.SPENDS_AMOUNT));
        String created = cursor.getString(cursor.getColumnIndex(MetaData.SPENDS_CREATED));

        Spend spend = new Spend(id, girl, type, amount, created);
        return spend;
    }

    public void trim() {
        if (mGirl != null) {
            mGirl = mGirl.trim();
        }
        if (mSpendType != null) {
            mSpendType = mSpendType.trim();
        }
        if (mCreated != null) {
            mCreated = mCreated.trim();
        }
    }

    public int getId() {
        return mId;
    }

    public String getGirl() {
        return mGirl;
    }

    public void setGirl(String girl) {
        this.mGirl = girl;
    }

    public String getSpendType() {
        return mSpendType;
    }

    public void setSpendType(String spendType) {
        this.mSpendType = spendType;
    }

    public int getAmount() {
        return mAmount;
    }

    public String getAmountAsString() {
        return "" + mAmount;
    }

    public void setAmount(int amount) {
        this.mAmount = amount;
    }

    public String getCreated() {
        return mCreated;
    }

    public void setCreated(String created) {
        mCreated = created;
    }
}
