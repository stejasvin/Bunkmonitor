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
        CustomFontsLoader customFontsLoader = new CustomFontsLoader();

//        TextView tvTitle = (TextView)findViewById(R.id.slots_title);
//        tvTitle.setTypeface(customFontsLoader.getTypeface(this));
//        tvTitle = (TextView)findViewById(R.id.slots_tv_mon);
//        tvTitle.setTypeface(customFontsLoader.getTypeface(this));
//        tvTitle = (TextView)findViewById(R.id.slots_tv_tue);
//        tvTitle.setTypeface(customFontsLoader.getTypeface(this));
//        tvTitle = (TextView)findViewById(R.id.slots_tv_wed);
//        tvTitle.setTypeface(customFontsLoader.getTypeface(this));
//        tvTitle = (TextView)findViewById(R.id.slots_tv_thu);
//        tvTitle.setTypeface(customFontsLoader.getTypeface(this));
//        tvTitle = (TextView)findViewById(R.id.slots_tv_fri);
//        tvTitle.setTypeface(customFontsLoader.getTypeface(this));
//        tvTitle = (TextView)findViewById(R.id.slots_tv_sat);
//        tvTitle.setTypeface(customFontsLoader.getTypeface(this));
//        tvTitle = (TextView)findViewById(R.id.slots_tv_sun);
//        tvTitle.setTypeface(customFontsLoader.getTypeface(this));

        final EditText etMon = (EditText)findViewById(R.id.slots_mon);
        //etMon.setTypeface(customFontsLoader.getTypeface(this));
        final EditText etTue = (EditText)findViewById(R.id.slots_tue);
//        etTue.setTypeface(customFontsLoader.getTypeface(this));
        final EditText etWed = (EditText)findViewById(R.id.slots_wed);
//        etWed.setTypeface(customFontsLoader.getTypeface(this));
        final EditText etThurs = (EditText)findViewById(R.id.slots_thurs);
//        etThurs.setTypeface(customFontsLoader.getTypeface(this));
        final EditText etFri = (EditText)findViewById(R.id.slots_fri);
//        etFri.setTypeface(customFontsLoader.getTypeface(this));
        final EditText etSat = (EditText)findViewById(R.id.slots_sat);
//        etSat.setTypeface(customFontsLoader.getTypeface(this));
        final EditText etSun = (EditText)findViewById(R.id.slots_sun);
//        etSun.setTypeface(customFontsLoader.getTypeface(this));
        //String slots[] = {"A","B","C","D","E","G","P","Q","R","S","Extras"};
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

        final SharedPreferences mPrefs = getSharedPreferences(
                "bunkmonitor.SHARED_PREF", 0);
        etMon.setText(mPrefs.getString("MONDAY",""));
        etMon.setTextColor(getResources().getColor(android.R.color.white));
        etTue.setText(mPrefs.getString("TUESDAY",""));
        etTue.setTextColor(getResources().getColor(android.R.color.white));
        etWed.setText(mPrefs.getString("WEDNESDAY",""));
        etWed.setTextColor(getResources().getColor(android.R.color.white));
        etThurs.setText(mPrefs.getString("THURSDAY",""));
        etThurs.setTextColor(getResources().getColor(android.R.color.white));
        etFri.setText(mPrefs.getString("FRIDAY",""));
        etFri.setTextColor(getResources().getColor(android.R.color.white));
        etSat.setText(mPrefs.getString("SATURDAY",""));
        etSat.setTextColor(getResources().getColor(android.R.color.white));
        etSun.setText(mPrefs.getString("SUNDAY",""));
        etSun.setTextColor(getResources().getColor(android.R.color.white));

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
