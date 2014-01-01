package com.example.bunkmonitor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EntryDetailsDatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "entryDetailsManager";

	// courseions table name
	private static final String TABLE_ENTRY = "entryDetails";

	// courseions Table Columns names
	public static final String KEY_LOCAL_ID = "l_id";
	public static final String KEY_COURSE_ID = "course_id";
    public static final String KEY_STATUS = "status";
    public static final String KEY_TIME = "time";
    public static final String KEY_ENTERED = "entered";

    private static final String TAG = "entryDetailsDatabaseHandler";

    public EntryDetailsDatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_courseS_TABLE = "CREATE TABLE " + TABLE_ENTRY + "("
				+ KEY_LOCAL_ID + " INTEGER PRIMARY KEY,"
                + KEY_COURSE_ID + " TEXT,"
				+ KEY_STATUS + " INT,"
                + KEY_TIME + " TEXT,"
                + KEY_ENTERED + " INT "+")";
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

	public void addEntry(EntryDetails entryDetails) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		 // course primary key

        values.put(KEY_LOCAL_ID, entryDetails.getL_id());
        values.put(KEY_COURSE_ID, entryDetails.getCourse_id());
        values.put(KEY_STATUS , entryDetails.getStatus());
        values.put(KEY_TIME, entryDetails.getTime());
        values.put(KEY_ENTERED, entryDetails.getEntered());

		// Inserting Row
		if(db.insert(TABLE_ENTRY, null, values)==-1)
            Log.e(TAG, "error in inserting");
		db.close(); // Closing database connection
	}

    public void updateEntryDetails(EntryDetails entryDetails) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LOCAL_ID, entryDetails.getL_id());
        values.put(KEY_COURSE_ID, entryDetails.getCourse_id());
        values.put(KEY_STATUS , entryDetails.getStatus());
        values.put(KEY_TIME, entryDetails.getTime());
        values.put(KEY_ENTERED, entryDetails.getEntered());

        db.update(TABLE_ENTRY,
                values,
                KEY_LOCAL_ID + "=?",
                new String[]{entryDetails.getL_id()});
        db.close();
    }

	public EntryDetails getEntryDetails(String l_id) {
        if(l_id==null)
            return null;

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_ENTRY, new String[]{"*"},
                KEY_LOCAL_ID + "=?",
				new String[] { l_id }, null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		EntryDetails entryDetails = new EntryDetails();
        entryDetails.setL_id(cursor.getString(0));
        entryDetails.setCourse_id(cursor.getString(1));
        entryDetails.setStatus(cursor.getInt(2));
        entryDetails.setTime(cursor.getString(3));
        entryDetails.setEntered(cursor.getInt(4));


        db.close();
		return entryDetails;
	}
	
	public List<EntryDetails> getAllActiveEntryDetails() {
		List<EntryDetails> entryDetailsList = new ArrayList<EntryDetails>();
		// Select All Query
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ENTRY, new String[]{"*"},
                KEY_TIME + "=1",
                new String[] {}, null, null, null, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
                EntryDetails entryDetails = new EntryDetails();
                entryDetails.setL_id(cursor.getString(0));
                entryDetails.setCourse_id(cursor.getString(1));
                entryDetails.setStatus(cursor.getInt(2));
                entryDetails.setTime(cursor.getString(3));
                entryDetails.setEntered(cursor.getInt(4));

                // Adding course to list
			    entryDetailsList.add(entryDetails);
			} while (cursor.moveToNext());
		}
        db.close();

        // return course list
		return entryDetailsList;
	}

    public List<Entry> getEntryListByDate(String date) {

        if(date==null)
            return null;

        List<Entry> entryList = new ArrayList<Entry>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ENTRY, new String[]{"*"},
                KEY_TIME + "=?",
                new String[] { date }, null, null, null, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Entry entry = new Entry();
                entry.setCourse_id(cursor.getString(1));

                int status = cursor.getInt(2);
                switch (status){
                    case Utilities.ATTENDED:
                        entry.setAttended(1);
                        break;
                    case Utilities.BUNKED:
                        entry.setBunked(1);
                        break;
                    case Utilities.CANCELLED:
                        entry.setCancelled(1);
                        break;
                }


//                EntryDetails entryDetails = new EntryDetails();
//                entryDetails.setL_id(cursor.getString(0));
//                entryDetails.setCourse_id(cursor.getString(1));
//                entryDetails.setStatus(cursor.getInt(2));
//                entryDetails.setTime(cursor.getString(3));
//                entryDetails.setEntered(cursor.getInt(4));

                // Adding course to list
                entryList.add(entry);
            } while (cursor.moveToNext());
        }
        db.close();

        return entryList;

    }

    public void batchEdit(Context context, String fromDate, String toDate, int mode){
        String[] sf = fromDate.split("/");
        int fDay = Integer.decode(sf[0]),fMonth = Integer.decode(sf[1])-1,fYear = Integer.decode(sf[2]);
        String[] st = toDate.split("/");
        int tDay = Integer.decode(st[0]),tMonth = Integer.decode(st[1]),tYear = Integer.decode(st[2]);
//        int day=fDay,month=fMonth,year=fYear;


        CourseDatabaseHandler courseDatabaseHandler = new CourseDatabaseHandler(context);
        List<Course> cList = courseDatabaseHandler.getAllActiveCourses();

        boolean flag = true;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, fDay);
        cal.set(Calendar.MONTH,fMonth);
        cal.set(Calendar.YEAR,fYear);

//        CalendarView calView = new CalendarView(context);
        EntryDetails entryDetails = new EntryDetails();

        while(flag){
            for(Course c: cList){
                if(c.getActive()==0)
                    continue;

                entryDetails.setCourse_id(c.getId());
                entryDetails.setStatus(mode);
                entryDetails.setTime(Utilities.getDate(cal.getTime().toString()));
                entryDetails.setEntered(1);
                entryDetails.setSlot(c.getSlot());
                addEntry(entryDetails);
                switch (mode){
                    case Utilities.ATTENDED: c.incAttended();break;
                    case Utilities.BUNKED: c.incBunked();break;
                    case Utilities.CANCELLED: c.incCancelled();break;
                }
                courseDatabaseHandler.updateCourse(c);
            }

            cal.add(Calendar.DAY_OF_MONTH,1);

            if(Utilities.getDate(cal.getTime().toString()).equals(toDate))
                flag = false;
        }

    }
    /*public List<EntryDetails> getActiveDiffList() {
        List<EntryDetails> entryDetailsList = new ArrayList<EntryDetails>();
        // Select All Query
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ENTRY, new String[]{"*"},
                KEY_TIME + "=1",
                new String[] {}, null, null, null, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                EntryDetails entryDetails = new EntryDetails();
                entryDetails.setL_id(cursor.getString(0));
                entryDetails.setCourse_id(cursor.getString(1));
                entryDetails.setAttended(0);
                entryDetails.setBunked(0);
                entryDetails.setCancelled(0);
                entryDetails.setActive(cursor.getInt(5));

                // Adding course to list
                entryDetailsList.add(entryDetails);
            } while (cursor.moveToNext());
        }
        db.close();

        // return course list
        return entryDetailsList;
    }*/

}
