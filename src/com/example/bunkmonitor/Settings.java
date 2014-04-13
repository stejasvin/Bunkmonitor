package com.example.bunkmonitor;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * Created by stejasvin on 3/4/14.
 */
public class Settings extends Activity {

    private static final int REQUEST_DATE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        final SharedPreferences mPrefs = getSharedPreferences(
                "bunkmonitor.SHARED_PREF", 0);
        int notifTime = mPrefs.getInt("NOTIF_TIME",1700);
        boolean isWake = mPrefs.getBoolean("WAKE_UP",true);
        boolean isLs = mPrefs.getBoolean("LOCKSCREEN_ENABLE",true);
        boolean isNotif = mPrefs.getBoolean("ENABLE_NOTIF",true);

        String semStartDate = mPrefs.getString("SEM_START_DATE",Utilities.getDate(Utilities.getCurrentTime()));

        final TimePicker timePicker = (TimePicker)findViewById(R.id.timePicker);
        timePicker.setCurrentHour(notifTime/100);
        timePicker.setCurrentMinute(notifTime % 100);

        final CheckBox cbnotif = (CheckBox)findViewById(R.id.set_enable_notif);
        final CheckBox cbls = (CheckBox)findViewById(R.id.set_enable_ls);
        final CheckBox cbwake = (CheckBox)findViewById(R.id.set_wakeup);
        final TextView tvsemstart = (TextView)findViewById(R.id.settings_sem_start);

        cbnotif.setChecked(isNotif);
        cbls.setEnabled(isNotif);
        cbls.setChecked(isLs);

        tvsemstart.setText(semStartDate);

        if(isNotif && isLs)
            cbwake.setEnabled(true);
        else
            cbwake.setEnabled(false);

        cbwake.setChecked(isWake);

        cbnotif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked && cbls.isChecked())
                    cbwake.setEnabled(true);
                else
                    cbwake.setEnabled(false);

                cbls.setEnabled(isChecked);

//                if(!isChecked){
//                    cbwake.setChecked(false);
//                    cbls.setEnabled(false);
//                }
            }
        });

        cbls.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cbwake.setEnabled(isChecked);
//                if(!isChecked)
//                    cbwake.setChecked(false);
            }
        });

        Button button = (Button)findViewById(R.id.set_b);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor mEditor = mPrefs.edit();
                mEditor.putInt("NOTIF_TIME", timePicker.getCurrentHour() * 100 + timePicker.getCurrentMinute()).commit();
                mEditor.putBoolean("LOCKSCREEN_ENABLE", cbls.isChecked()).commit();
                mEditor.putBoolean("WAKE_UP", cbwake.isChecked()).commit();
                mEditor.putBoolean("ENABLE_NOTIF", cbnotif.isChecked()).commit();
                mEditor.putString("SEM_START_DATE",tvsemstart.getText().toString()).commit();

                if(cbnotif.isChecked())
                    Utilities.setRecurringAlarm(Settings.this,0);
                else
                    Utilities.cancelAlarm(Settings.this);

                setResult(RESULT_OK);
                finish();
            }
        });

        Button buttonCal = (Button)findViewById(R.id.settings_cal);
        buttonCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Settings.this,CheckEntryCal.class);
                startActivityForResult(intent,REQUEST_DATE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_DATE){
            final TextView tvsemstart = (TextView)findViewById(R.id.settings_sem_start);
            tvsemstart.setText(data.getStringExtra("date"));
        }
    }
}
