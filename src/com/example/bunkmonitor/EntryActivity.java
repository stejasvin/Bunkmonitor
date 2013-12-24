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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;


/**
 * Created by stejasvin on 12/13/13.
 */
public class EntryActivity extends Activity {

    private static final String TAG = "EntryActivity";
    ListView listView;
    List<Course> cList;
    List<Entry> eList;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.entry_sheet);

        listView = (ListView) findViewById(R.id.listview1);

        final CourseDatabaseHandler courseDatabaseHandler = new CourseDatabaseHandler(this);
        cList = courseDatabaseHandler.getAllActiveCourses();
        eList = Entry.getEntryList(cList);

        EntryListAdapter adapter = new EntryListAdapter(this,eList);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //gen
                int a=1;
            }
        });

        Button done = (Button)findViewById(R.id.es_b_done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i=0;i<cList.size();i++){
                    //Adding the difflist entries to orig list and updating the db
                    cList.get(i).setAttended(cList.get(i).getAttended()+eList.get(i).getAttended());
                    cList.get(i).setBunked(cList.get(i).getBunked() + eList.get(i).getBunked());
                    cList.get(i).setCancelled(cList.get(i).getCancelled() + eList.get(i).getCancelled());
                    courseDatabaseHandler.updateCourse(cList.get(i));
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
