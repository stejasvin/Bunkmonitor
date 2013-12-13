package com.example.bunkmonitor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class EntryDatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "entryManager";

	// courseions table name
	private static final String TABLE_ENTRY = "entry";

	// courseions Table Columns names
	public static final String KEY_LOCAL_ID = "l_id";
	public static final String KEY_COURSE_ID = "course_id";
	public static final String KEY_STATUS = "status";
    public static final String KEY_TIME = "time";

    public static final String KEY_SLOT = "slot";
    public static final String KEY_ENTERED = "entered";
    private static final String TAG = "entryDatabaseHandler";

    public EntryDatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_courseS_TABLE = "CREATE TABLE " + TABLE_ENTRY + "("
				+ KEY_LOCAL_ID + " INTEGER PRIMARY KEY,"
                + KEY_COURSE_ID + " TEXT UNIQUE,"
				+ KEY_STATUS + " INT,"
                + KEY_TIME + " TEXT,"
                + KEY_SLOT + " TEXT,"
                + KEY_ENTERED + " INT DEFAULT 1" +")";
		db.execSQL(CREATE_courseS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENTRY);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	public void addEntry(Entry entry) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		 // course primary key

        values.put(KEY_LOCAL_ID, entry.getL_id());
        values.put(KEY_COURSE_ID, entry.getCourse_id());
        values.put(KEY_STATUS, entry.getStatus());
        values.put(KEY_TIME, entry.getTime());

        values.put(KEY_SLOT, entry.getSlot());
        values.put(KEY_ENTERED, entry.getEntered());
		// Inserting Row
		if(db.insert(TABLE_ENTRY, null, values)==-1)
            Log.e(TAG, "error in inserting");
		db.close(); // Closing database connection
	}

	public Entry getEntry(String l_id) {
        if(l_id==null)
            return null;

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_ENTRY, new String[]{"*"},
                KEY_LOCAL_ID + "=?",
				new String[] { l_id }, null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		Entry entry = new Entry();
        entry.setL_id(cursor.getString(0));
        entry.setCourse_id(cursor.getString(1));
        entry.setStatus(cursor.getInt(2));
        entry.setTime(cursor.getString(3));

        entry.setSlot(cursor.getString(4));
        entry.setEntered(cursor.getInt(5));

        db.close();
		return entry;
	}
	
	public List<Entry> getAllNewEntry() {
		List<Entry> entryList = new ArrayList<Entry>();
		// Select All Query
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ENTRY, new String[]{"*"},
                KEY_ENTERED + "=0",
                new String[] {}, null, null, null, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
                Entry entry = new Entry();
                entry.setL_id(cursor.getString(0));
                entry.setCourse_id(cursor.getString(1));
                entry.setStatus(cursor.getInt(2));
                entry.setTime(cursor.getString(3));

                entry.setSlot(cursor.getString(4));
                entry.setEntered(cursor.getInt(5));

                // Adding course to list
			    entryList.add(entry);
			} while (cursor.moveToNext());
		}
        db.close();

        // return course list
		return entryList;
	}

}
