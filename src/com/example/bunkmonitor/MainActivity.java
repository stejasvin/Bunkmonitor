package com.example.bunkmonitor;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import static java.lang.Math.random;

public class MainActivity extends Activity {

    private static final int ENTRYLIST = 10;
    private static final int ADDNEWCOURSE = 20;
    private ListView list;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.courses);

        Demo();

        TextView tvDef = (TextView)findViewById(R.id.textView2);

        CourseDatabaseHandler courseDatabaseHandler = new CourseDatabaseHandler(this);
        List<Course> cList = courseDatabaseHandler.getAllActiveCourses();
        if(cList.isEmpty())
            tvDef.setVisibility(View.VISIBLE);
        else{
            list = new ListView(this);
            CoursesListAdapter adapter = new CoursesListAdapter(this,R.layout.single_list_item_courses,cList);
            list.setAdapter(adapter);

            LinearLayout ll = (LinearLayout)findViewById(R.id.c_list_layout);
            ll.addView(list);

        }

        Button bAddCourses = (Button)findViewById(R.id.b_courses);
        bAddCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddNewCourse.class);
                startActivity(intent);

            }
        });

        Button bEntry = (Button)findViewById(R.id.b_entry);
        bEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,EntryActivity.class);
                startActivityForResult(intent,ENTRYLIST);

            }
        });


	}

    void Demo(){
        //For Demo, populating entries and courses
        CourseDatabaseHandler courseDatabaseHandler = new CourseDatabaseHandler(this);
        Course course = new Course();
        course.setId("ID:"+(int)(random()*10000));
        course.setCredits("" + (int)(random()*10));
        course.setName("C:" + course.getId());
        course.setProf("Prof ABC");
        course.setSlot("A");
        course.setAttended(0);
        course.setBunked(0);
        course.setCancelled(0);
        course.setActive(0);
        courseDatabaseHandler.addCourse(course);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK)
            if(requestCode == ENTRYLIST){
                Intent i = getIntent();
                finish();
                startActivity(i);

            }else if(requestCode==ADDNEWCOURSE){
                Intent i = getIntent();
                finish();
                startActivity(i);
            }
    }
}
