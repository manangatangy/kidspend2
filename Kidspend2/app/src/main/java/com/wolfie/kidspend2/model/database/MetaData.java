package com.wolfie.kidspend2.model.database;

public class MetaData {

    public static final String DATABASE_NAME = "kidspend2.db";
    public static final int DATABASE_VERSION = 1;

    public static final String SPENDS_TABLE = "spends";
    public static final String SPENDS_ID = "_id";
    public static final String SPENDS_GIRL = "girl";
    public static final String SPENDS_TYPE = "type";
    public static final String SPENDS_AMOUNT = "amount";
    public static final String SPENDS_CREATED = "created";
    public static final String[] SPENDS_ALL_COLUMNS = {
            SPENDS_ID, SPENDS_GIRL, SPENDS_TYPE, SPENDS_AMOUNT, SPENDS_CREATED
    };

}
