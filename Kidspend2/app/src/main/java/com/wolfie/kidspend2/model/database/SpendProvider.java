package com.wolfie.kidspend2.model.database;

public class SpendProvider {}
//        extends ContentProvider {
//    public static final String TAG = "SpendProvider";
//
//    private static HashMap<String, String> spendProjectionMap;
//    static {
//        spendProjectionMap = new HashMap<String, String>();
//        spendProjectionMap.put(TableMetaData._ID, TableMetaData._ID);
//        spendProjectionMap.put(TableMetaData.SPEND_DATE, TableMetaData.SPEND_DATE);
//        spendProjectionMap.put(TableMetaData.SPEND_TYPE, TableMetaData.SPEND_TYPE);
//        spendProjectionMap.put(TableMetaData.SPEND_AMOUNT, TableMetaData.SPEND_AMOUNT);
//        spendProjectionMap.put(TableMetaData.SPEND_ACCOUNT, TableMetaData.SPEND_ACCOUNT);
//    }
//
//    private static final UriMatcher uriMatcher;
//    private static final int SPEND_URI_INDICATOR_COLLECTION = 1;
//    private static final int SPEND_URI_INDICATOR_SINGLE = 2;
//    private static final int SPEND_URI_INDICATOR_COLLECTION_GROUP_BY = 3;
//    static {
//        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
//        uriMatcher.addURI(ProviderMetaData.AUTHORITY, "spends", SPEND_URI_INDICATOR_COLLECTION);
//        uriMatcher.addURI(ProviderMetaData.AUTHORITY, "spends/#", SPEND_URI_INDICATOR_SINGLE);
//        uriMatcher.addURI(ProviderMetaData.AUTHORITY, "spends/groupBy/*", SPEND_URI_INDICATOR_COLLECTION_GROUP_BY);		// * = group by column
//    }
//
//    @Override
//    public String getType(Uri uri) {
//        switch(uriMatcher.match(uri)) {
//        case SPEND_URI_INDICATOR_COLLECTION:
//            return TableMetaData.SPEND_CONTENT_TYPE;
//        case SPEND_URI_INDICATOR_SINGLE:
//            return TableMetaData.SPEND_CONTENT_ITEM_TYPE;
//        case SPEND_URI_INDICATOR_COLLECTION_GROUP_BY:
//            return TableMetaData.SPEND_CONTENT_TYPE;
//        default:
//            throw new IllegalArgumentException("Unknown URI " + uri);
//        }
//    }
//
//    private static class DatabaseHelper extends SQLiteOpenHelper {
//        DatabaseHelper(Context context) {
//            super(context, ProviderMetaData.DATABASE_NAME, null, ProviderMetaData.DATABASE_VERSION);
//        }
//
//        @Override
//        public void onCreate(SQLiteDatabase db) {
//            db.execSQL("CREATE TABLE IF NOT EXISTS " + TableMetaData.SPEND_TABLE_NAME + " ("
//                               + TableMetaData._ID + " INTEGER PRIMARY KEY,"
//                               + TableMetaData.SPEND_DATE + " TEXT,"
//                               + TableMetaData.SPEND_TYPE + " TEXT,"
//                               + TableMetaData.SPEND_ACCOUNT + " TEXT,"
//                               + TableMetaData.SPEND_AMOUNT + " INTEGER" + ");");
//        }
//
//        @Override
//        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            Log.w(TAG, "upgrading database from version " + oldVersion + " to " + newVersion);
//            if (oldVersion == 1 && newVersion == 2) {
//                db.execSQL("ALTER TABLE " + TableMetaData.SPEND_TABLE_NAME + " ADD COLUMN " + TableMetaData.SPEND_ACCOUNT + " TEXT");
//                db.execSQL("UPDATE " + TableMetaData.SPEND_TABLE_NAME + " SET " + TableMetaData.SPEND_ACCOUNT + " = 'PERSONAL'");
//            }
//            //db.execSQL("DROP TABLE IF EXISTS " + SpendsTableMetaData.TABLE_NAME);
//            onCreate(db);
//        }
//    }
//
//    private DatabaseHelper openHelper;
//
//    @Override
//    public boolean onCreate() {
//        openHelper = new DatabaseHelper(getContext());
//        return true;
//    }
//
//    @Override
//    public Cursor query(Uri uri, String[] projection, String selection,
//                        String[] selectionArgs, String sortOrder) {
//        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
//        String groupBy = null;
//        switch(uriMatcher.match(uri)) {
//        case SPEND_URI_INDICATOR_SINGLE:
//            qb.setTables(TableMetaData.SPEND_TABLE_NAME);
//            qb.setProjectionMap(spendProjectionMap);
//            qb.appendWhere(TableMetaData._ID + "=" + uri.getPathSegments().get(1));
//            break;
//        case SPEND_URI_INDICATOR_COLLECTION:
//            qb.setTables(TableMetaData.SPEND_TABLE_NAME);
//            qb.setProjectionMap(spendProjectionMap);
//            break;
//        case SPEND_URI_INDICATOR_COLLECTION_GROUP_BY:
//            qb.setTables(TableMetaData.SPEND_TABLE_NAME);
//            qb.setProjectionMap(spendProjectionMap);
//            groupBy = uri.getPathSegments().get(2);
//            break;
//        default:
//            throw new IllegalArgumentException("Unknown URI " + uri);
//        }
//        if (TextUtils.isEmpty(sortOrder)) {
//            sortOrder = TableMetaData.DEFAULT_SORT_ORDER;
//        }
//        SQLiteDatabase db = openHelper.getReadableDatabase();
//        Cursor c = qb.query(db, projection, selection, selectionArgs, groupBy, null, sortOrder);
//        int i = c.getCount();
//        Log.w(TAG, "selection:" + selection + ", count=" + i);
//        // Tell cursor which uri to watch, so it knows when its source data changes.
//        c.setNotificationUri(getContext().getContentResolver(), uri);
//        return c;
//    }
//
//    @Override
//    public Uri insert(Uri uri, ContentValues values) {
//        int match = uriMatcher.match(uri);
//        String tableName;
//        Uri tableUri;
//
//        if (match == SPEND_URI_INDICATOR_COLLECTION) {
//            // Ensure that all the fields are set.
//            checkFieldIsPresent(values, TableMetaData.SPEND_DATE, uri);
//            checkFieldIsPresent(values, TableMetaData.SPEND_TYPE, uri);
//            checkFieldIsPresent(values, TableMetaData.SPEND_AMOUNT, uri);
//            checkFieldIsPresent(values, TableMetaData.SPEND_ACCOUNT, uri);
//            tableName = TableMetaData.SPEND_TABLE_NAME;
//            tableUri = TableMetaData.SPEND_CONTENT_URI;
//        } else {
//            throw new IllegalArgumentException("Unknown URI " + uri);
//        }
//
//        SQLiteDatabase db = openHelper.getWritableDatabase();
//        long rowId = db.insert(tableName, null, values);
//        if (rowId <= 0) {
//            throw new SQLException("Failed to insert row into " + uri);
//        }
//
//        //Log.v(TAG, "inserted: _ID=" + rowId);
//        Uri insertedUri = ContentUris.withAppendedId(tableUri, rowId);
//        getContext().getContentResolver().notifyChange(insertedUri, null);
//        return insertedUri;
//    }
//
//    private void checkFieldIsPresent(ContentValues values, String field, Uri uri) {
//        if (values.containsKey(field) == false) {
//            throw new SQLException("Failed to insert row because " + field + " date is needed" + uri);
//        }
//    }
//
//    @Override
//    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
//        SQLiteDatabase db = openHelper.getWritableDatabase();
//        int count;
//        switch(uriMatcher.match(uri)) {
//        case SPEND_URI_INDICATOR_COLLECTION:
//            count = db.update(TableMetaData.SPEND_TABLE_NAME, values, where, whereArgs);
//            //Log.v(TAG, "updated: count=" + count);
//            break;
//        case SPEND_URI_INDICATOR_SINGLE:
//            String rowId = uri.getPathSegments().get(1);
//            count = db.update(TableMetaData.SPEND_TABLE_NAME, values, whereClause(rowId, where), whereArgs);
//            //Log.v(TAG, "updated: _ID=" + rowId);
//            break;
//        default:
//            return 0;		// temp
//        //throw new IllegalArgumentException("Unknown URI " + uri);
//        }
//        getContext().getContentResolver().notifyChange(uri, null);
//        return count;
//    }
//
//    @Override
//    public int delete(Uri uri, String where, String[] whereArgs) {
//        SQLiteDatabase db = openHelper.getWritableDatabase();
//        int count;
//        switch(uriMatcher.match(uri)) {
//        case SPEND_URI_INDICATOR_COLLECTION:
//            count = db.delete(TableMetaData.SPEND_TABLE_NAME, where, whereArgs);
//            //Log.v(TAG, "deleted: count=" + count);
//            break;
//        case SPEND_URI_INDICATOR_SINGLE:
//            String rowId1 = uri.getPathSegments().get(1);
//            count = db.delete(TableMetaData.SPEND_TABLE_NAME, whereClause(rowId1, where), whereArgs);
//            //Log.v(TAG, "deleted: _ID=" + rowId1);
//            break;
//        default:
//            throw new IllegalArgumentException("Unknown URI " + uri);
//        }
//        getContext().getContentResolver().notifyChange(uri, null);
//        return count;
//    }
//
//    private String whereClause(String rowId, String where) {
//        return TableMetaData._ID + "=" + rowId + (TextUtils.isEmpty(where) ? "" : " AND (" + where + ")");
//    }
//
//}
