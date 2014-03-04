package com.example.bunkmonitor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class CourseDatabaseHandler extends SQLiteOpenHelper {


    private Context context;
	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "coursesManager";

	// courseions table name
	private static final String TABLE_COURSES = "course";

	// courseions Table Columns names
	public static final String KEY_LOCAL_ID = "l_id";
	public static final String KEY_ID = "id";
	public static final String KEY_CREDITS = "credits";
    public static final String KEY_PROF = "prof";
    public static final String KEY_NAME = "name";
    public static final String KEY_SLOT = "slot";
    public static final String KEY_ATTENDED = "attended";
    public static final String KEY_BUNKED = "bunked";
    public static final String KEY_CANCELLED = "cancelled";
    public static final String KEY_ACTIVE = "active";
    private static final String TAG = "courseDatabaseHandler";

    public CourseDatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_courseS_TABLE = "CREATE TABLE " + TABLE_COURSES + "("
				+ KEY_LOCAL_ID + " INTEGER PRIMARY KEY,"
                + KEY_ID + " TEXT,"
				+ KEY_NAME + " TEXT,"
                + KEY_CREDITS + " TEXT,"
                + KEY_SLOT + " TEXT,"
                + KEY_PROF + " TEXT,"
                + KEY_ATTENDED + " INT,"
                + KEY_BUNKED + " INT,"
                + KEY_CANCELLED + " INT,"
                + KEY_ACTIVE + " INT " +")";
        db.execSQL(CREATE_courseS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSES);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new course
	public void addCourse(Course course) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		 // course primary key

        values.put(KEY_LOCAL_ID, course.getLocalId());
        values.put(KEY_ID, course.getId());
        values.put(KEY_NAME, course.getName());
        values.put(KEY_CREDITS, course.getCredits());
        values.put(KEY_SLOT, course.getSlot());
        values.put(KEY_PROF, course.getProf());
        values.put(KEY_ATTENDED, course.getAttended());
        values.put(KEY_BUNKED, course.getBunked());
        values.put(KEY_CANCELLED, course.getCancelled());
        values.put(KEY_ACTIVE, course.getActive());

		// Inserting Row
		if(db.insert(TABLE_COURSES, null, values)==-1)
            Log.e(TAG, "error in inserting");
		db.close(); // Closing database connection
	}

	// Getting single course
	public Course getCourse(String lid) {
        if(lid==null)
            return null;

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_COURSES, new String[]{"*"},
                KEY_LOCAL_ID + "=?",
				new String[] { lid }, null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		Course course = new Course();
        course.setLocalId(cursor.getString(0));
        course.setId(cursor.getString(1));
        course.setName(cursor.getString(2));
        course.setCredits(cursor.getString(3));
        course.setSlot(cursor.getString(4));
        course.setProf(cursor.getString(5));
        course.setAttended(cursor.getInt(6));
        course.setBunked(cursor.getInt(7));
        course.setCancelled(cursor.getInt(8));
        course.setActive(cursor.getInt(9));

		// return course

        db.close();
		return course;
	}
	
	// Getting All courses
    public List<Course> getAllActiveCourses() {
        List<Course> courseList = new ArrayList<Course>();
        // Select All Query

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_COURSES, new String[]{"*"},
                KEY_ACTIVE + "=1",
                new String[] {}, null, null, null, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Course course = new Course();
                course.setLocalId(cursor.getString(0));
                course.setId(cursor.getString(1));
                course.setName(cursor.getString(2));
                course.setCredits(cursor.getString(3));
                course.setSlot(cursor.getString(4));
                course.setProf(cursor.getString(5));
                course.setAttended(cursor.getInt(6));
                course.setBunked(cursor.getInt(7));
                course.setCancelled(cursor.getInt(8));
                course.setActive(cursor.getInt(9));

                // Adding course to list
                courseList.add(course);
            } while (cursor.moveToNext());
        }
        db.close();

        // return course list
        return courseList;
    }

    // Getting All courses
    public List<Course> getAllCourses() {
        List<Course> courseList = new ArrayList<Course>();
        // Select All Query

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_COURSES, new String[]{"*"},
                null,
                new String[] {}, null, null, null, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Course course = new Course();
                course.setLocalId(cursor.getString(0));
                course.setId(cursor.getString(1));
                course.setName(cursor.getString(2));
                course.setCredits(cursor.getString(3));
                course.setSlot(cursor.getString(4));
                course.setProf(cursor.getString(5));
                course.setAttended(cursor.getInt(6));
                course.setBunked(cursor.getInt(7));
                course.setCancelled(cursor.getInt(8));
                course.setActive(cursor.getInt(9));

                // Adding course to list
                courseList.add(course);
            } while (cursor.moveToNext());
        }
        db.close();

        // return course list
        return courseList;
    }

    public void updateCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, course.getId());
        values.put(KEY_NAME, course.getName());
        values.put(KEY_CREDITS, course.getCredits());
        values.put(KEY_SLOT, course.getSlot());
        values.put(KEY_PROF, course.getProf());
        values.put(KEY_ATTENDED, course.getAttended());
        values.put(KEY_BUNKED, course.getBunked());
        values.put(KEY_CANCELLED, course.getCancelled());
        values.put(KEY_ACTIVE, course.getActive());

        db.update(TABLE_COURSES,
                values,
                KEY_LOCAL_ID + "=?",
                new String[]{course.getLocalId()});
        db.close();
    }

}
