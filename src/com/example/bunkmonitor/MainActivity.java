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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.courses);

        Demo();

        TextView tvDef = (TextView)findViewById(R.id.textView2);

        CourseDatabaseHandler courseDatabaseHandler = new CourseDatabaseHandler(this);
        EntryDatabaseHandler entryDatabaseHandler = new EntryDatabaseHandler(this);
        List<Course> cList = courseDatabaseHandler.getAllActiveCourses();
        List<Entry> eList = entryDatabaseHandler.getAllActiveEntry();
        if(cList.isEmpty())
            tvDef.setVisibility(View.VISIBLE);
        else{
            ListView list = new ListView(this);
            CoursesListAdapter adapter = new CoursesListAdapter(this,R.layout.single_list_item_courses,cList,eList);
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
                startActivity(intent);

            }
        });


	}

    void Demo(){
        //For Demo, populating entries and courses
        CourseDatabaseHandler courseDatabaseHandler = new CourseDatabaseHandler(this);
        Course course = new Course();
        course.setId("ID"+random()%10000);
        course.setCredits("" + random() % 10);
        course.setName("Course" + course.getId());
        course.setProf("Prof ABC");
        course.setSlot("A");
        courseDatabaseHandler.addCourse(course);

        EntryDatabaseHandler entryDatabaseHandler = new EntryDatabaseHandler(this);
        Entry entry = new Entry();
        entry.setCourse_id(course.getId());
        entry.setAttended(55);
        entry.setBunked(2);
        entry.setCancelled(22);
        entry.setActive(1);
        entryDatabaseHandler.addEntry(entry);

    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
