package com.example.bunkmonitor;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

/**
 * Created by stejasvin on 1/1/14.
 */
public class EditSlotsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.edit_slots);

        EditText etMon = (EditText)findViewById(R.id.slots_mon);
        EditText etTue = (EditText)findViewById(R.id.slots_tue);
        EditText etWed = (EditText)findViewById(R.id.slots_wed);
        EditText etThurs = (EditText)findViewById(R.id.slots_thurs);
        EditText etFri = (EditText)findViewById(R.id.slots_fri);
        EditText etSat = (EditText)findViewById(R.id.slots_sat);
        EditText etSun = (EditText)findViewById(R.id.slots_sun);

        SharedPreferences mPrefs = getSharedPreferences(
                "bunkmonitor.SHARED_PREF", 0);
        etMon.setText(mPrefs.getString("MONDAY",""));
        etTue.setText(mPrefs.getString("TUESDAY",""));
        etWed.setText(mPrefs.getString("WEDNESDAY",""));
        etThurs.setText(mPrefs.getString("THURSDAY",""));
        etFri.setText(mPrefs.getString("FRIDAY",""));
        etSat.setText(mPrefs.getString("SATURDAY",""));
        etSun.setText(mPrefs.getString("SUNDAY",""));

        
    }
}
