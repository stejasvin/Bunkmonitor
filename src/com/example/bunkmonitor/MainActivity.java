package com.example.bunkmonitor;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Math.random;

public class MainActivity extends Activity {

    private static final int ENTRYLIST = 10;
    private static final int ADDNEWCOURSE = 20;
    private static final int REQUEST_CHECK_ENTRY = 30;
    private static final String TAG = "MainActivity";
    public static final int REFRESH = 40;
    //    private ListView list;
    CoursesExpListAdapter adapter;
    List<Course> cList;
    //List<Boolean> isExpanded = new ArrayList<Boolean>();
    private ExpandableListView expList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.courses);

        SharedPreferences mPrefs = getSharedPreferences(
                "bunkmonitor.SHARED_PREF", 0);
        if (mPrefs.getString("MONDAY", null) == null)
            initializePrefs();

//        //Checking for entry once
        if(!mPrefs.getBoolean("SNOOZE",false)){
            Intent downloader = new Intent(this, DailyNotifService.class);
            startService(downloader);
        }
        //Demo();

        final Button imDef = (Button) findViewById(R.id.textView2);
        imDef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imDef.setEnabled(false);
                Intent intent = new Intent(MainActivity.this, AddNewCourse.class);
                startActivityForResult(intent, ADDNEWCOURSE);
                imDef.setEnabled(true);
            }
        });

        final Button bSet = (Button) findViewById(R.id.b_listview);
        bSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bSet.setEnabled(false);
                Intent intent = new Intent(MainActivity.this, BunkList.class);
                startActivity(intent);
                bSet.setEnabled(true);
            }
        });


        final Button bEntry = (Button) findViewById(R.id.courses_entry_b);
        bEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bEntry.setEnabled(false);
                Intent intent = new Intent(MainActivity.this,EntryActivity.class);
                intent.putExtra("TODAY_ENTRY",true);
                startActivityForResult(intent, REFRESH);
                bEntry.setEnabled(true);
            }
        });


        CourseDatabaseHandler courseDatabaseHandler = new CourseDatabaseHandler(this);
        cList = courseDatabaseHandler.getAllCourses();
        if (cList.isEmpty())
            imDef.setVisibility(View.VISIBLE);
        else {
            EntryDetailsDatabaseHandler entryDetailsDatabaseHandler = new EntryDetailsDatabaseHandler(this);
            HashMap<String,ArrayList<String>> hashMap = entryDetailsDatabaseHandler.getBunksDates();

            //initiate boolean list

            expList = new ExpandableListView(this);
            expList.setGroupIndicator(null);
            expList.setDivider(null);
            adapter = new CoursesExpListAdapter(this, R.layout.single_list_item_courses,R.layout.single_list_item_courses_child, cList,hashMap);
            expList.setAdapter(adapter);
            expList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MainActivity.this, optionsLongPress.class);
                    intent.putExtra("IS_EDIT", true);

                    //calculating position
                    int index = 0,temp=0;
                    for(Boolean b:adapter.getIsExpanded()){
                        if(temp==position)
                            break;
                        index++;
                        temp++;
                        if(b.booleanValue())
                            temp++;

                    }
                    intent.putExtra("COURSE_LID", cList.get(index).getLocalId());
                    startActivityForResult(intent, REFRESH);
                    return false;
                }
            });

            LinearLayout ll = (LinearLayout) findViewById(R.id.c_list_layout);
            ll.addView(expList);
        }

        Button bAddCourses = (Button) findViewById(R.id.b_courses);
        bAddCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNewCourse.class);
                startActivityForResult(intent, ADDNEWCOURSE);

            }
        });

    }

    void initializePrefs() {
        SharedPreferences mPrefs = getSharedPreferences(
                "bunkmonitor.SHARED_PREF", 0);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putInt("NOTIF_TIME",1700);
        mEditor.putString("MONDAY", "").commit();
        mEditor.putString("TUESDAY", "").commit();
        mEditor.putString("WEDNESDAY", "").commit();
        mEditor.putString("THURSDAY", "").commit();
        mEditor.putString("FRIDAY", "").commit();
        mEditor.putString("SATURDAY", "").commit();
        mEditor.putString("SUNDAY", "").commit();

        mEditor.putString("LAST_SLOT","a");

        //Set recurring alarms if not snoozed
        if(!mPrefs.getBoolean("SNOOZE",false))
            Utilities.setRecurringAlarm(this,0);
    }

    void Demo() {
        //For Demo, populating entries and courses
        CourseDatabaseHandler courseDatabaseHandler = new CourseDatabaseHandler(this);
        Course course = new Course();
        course.setId("ID:" + (int) (random() * 10000));
        course.setCredits("" + (int) (random() * 10));
        course.setName("C:" + course.getId());
        course.setProf("Prof ABC");
        course.setSlot("A");
        course.setAttended(0);
        course.setBunked(0);
        course.setCancelled(0);
        course.setActive(1);
        courseDatabaseHandler.addCourse(course);

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection

        Intent intent;
        switch (item.getItemId()) {
//            case R.id.timetable_settings:
//
//                intent = new Intent(MainActivity.this, EditSlotsActivity.class);
//                startActivity(intent);
//
//                return true;
//
            case R.id.manual_settings:

                intent = new Intent(MainActivity.this, EditEntryActivity.class);
                startActivityForResult(intent,REFRESH);
                return true;

            case R.id.settings:

                intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REFRESH) {
            Intent i = getIntent();
            finish();
            startActivity(i);
        }
        if (resultCode == RESULT_OK)
            if (requestCode == ENTRYLIST) {
                Intent i = getIntent();
                finish();
                startActivity(i);

            } else if (requestCode == ADDNEWCOURSE) {
                Intent i = getIntent();
                finish();
                startActivity(i);
            }
//            else if (requestCode == REQUEST_CHECK_ENTRY) {
//                Intent ret = new Intent(this, EntryActivity.class);
//                ret.putExtra("date", Utilities.getDate(data.getStringExtra("date")));
//                ret.putExtra("bunkmonitor.MODE", Utilities.READ);
//                //this.setResult(RESULT_OK, ret);
//                startActivity(ret);
//            }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Intent i = getIntent();
//        finish();
//        startActivity(i);
        if (adapter != null) {
            Button imDef = (Button) findViewById(R.id.textView2);
            imDef.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, AddNewCourse.class);
                    startActivityForResult(intent, ADDNEWCOURSE);
                }
            });
            CourseDatabaseHandler courseDatabaseHandler = new CourseDatabaseHandler(this);
            adapter.cList = courseDatabaseHandler.getAllCourses();
//        if(cList.isEmpty())
//            tvDef.setVisibility(View.VISIBLE);
//        else{
//
            //DataSetObserver o;
            //adapter.registerDataSetObserver(o);
        }

        SharedPreferences mPrefs = getSharedPreferences(
                "bunkmonitor.SHARED_PREF", 0);
        String lastEntryDate = mPrefs.getString("bunkmonitor.LAST_ENTRY_DATE", "0");
        String today = Utilities.getDate(Utilities.getCurrentTime());
        Button entry = (Button)findViewById(R.id.courses_entry_b);
        if(today.equals(lastEntryDate))
            entry.setEnabled(false);
        else
            entry.setEnabled(true);

        ///list.invalidateViews();

//        }
    }


}
