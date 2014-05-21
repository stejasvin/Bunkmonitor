package com.example.bunkmonitor;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stejasvin on 3/9/14.
 */
public class ExtraCourseList extends ListActivity {

    List<String> extraCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CourseDatabaseHandler courseDatabaseHandler = new CourseDatabaseHandler(this);
        List<Course> courseList = courseDatabaseHandler.getAllCourses();
        if(courseList.size()>0){
            extraCourses = new ArrayList<String>();
            setListAdapter(new ExtraCourseListAdapter(ExtraCourseList.this,courseList,extraCourses));
            getListView().setBackgroundColor(getResources().getColor(R.color.sticky_yellow));
        }
        else{
            finish();
            Toast.makeText(this,"No courses added",Toast.LENGTH_SHORT).show();
        }

        //LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(this, LinearLayout.LayoutParams.)
        //getListView().setLayoutParams();

    }

    @Override
    public void onBackPressed() {
        if(extraCourses!=null){
            String[] xc = new String[extraCourses.size()];
            int k=0;
            for(String s:extraCourses)
                xc[k++]=s;
            Intent i = new Intent();
            i.putExtra("EXTRA_LIST", xc);
            setResult(EntryActivity.EXTRA_LIST,i);
            finish();
        }
        super.onBackPressed();
    }
}
