package com.example.bunkmonitor;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

/**
 * Created by stejasvin on 3/9/14.
 */
public class BunkList extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CourseDatabaseHandler courseDatabaseHandler = new CourseDatabaseHandler(this);
        List<Course> courseList = courseDatabaseHandler.getAllCourses();
        if(courseList.size()>0){
            setListAdapter(new BunkListAdapter(BunkList.this,courseList));
            getListView().setBackgroundColor(getResources().getColor(R.color.sticky_yellow));
        }
        else{
            finish();
            Toast.makeText(this,"No courses added",Toast.LENGTH_SHORT).show();
        }
        //LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(this, LinearLayout.LayoutParams.)
        //getListView().setLayoutParams();

    }
}
