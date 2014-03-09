package com.example.bunkmonitor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Calendar;

/**
 * Created by stejasvin on 1/1/14.
 */
public class EditEntryActivity extends Activity {

    public static final int REQUEST_SINGLE = 10;
    private static final int REQUEST_BATCH_FROM = 20;
    public static final int REQUEST_BATCH_TO = 30;

    int MODE = Utilities.SINGLE; // or Utilitiles.BATCH
    DatePicker dp1,dp2,dp3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_edit_entry);

        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.edit_rg1);
        final RadioButton rbSingle = (RadioButton)findViewById(R.id.edit_rb1);
        final RadioButton rbBatch = (RadioButton)findViewById(R.id.edit_rb2);

        RadioGroup radioGroup2 = (RadioGroup)findViewById(R.id.edit_rg2);
        final RadioButton rbBunked = (RadioButton)findViewById(R.id.edit_rb3);
        final RadioButton rbAttended = (RadioButton)findViewById(R.id.edit_rb4);

        dp1 = (DatePicker)findViewById(R.id.edit_date1);
        dp2 = (DatePicker)findViewById(R.id.edit_date2);
        dp3 = (DatePicker)findViewById(R.id.edit_date3);

        final LinearLayout llSingle = (LinearLayout)findViewById(R.id.edit_ll_single);
        final LinearLayout llBatch = (LinearLayout)findViewById(R.id.edit_ll_batch);

        rbSingle.setChecked(true);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(rbSingle.isChecked()){
                    llSingle.setVisibility(View.VISIBLE);
                    llBatch.setVisibility(View.GONE);
                    MODE=Utilities.SINGLE;
                }else if(rbBatch.isChecked()){
                    llSingle.setVisibility(View.GONE);
                    llBatch.setVisibility(View.VISIBLE);
                    MODE=Utilities.BATCH;
                }
            }
        });

//        Button bCal1 = (Button)findViewById(R.id.edit_b1);
//        Button bCal2 = (Button)findViewById(R.id.edit_b2);
//        Button bCal3 = (Button)findViewById(R.id.edit_b3);
        final Button bDone = (Button)findViewById(R.id.edit_done);

//        bCal1.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(EditEntryActivity.this,CheckEntryCal.class);
//                startActivityForResult(intent,REQUEST_SINGLE);
//            }
//        });
//
//        bCal2.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(EditEntryActivity.this,CheckEntryCal.class);
//                startActivityForResult(intent,REQUEST_BATCH_FROM);
//            }
//        });
//
//        bCal3.setOnClickListener(new View.OnClickListener() {
//
//
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(EditEntryActivity.this,CheckEntryCal.class);
//                startActivityForResult(intent,REQUEST_BATCH_TO);
//            }
//        });

        bDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bDone.setEnabled(false);
                if(MODE == Utilities.SINGLE){

                    Intent intent = new Intent(EditEntryActivity.this,EntryActivity.class);
                    intent.putExtra("bunkmonitor.MODE",Utilities.WRITE);

                    intent.putExtra("COURSE_LOCAL_ID",getIntent().getStringExtra("COURSE_LOCAL_ID"));

                    Calendar cal = Calendar.getInstance();
                    cal.set(dp1.getYear(),dp1.getMonth(),dp1.getDayOfMonth());
                    intent.putExtra("date",Utilities.getDate(cal.getTime().toString()));
                    startActivity(intent);
                    setResult(RESULT_OK);
                    finish();

                }else if(MODE == Utilities.BATCH){

                    Calendar cal1=Calendar.getInstance(),cal2=Calendar.getInstance();
                    cal1.set(Calendar.DAY_OF_MONTH,dp2.getDayOfMonth());
                    cal1.set(Calendar.MONTH,dp2.getMonth());
                    cal1.set(Calendar.YEAR,dp2.getYear());
                    cal2.set(Calendar.DAY_OF_MONTH,dp3.getDayOfMonth());
                    cal2.set(Calendar.MONTH,dp3.getMonth());
                    cal2.set(Calendar.YEAR,dp3.getYear());
                    EntryDetailsDatabaseHandler entryDetailsDatabaseHandler = new EntryDetailsDatabaseHandler(EditEntryActivity.this);
                    if(rbBunked.isChecked())
                        entryDetailsDatabaseHandler.batchEdit(EditEntryActivity.this,Utilities.getDate(cal1.getTime().toString())
                                    ,Utilities.getDate(cal2.getTime().toString()),Utilities.BUNKED);
                    if(rbAttended.isChecked())
                        entryDetailsDatabaseHandler.batchEdit(EditEntryActivity.this,Utilities.getDate(cal1.getTime().toString())
                                ,Utilities.getDate(cal2.getTime().toString()),Utilities.ATTENDED);
                    setResult(RESULT_OK);
                    finish();

                }
                bDone.setEnabled(true);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_SINGLE){
                String date = data.getStringExtra("date");

                String[] s = date.split("/");
                Utilities.processDateArray(s);
                if(s!=null)
                    dp1.updateDate(Integer.decode(s[2]),Integer.decode(s[1])-1,Integer.decode(s[0]));

            }else if(requestCode == REQUEST_BATCH_FROM){
                String date = data.getStringExtra("date");
                //DatePicker dp = (DatePicker)findViewById(R.id.edit_date2);
                String[] s = date.split("/");
                Utilities.processDateArray(s);
                if(s!=null)
                    dp2.updateDate(Integer.decode(s[2]),Integer.decode(s[1])-1,Integer.decode(s[0]));

            }else if(requestCode == REQUEST_BATCH_TO){
                String date = data.getStringExtra("date");
                //DatePicker dp = (DatePicker)findViewById(R.id.edit_date3);
                String[] s = date.split("/");
                Utilities.processDateArray(s);
//                if(s[0].charAt(0)=='0')
//                    s[0]=s[0].substring(1);
//                if(s[1].charAt(0)=='0')
//                    s[1]=s[1].substring(1);
//                if(s[2].charAt(0)=='0')
//                    s[2]=s[2].substring(1);

                if(s!=null)
                    dp3.updateDate(Integer.decode(s[2]),Integer.decode(s[1])-1,Integer.decode(s[0]));

            }
        }
    }
}

