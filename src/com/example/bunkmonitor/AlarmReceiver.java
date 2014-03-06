package com.example.bunkmonitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Tejas on 8/25/13.
 */
public class AlarmReceiver extends BroadcastReceiver {
    private static final String DEBUG_TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(DEBUG_TAG, "Recurring alarm; requesting download service.");

        if(!intent.getBooleanExtra("ALARM",false))
            return;

        //reseting flag for snooze set in Lockscreen Activity
        SharedPreferences mPrefs = context.getSharedPreferences(
                "bunkmonitor.SHARED_PREF", 0);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putBoolean("SNOOZE", false).commit();
        //mEditor.putBoolean("FLAG_LS_ACT",true).commit();


        Intent downloader = new Intent(context, DailyNotifService.class);
        context.startService(downloader);
    }


}
