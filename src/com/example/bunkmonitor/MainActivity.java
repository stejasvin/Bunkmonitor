package com.example.bunkmonitor;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import static java.lang.Math.random;

public class MainActivity extends Activity {

    private static final int ENTRYLIST = 10;
    private static final int ADDNEWCOURSE = 20;
    private static final int REQUEST_CHECK_ENTRY = 30;
    private ListView list;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.courses);

        SharedPreferences mPrefs = getSharedPreferences(
                "bunkmonitor.SHARED_PREF", 0);
        if(mPrefs.getString("MONDAY",null)==null)
            initializePrefs();
        //Demo();

        TextView tvDef = (TextView)findViewById(R.id.textView2);

        CourseDatabaseHandler courseDatabaseHandler = new CourseDatabaseHandler(this);
        List<Course> cList = courseDatabaseHandler.getAllCourses();
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

        findViewById(R.id.slotOk).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				Intent intent = new Intent(MainActivity.this,Timetable.class);
//                startActivity(intent);
                Intent intent = new Intent(MainActivity.this,CheckEntryCal.class);
                startActivityForResult(intent, REQUEST_CHECK_ENTRY);


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

        Button bBatch = (Button)findViewById(R.id.b_batch);
        bBatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,EditEntryActivity.class);
                startActivityForResult(intent,ENTRYLIST);

            }
        });


	}

    void initializePrefs(){
        SharedPreferences mPrefs = getSharedPreferences(
                "bunkmonitor.SHARED_PREF", 0);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString("MONDAY", "ABCDGP").commit();
        mEditor.putString("TUESDAY", "BCDEAQ").commit();
        mEditor.putString("WEDNESDAY", "CDEFBR").commit();
        mEditor.putString("THURSDAY", "EFGADS").commit();
        mEditor.putString("FRIDAY", "FGABCE").commit();
        mEditor.putString("SATURDAY", "").commit();
        mEditor.putString("SUNDAY", "").commit();
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
        course.setActive(1);
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
            }else if(requestCode==REQUEST_CHECK_ENTRY){
                Intent ret = new Intent(this,EntryActivity.class);
                ret.putExtra("date", Utilities.getDate(data.getStringExtra("date")));
                ret.putExtra("bunkmonitor.MODE", Utilities.READ);
                //this.setResult(RESULT_OK, ret);
                startActivity(ret);
            }
    }
}
