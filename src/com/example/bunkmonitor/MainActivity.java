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

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.courses);

        TextView tvDef = (TextView)findViewById(R.id.textView2);

        CourseDatabaseHandler courseDatabaseHandler = new CourseDatabaseHandler(this);
        List<Course> cList = courseDatabaseHandler.getAllCourses();
        if(cList.isEmpty())
            tvDef.setVisibility(View.VISIBLE);
        else{
            ListView list = new ListView(this);
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
                finish();
            }
        });

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
