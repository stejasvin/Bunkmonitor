package com.example.bunkmonitor;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by stejasvin on 1/1/14.
 */
public class EditSlotsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.edit_slots);

        final EditText etMon = (EditText)findViewById(R.id.slots_mon);
        final EditText etTue = (EditText)findViewById(R.id.slots_tue);
        final EditText etWed = (EditText)findViewById(R.id.slots_wed);
        final EditText etThurs = (EditText)findViewById(R.id.slots_thurs);
        final EditText etFri = (EditText)findViewById(R.id.slots_fri);
        final EditText etSat = (EditText)findViewById(R.id.slots_sat);
        final EditText etSun = (EditText)findViewById(R.id.slots_sun);

        final SharedPreferences mPrefs = getSharedPreferences(
                "bunkmonitor.SHARED_PREF", 0);
        etMon.setText(mPrefs.getString("MONDAY",""));
        etTue.setText(mPrefs.getString("TUESDAY",""));
        etWed.setText(mPrefs.getString("WEDNESDAY",""));
        etThurs.setText(mPrefs.getString("THURSDAY",""));
        etFri.setText(mPrefs.getString("FRIDAY",""));
        etSat.setText(mPrefs.getString("SATURDAY",""));
        etSun.setText(mPrefs.getString("SUNDAY",""));

        Button done = (Button)findViewById(R.id.slots_done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor mEditor = mPrefs.edit();
                mEditor.putString("MONDAY", etMon.getText().toString()).commit();
                mEditor.putString("TUESDAY", etTue.getText().toString()).commit();
                mEditor.putString("WEDNESDAY", etWed.getText().toString()).commit();
                mEditor.putString("THURSDAY", etThurs.getText().toString()).commit();
                mEditor.putString("FRIDAY", etFri.getText().toString()).commit();
                mEditor.putString("SATURDAY", etSat.getText().toString()).commit();
                mEditor.putString("SUNDAY", etSun.getText().toString()).commit();
                finish();
            }
        });
    }
}
