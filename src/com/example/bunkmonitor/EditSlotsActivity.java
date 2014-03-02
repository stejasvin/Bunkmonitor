package com.example.bunkmonitor;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by stejasvin on 1/1/14.
 */
public class EditSlotsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.edit_slots_backup);

//        final EditText etMon = (EditText)findViewById(R.id.slots_mon);
//        final EditText etTue = (EditText)findViewById(R.id.slots_tue);
//        final EditText etWed = (EditText)findViewById(R.id.slots_wed);
//        final EditText etThurs = (EditText)findViewById(R.id.slots_thurs);
//        final EditText etFri = (EditText)findViewById(R.id.slots_fri);
//        final EditText etSat = (EditText)findViewById(R.id.slots_sat);
//        final EditText etSun = (EditText)findViewById(R.id.slots_sun);
        String slots[] = {"A","B","C","D","E","G","P","Q","R","S","Extras"};
//        TableLayout tlSlots = (TableLayout)findViewById(R.id.slots_tl);
//        TableRow.LayoutParams cp = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1f);
//        TableRow.LayoutParams trp = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        TableRow.LayoutParams ip = new TableRow.LayoutParams(50, 50);
//        for(int i=0;i<slots.length;i++){
//            TableRow row = new TableRow(this);
//            row.setLayoutParams(trp);
//            TextView tv = new TextView(this);
//            tv.setTextColor(getResources().getColor(android.R.color.white));
//            tv.setText(slots[i]);
//            tv.setGravity(Gravity.CENTER);
//            tv.setLayoutParams(cp);
//            row.addView(tv);
//            for(int j=0;j<7;j++){
//
//                final Button cb = new Button(this);
//
////                cb.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        if(cb.getDrawable()==getResources().getDrawable(R.drawable.e_chalkbox))
////                            cb.setImageDrawable(getResources().getDrawable(R.drawable.f_chalkbox));
////                        else
////                            cb.setImageDrawable(getResources().getDrawable(R.drawable.e_chalkbox));
////                    }
////                });
////                cb.setImageDrawable(getResources().getDrawable(R.drawable.e_chalkbox));
//                cb.setLayoutParams(cp);
//                //cb.setGravity(Gravity.CENTER);
//                row.addView(cb);
//            }
//            tlSlots.addView(row);
//        }

        /*final SharedPreferences mPrefs = getSharedPreferences(
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
        });*/
    }
}
