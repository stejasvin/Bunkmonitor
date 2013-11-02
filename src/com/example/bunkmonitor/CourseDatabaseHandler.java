package com.example.bunkmonitor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.bunkmonitor.CourseDatabaseHandler.KEY_CREDITS;

public class CourseDatabaseHandler extends SQLiteOpenHelper {

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
    private static final String TAG = "courseDatabaseHandler";

    public CourseDatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_courseS_TABLE = "CREATE TABLE " + TABLE_COURSES + "("
				+ KEY_LOCAL_ID + " INTEGER PRIMARY KEY,"
                + KEY_ID + " TEXT UNIQUE,"
				+ KEY_NAME + " TEXT,"
                + KEY_CREDITS + " TEXT,"
                + KEY_SLOT + " TEXT,"
                + KEY_PROF + " TEXT" +")";
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

		// Inserting Row
		if(db.insert(TABLE_COURSES, null, values)==-1)
            Log.e(TAG, "error in inserting");
		db.close(); // Closing database connection
	}

	// Getting single course
	public Course getCourse(String id) {
        if(id==null)
            return null;

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_COURSES, new String[]{"*"},
                KEY_ID + "=?",
				new String[] { id }, null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		Course course = new Course();
        course.setLocalId(cursor.getString(0));
        course.setId(cursor.getString(1));
        course.setName(cursor.getString(2));
        course.setCredits(cursor.getString(3));
        course.setSlot(cursor.getString(4));
        course.setProf(cursor.getString(5));

		// return course

        db.close();
		return course;
	}
	
	// Getting All courses
	public List<Course> getAllCourses() {
		List<Course> courseList = new ArrayList<Course>();
		// Select All Query
		String selectQuery = "SELECT * FROM " + TABLE_COURSES;


		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

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

                // Adding course to list
			    courseList.add(course);
			} while (cursor.moveToNext());
		}
        db.close();

        // return course list
		return courseList;
	}

}
