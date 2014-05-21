package com.example.bunkmonitor;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


/**
 * Created by stejasvin on 12/13/13.
 */
public class EntryActivity extends Activity {

    private static final String TAG = "EntryActivity";
    public static final int EXTRA_LIST = 0;
    int MODE = Utilities.WRITE;

    ListView listView;
    List<Course> cList;
    List<Entry> eList;
    String date;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.entry_sheet);

        //Clear notification
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(0);

        Intent iThis = getIntent();

        MODE = iThis.getIntExtra("bunkmonitor.MODE", Utilities.WRITE);
        //boolean extraMode = iThis.getBooleanExtra("EXTRAS", false);
        final boolean todayEntry = iThis.getBooleanExtra("TODAY_ENTRY", false);

        //To make sure the correct courses are displayed
        date = iThis.getStringExtra("date");
        if (date == null) {
            date = Utilities.getDate(Utilities.getCurrentTime());
            Utilities.clearNotifications(EntryActivity.this);
        }
        String[] s = date.split("/");
        Utilities.processDateArray(s);
        final Calendar cal = Calendar.getInstance();
        cal.set(Integer.decode(s[2]), Integer.decode(s[1]) - 1, Integer.decode(s[0]));

        TextView tvDate = (TextView) findViewById(R.id.entry_date);
        tvDate.setText(date);

        listView = (ListView) findViewById(R.id.listview1);
        listView.setClickable(false);
        listView.setItemsCanFocus(false);
        listView.setCacheColorHint(0);

        final CourseDatabaseHandler courseDatabaseHandler = new CourseDatabaseHandler(this);
        final EntryDetailsDatabaseHandler entryDetailsDatabaseHandler = new EntryDetailsDatabaseHandler(this);

        TextView noCourses = (TextView) findViewById(R.id.entry_no_courses);
        LinearLayout llAll = (LinearLayout) findViewById(R.id.entry_ll);
        LinearLayout llAll1 = (LinearLayout) findViewById(R.id.entry_ll1);
        LinearLayout llAll2 = (LinearLayout) findViewById(R.id.entry_ll2);

        final Button done = (Button) findViewById(R.id.es_b_done);
        final Button attAll = (Button) findViewById(R.id.es_b_attall);
        final Button holiday = (Button) findViewById(R.id.es_b_canc);
        final Button prev = (Button) findViewById(R.id.es_b_prev);
        final Button next = (Button) findViewById(R.id.es_b_next);

        if (todayEntry) {
            prev.setVisibility(View.INVISIBLE);
            next.setVisibility(View.INVISIBLE);
        } else if (date.equals(Utilities.getDate(Utilities.getCurrentTime()))) {
            next.setVisibility(View.INVISIBLE);
        }

        if (MODE == Utilities.WRITE) {
            String clid = getIntent().getStringExtra("COURSE_LOCAL_ID");
            if (clid != null) {
                cList = new ArrayList<Course>();
                cList.add(courseDatabaseHandler.getCourse(clid));
                eList = Entry.getEntryList(cList);
            } else {
                Utilities.toggleActiveCourses(this, cal.get(Calendar.DAY_OF_WEEK));
                cList = courseDatabaseHandler.getAllActiveCourses();
                eList = Entry.getEntryList(cList);

                //Adding Booked Extras
                HashMap<String,Entry> extraList = entryDetailsDatabaseHandler.getEntryListByDate(date);

                //Same courses can also be added more than once.
                for(Course c:cList)
                    if(extraList.containsKey(c.getLocalId()))
                        eList.add(extraList.get(c.getLocalId()));

            }

            //gets an empty list


            //Getting a hashmap of entries on that date and adding to list of courses.
            //The map might not contain all courses available on that date as only
            //those entries will be there
            HashMap<String, Entry> eMap = entryDetailsDatabaseHandler.getEntryListByDate(date);
            if (eMap != null && eMap.size() > 0) {
                for (Entry entry : eList) {
                    if (!eMap.containsKey(entry.getCourse_lid()))
                        continue;
                    Entry temp = eMap.get(entry.getCourse_lid());
                    entry.setAttended(temp.getAttended());
                    entry.setBunked(temp.getBunked());
                    entry.setCancelled(temp.getCancelled());
                    eMap.remove(entry.getCourse_lid());
                }

                //for extra classes
                if (eMap.size() > 0) {
                    List<Course> courseList = courseDatabaseHandler.getAllCourses();
                    for (Course c : courseList) {
                        if (eMap.containsKey(c.getLocalId())) {
                            eList.add(eMap.get(c.getLocalId()));
                            cList.add(c);
                            eMap.remove(c.getLocalId());
                            if (eMap.size() == 0) {

                                break;
                            }
                        }
                    }
                }
            }

            if (eList.size() == 0) {
                noCourses.setVisibility(View.VISIBLE);
                //llAll.setVisibility(View.GONE);
                llAll1.setVisibility(View.GONE);
                listView.setVisibility(View.GONE);
                llAll2.setVisibility(View.INVISIBLE);

            } else {
                EntryListAdapter adapter = new EntryListAdapter(this, eList, MODE);
                listView.setAdapter(adapter);
            }
        }
//        else if(MODE == Utilities.READ){
//            if(date==null){
//                Toast.makeText(this,"Error date null",Toast.LENGTH_LONG).show();
//                finish();
//                return;
//            }
//            done.setVisibility(View.GONE);
//            eList = entryDetailsDatabaseHandler.getEntryListByDate(date);
//            if(eList.size()==0){
//                noCourses.setVisibility(View.VISIBLE);
//                llAll.setVisibility(View.GONE);
//            }else{
//                EntryListAdapter adapter = new EntryListAdapter(this,eList,MODE);
//                listView.setAdapter(adapter);
//            }
//        }

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                done.setEnabled(false);
                postEntry(0);
                done.setEnabled(true);
            }
        });

        attAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attAll.setEnabled(false);
                postEntry(1);
                attAll.setEnabled(true);
            }
        });

        holiday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holiday.setEnabled(false);
                postEntry(2);
                holiday.setEnabled(true);
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                prev.setEnabled(false);
                cal.add(Calendar.DAY_OF_MONTH, -1);
                Intent intent = getIntent();
                intent.putExtra("EXTRAS", false);
                intent.putExtra("date", Utilities.getDate(cal.getTime().toString()));
                finish();
                startActivity(intent);
                prev.setEnabled(true);

            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                next.setEnabled(false);
                cal.add(Calendar.DAY_OF_MONTH, 1);
                Intent intent = getIntent();
                intent.putExtra("EXTRAS", false);
                intent.putExtra("date", Utilities.getDate(cal.getTime().toString()));
                finish();
                startActivity(intent);
                next.setEnabled(true);

            }
        });
//        //Setting alarm for next day
//        Utilities.setRecurringAlarm(this,0);

    }

    private void postEntry(int mode) {

        CourseDatabaseHandler courseDatabaseHandler = new CourseDatabaseHandler(this);
        EntryDetailsDatabaseHandler entryDetailsDatabaseHandler = new EntryDetailsDatabaseHandler(this);
        EntryDetails entryDetails = new EntryDetails();

        if (mode == 1) {
            for (Entry e : eList) {
                e.setAttended(1);
                e.setBunked(0);
                e.setCancelled(0);
            }
        }else if(mode == 2){
            for (Entry e : eList) {
                e.setAttended(0);
                e.setBunked(0);
                e.setCancelled(1);
            }
        }

        for (int i = 0; i < cList.size(); i++) {
            //Adding the difflist entries to orig list and updating the db
            Course c = cList.get(i);
            Entry e = eList.get(i);

            entryDetails.setCourse_lid(c.getLocalId());
            entryDetails.setEntered(1);

            if (e.getAttended() == 1)
                entryDetails.setStatus(Utilities.ATTENDED);
            else if (e.getBunked() == 1)
                entryDetails.setStatus(Utilities.BUNKED);
            else if (e.getCancelled() == 1)
                entryDetails.setStatus(Utilities.CANCELLED);

            entryDetails.setTime(date);
            Log.i(TAG, "Date: " + Utilities.getDate(Utilities.getCurrentTime()));

            //if error in inserting data, unique contraint!
            if (entryDetailsDatabaseHandler.addEntry(entryDetails) != -1) {
                //recalculating all bunks and attendance in case of conflict on replace
                Entry entry = entryDetailsDatabaseHandler.getAllAttTotal(c.getLocalId());
                c.setAttended(entry.getAttended());
                c.setBunked(entry.getBunked());
                c.setCancelled(entry.getCancelled());
                courseDatabaseHandler.updateCourse(c);
            }
        }

        SharedPreferences mPrefs = getSharedPreferences(
                "bunkmonitor.SHARED_PREF", 0);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString("bunkmonitor.LAST_ENTRY_DATE", Utilities.getDate(Utilities.getCurrentTime())).commit();

        setResult(RESULT_OK);
        finish();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.entry, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection

        Intent intent;
        switch (item.getItemId()) {
            case R.id.extras:

                Intent intent1 = new Intent(EntryActivity.this,ExtraCourseList.class);
                startActivityForResult(intent1,EXTRA_LIST);
//                intent1.putExtra("EXTRAS", true);
//                finish();
//                startActivity(intent1);

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == EXTRA_LIST){
            String[] xc = data.getStringArrayExtra("EXTRA_LIST");
            if(xc!=null && xc.length>0){
                EntryDetailsDatabaseHandler entryDetailsDatabaseHandler = new EntryDetailsDatabaseHandler(EntryActivity.this);
                EntryDetails entryDetails = new EntryDetails();
                for(String s:xc){
                    entryDetails.setCourse_lid(s);
                    entryDetails.setStatus(Utilities.EXTRA);
                    entryDetails.setTime(date);
                    entryDetailsDatabaseHandler.addEntry(entryDetails);
                }
            }
            finish();
            startActivity(getIntent());

        }
    }
}


