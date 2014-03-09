package com.example.bunkmonitor;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

/**
 * Created by stejasvin on 3/5/14.
 */
public class LockscreenActivity extends Activity {

    private static final String TAG = "LockscreenActivity";

//    SensorManager mSensorManager;
//    Sensor mSensor;

    private BroadcastReceiver callDetectReceiver;

    @Override
    protected void onStart() {

        super.onStart();

        callDetectReceiver = new BroadcastReceiver() {

            public void onReceive(Context context, Intent intent) {

                Log.i(TAG, "calldetectReceiver triggered");
                String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

                if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                    //Phone is ringing
                    SharedPreferences mPrefs = getSharedPreferences(
                            "bunkmonitor.SHARED_PREF", 0);
                    SharedPreferences.Editor mEditor = mPrefs.edit();
                    mEditor.putBoolean("SNOOZE", true).commit();
                    finish();
                    return;

                } else if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                    //Call received

                } else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                    //Call Dropped or rejected

                }

            }
        };
        registerReceiver(callDetectReceiver, new IntentFilter("android.intent.action.PHONE_STATE"));

    }

    @Override
    protected void onStop() {
        super.onStop();

        unregisterReceiver(callDetectReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lockscreen);
        SharedPreferences mPrefs = getSharedPreferences(
                "bunkmonitor.SHARED_PREF", 0);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mPrefs = getSharedPreferences(
                "bunkmonitor.SHARED_PREF", 0);

        if (mPrefs.getBoolean("SNOOZE", false)) {
            finish();
            return;
        }

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        if (mPrefs.getBoolean("WAKE_UP", false))
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        Button snooze = (Button) findViewById(R.id.ls_snooze);
        Button attall = (Button) findViewById(R.id.ls_attall);
        Button bunk = (Button) findViewById(R.id.ls_bunk);
        Button holiday = (Button) findViewById(R.id.ls_canc);
        Button goApp = (Button) findViewById(R.id.ls_go_app);

        CourseDatabaseHandler courseDatabaseHandler = new CourseDatabaseHandler(this);

        snooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSnooze();
            }
        });

        attall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                autoEntry(0);

            }
        });

        bunk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                autoEntry(1);

            }
        });

        holiday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                autoEntry(2);

            }
        });


        goApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LockscreenActivity.this, EntryActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        doSnooze();
    }

    private void autoEntry(int mode) {

        CourseDatabaseHandler courseDatabaseHandler = new CourseDatabaseHandler(this);
        EntryDetailsDatabaseHandler entryDetailsDatabaseHandler = new EntryDetailsDatabaseHandler(this);
        EntryDetails entryDetails = new EntryDetails();

        String date = Utilities.getDate(Utilities.getCurrentTime());
        String[] s = date.split("/");
        Utilities.processDateArray(s);
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.decode(s[2]), Integer.decode(s[1]) - 1, Integer.decode(s[0]));
        Utilities.toggleActiveCourses(this, cal.get(Calendar.DAY_OF_WEEK));

        List<Course> cList = courseDatabaseHandler.getAllActiveCourses();
        List<Entry> eList = Entry.getEntryList(cList);

        for (Entry e : eList) {

            if (mode == 0) {
                e.setAttended(1);
                e.setBunked(0);
                e.setCancelled(0);
            } else if (mode == 1) {
                e.setAttended(0);
                e.setBunked(1);
                e.setCancelled(0);
            } else if (mode == 2){
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

            //recalculating all bunks and attendance in case of conflict on replace
            Entry entry = entryDetailsDatabaseHandler.getAllAttTotal(c.getLocalId());
            c.setAttended(entry.getAttended());
            c.setBunked(entry.getBunked());
            c.setCancelled(entry.getCancelled());
            courseDatabaseHandler.updateCourse(c);

        }

        SharedPreferences mPrefs = getSharedPreferences(
                "bunkmonitor.SHARED_PREF", 0);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString("bunkmonitor.LAST_ENTRY_DATE", Utilities.getDate(Utilities.getCurrentTime())).commit();

        //setResult(RESULT_OK);
        finish();
    }

    public void doSnooze() {
        //setting flag for snooze
        SharedPreferences mPrefs = getSharedPreferences(
                "bunkmonitor.SHARED_PREF", 0);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putBoolean("SNOOZE", true).commit();

        //Cancelling alarm
        Utilities.cancelAlarm(getApplicationContext());
        //set alarm with snooze of 1 hr
        Utilities.setRecurringAlarm(getApplicationContext(), 1);
        finish();

        Toast.makeText(getApplicationContext(),"Snoozed for an hour",Toast.LENGTH_LONG).show();
    }

//    protected void onResume() {
//
//        super.onResume();
//        SharedPreferences mPrefs = getSharedPreferences(
//                "bunkmonitor.SHARED_PREF", 0);
//        if(mPrefs.getBoolean("FLAG_LS_ACT",true))
//            mSensorManager.registerListener(this, mSensor,SensorManager.SENSOR_DELAY_FASTEST);
//    }
//
//    protected void onDestroy() {
//        super.onPause();
//        mSensorManager.unregisterListener(this);
//    }
//
//    public void onAccuracyChanged(Sensor sensor, int accuracy) {
//    }
//
//    public void onSensorChanged(SensorEvent event) {
//        if (event.values[0] == 0) {
//            //near
//            int a;
//            a=1;
//        } else {
//            //far
//            Intent i = getIntent();
//            i.putExtra("PROX",1);
////            finish();
////            startActivity(i);
//        }
//        SharedPreferences mPrefs = getSharedPreferences(
//                "bunkmonitor.SHARED_PREF", 0);
//        SharedPreferences.Editor mEditor = mPrefs.edit();
//        //mEditor.putBoolean("FLAG_LS_ACT",false).commit();
//        //mSensorManager.unregisterListener(this);
//    }
}
