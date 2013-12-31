package com.example.bunkmonitor;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.Calendar;
import java.util.List;


/**
 * Created by stejasvin on 12/13/13.
 */
public class EntryActivity extends Activity {

    private static final String TAG = "EntryActivity";
    int MODE = Utilities.WRITE;
    ListView listView;
    List<Course> cList;
    List<Entry> eList;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.entry_sheet);

        Intent iThis = getIntent();
        MODE = iThis.getIntExtra("bunkmonitor.MODE",Utilities.WRITE);

        listView = (ListView) findViewById(R.id.listview1);

        final CourseDatabaseHandler courseDatabaseHandler = new CourseDatabaseHandler(this);
        final EntryDetailsDatabaseHandler entryDetailsDatabaseHandler = new EntryDetailsDatabaseHandler(this);


        Button done = (Button)findViewById(R.id.es_b_done);

        if(MODE == Utilities.WRITE){
            cList = courseDatabaseHandler.getAllActiveCourses();
            eList = Entry.getEntryList(cList);
            EntryListAdapter adapter = new EntryListAdapter(this,eList,MODE);
            listView.setAdapter(adapter);
        }else if(MODE == Utilities.READ){
            done.setVisibility(View.GONE);

            String date = iThis.getStringExtra("date");
            if(date==null)
                return;
            eList = entryDetailsDatabaseHandler.getEntryListByDate(date);
            EntryListAdapter adapter = new EntryListAdapter(this,eList,MODE);
            listView.setAdapter(adapter);
        }


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EntryDetails entryDetails = new EntryDetails();

                for(int i=0;i<cList.size();i++){
                    //Adding the difflist entries to orig list and updating the db
                    Course c = cList.get(i);
                    Entry e = eList.get(i);
                    c.setAttended(c.getAttended() + e.getAttended());
                    c.setBunked(c.getBunked() + e.getBunked());
                    c.setCancelled(c.getCancelled() + e.getCancelled());
                    courseDatabaseHandler.updateCourse(c);

                    entryDetails.setCourse_id(c.getId());
                    entryDetails.setEntered(1);

                    if(e.getAttended()==1)
                        entryDetails.setStatus(Utilities.ATTENDED);
                    else if(e.getBunked()==1)
                        entryDetails.setStatus(Utilities.BUNKED);
                    else if(e.getCancelled()==1)
                        entryDetails.setStatus(Utilities.CANCELLED);

                    entryDetails.setTime(Utilities.getDate(Utilities.getCurrentTime()));
                    Log.i(TAG,"Date: "+Utilities.getDate(Utilities.getCurrentTime()));
                    entryDetailsDatabaseHandler.addEntry(entryDetails);
                    
                    
                }

                SharedPreferences mPrefs = getSharedPreferences(
                        "bunkmonitor.SHARED_PREF", 0);
                SharedPreferences.Editor mEditor = mPrefs.edit();
                mEditor.putString("bunkmonitor.LAST_ENTRY_DATE", Utilities.getDate(Utilities.getCurrentTime())).commit();

                setResult(RESULT_OK);
                finish();

            }
        });

        //Setting alarm for next day
        setRecurringAlarm(this);

    }

    private void setRecurringAlarm(Context context) {
        Log.i(TAG, "setting Alarm");
        Calendar updateTime = Calendar.getInstance();
//        updateTime.setTimeZone(TimeZone.getTimeZone("GMT"));
//        updateTime.set(Calendar.HOUR_OF_DAY, 11);
//        updateTime.set(Calendar.MINUTE, 45);
        Intent downloader = new Intent(context, AlarmReceiver.class);
        PendingIntent recurringDownload = PendingIntent.getBroadcast(context,
                0, downloader, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarms = (AlarmManager) this.getSystemService(
                Context.ALARM_SERVICE);
        alarms.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                updateTime.getTimeInMillis(),
                10000, recurringDownload);  //Need to use INTERVAL_DAY instead of 10000
    }

}

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                //gen
//                int a=1;
//            }
//        });
