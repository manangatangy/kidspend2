package com.wolfie.kidspend2.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.wolfie.kidspend2.model.database.MetaData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * The primary data class, held on the database and represented in the ListFragment.
 * Only the mId field is unique.
 */
public class Spend implements Parcelable {

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
        // Formatted in the database as "1 DEC 2014"
        return mCreated;
    }

    public String getCreatedSortable() {
        // Formatted for sorting as "20141201"
        return parseDateString(mCreated);
    }

    public static String parseDateString(String dateString) {
        if (dateString == null) {
            return null;
        }
        String fields[] = dateString.split(" ");
        if (fields.length != 3) {
            return null;
        }
        if (mMonths.get(fields[1].toLowerCase()) == null) {
            return null;
        }
        if (fields[0].length() < 2) {
            fields[0] = "0" + fields[0];
        }
        String sortable = fields[2] + mMonths.get(fields[1].toLowerCase()) + fields[0];
        return sortable;
    }

    public static String getToday() {
        SimpleDateFormat formatter = new SimpleDateFormat("d MMM yyyy");
        String format = formatter.format(new Date());
        return format;
    }

    static private HashMap<String, String> mMonths;
    static {
        mMonths = new HashMap<>();
        mMonths.put("jan", "01");
        mMonths.put("feb", "02");
        mMonths.put("mar", "03");
        mMonths.put("apr", "04");
        mMonths.put("may", "05");
        mMonths.put("jun", "06");
        mMonths.put("jul", "07");
        mMonths.put("aug", "08");
        mMonths.put("sep", "09");
        mMonths.put("oct", "00");
        mMonths.put("nov", "11");
        mMonths.put("dec", "12");
    }
    public void setCreated(String created) {
        mCreated = created;
    }

    public static final Creator<Spend> CREATOR = new Creator<Spend>() {
        @Override
        public Spend createFromParcel(Parcel in) {
            return new Spend(in);
        }
        @Override
        public Spend[] newArray(int size) {
            return new Spend[size];
        }
    };

    protected Spend(Parcel in) {
        mId = in.readInt();
        mGirl = in.readString();
        mSpendType = in.readString();
        mAmount = in.readInt();
        mCreated = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeInt(mId);
        dest.writeString(mGirl);
        dest.writeString(mSpendType);
        dest.writeInt(mAmount);
        dest.writeString(mCreated);
    }
}
