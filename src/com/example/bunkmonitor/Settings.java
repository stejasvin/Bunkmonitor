package com.example.bunkmonitor;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

/**
 * Created by stejasvin on 3/4/14.
 */
public class Settings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        SharedPreferences mPrefs = getSharedPreferences(
                "bunkmonitor.SHARED_PREF", 0);
        int notifTime = mPrefs.getInt("NOTIF_TIME",1700);

        final TimePicker timePicker = (TimePicker)findViewById(R.id.timePicker);
        timePicker.setCurrentHour(notifTime/100);
        timePicker.setCurrentMinute(notifTime % 100);

        Button button = (Button)findViewById(R.id.set_b);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences mPrefs = getSharedPreferences(
                        "bunkmonitor.SHARED_PREF", 0);
                SharedPreferences.Editor mEditor = mPrefs.edit();
                mEditor.putInt("NOTIF_TIME", timePicker.getCurrentHour() * 100 + timePicker.getCurrentMinute()).commit();
                Utilities.setRecurringAlarm(Settings.this,0);
                finish();
            }
        });



    }
}
