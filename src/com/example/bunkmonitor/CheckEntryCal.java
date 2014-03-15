/*
 * Copyright (C) 2011 Chris Gao <chris@exina.net>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.bunkmonitor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exina.android.calendar.CalendarView;
import com.exina.android.calendar.Cell;

import java.util.Calendar;

public class CheckEntryCal extends Activity implements CalendarView.OnCellTouchListener {
    public static final String MIME_TYPE = "vnd.android.cursor.dir/vnd.exina.android.calendar.date";
    CalendarView mView = null;
    TextView mHit;
    Handler mHandler = new Handler();


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_entry_cal);

        DisplayMetrics displayMetrics = this.getResources()
                .getDisplayMetrics();
        int screenWidthInPix = displayMetrics.widthPixels;
        int screenheightInPix = displayMetrics.heightPixels;

        mView = (CalendarView) findViewById(R.id.calendar);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((int)(screenWidthInPix*0.7),(int)(screenheightInPix*0.8));
        lp.gravity = Gravity.CENTER;
        mView.setLayoutParams(lp);
        mView.setOnCellTouchListener(this);

        final TextView month = (TextView)findViewById(R.id.cal_text);
        month.setText(DateUtils.getMonthString(mView.getMonth(), DateUtils.LENGTH_LONG)+" "+mView.getYear());

        Button prev = (Button)findViewById(R.id.b_prev);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView.previousMonth();
                month.setText(DateUtils.getMonthString(mView.getMonth(), DateUtils.LENGTH_LONG)+" "+mView.getYear());
            }
        });
        Button next = (Button)findViewById(R.id.b_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView.nextMonth();
                month.setText(DateUtils.getMonthString(mView.getMonth(), DateUtils.LENGTH_LONG)+" "+mView.getYear());

            }
        });



    }


    public void onTouch(final Cell cell) {

        int year = mView.getYear();
        int month = mView.getMonth();
        int day = cell.getDayOfMonth();

        if (cell instanceof CalendarView.GrayCell) {

        } else {
            Intent ret = new Intent();
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH,day);
            cal.set(Calendar.MONTH,month);
            cal.set(Calendar.YEAR,year);
            ret.putExtra("date", Utilities.getDate(cal.getTime().toString()));
//            ret.putExtra("month", month);
//            ret.putExtra("day", day);
//            ret.putExtra("bunkmonitor.MODE", Utilities.READ);
            this.setResult(RESULT_OK, ret);
            //startActivity(ret);
            finish();

        }

        //final int day = cell.getDayOfMonth();
//		if(mView.firstDay(day))
//			mView.previousMonth();
//		else if(mView.lastDay(day))
//			mView.nextMonth();
//		else
//			return;

//        mHandler.post(new Runnable() {
//            public void run() {
////                DateUtils.getMonthString(mView.getMonth(), DateUtils.LENGTH_LONG) + " "+mView.getYear()
//                Toast.makeText(CheckEntryCal.this, "" + day, Toast.LENGTH_SHORT).show();
//            }
//        });
    }


}