package com.example.bunkmonitor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

/**
 * Created by stejasvin on 3/10/14.
 */
public class LoadCoursesActivity extends Activity {

    private List<Course> courseList;
    private boolean[] checkList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.load_act);
        final CourseDatabaseHandler courseDatabaseHandler = new CourseDatabaseHandler(this);
        Intent iThis = getIntent();
        String filePath = iThis.getStringExtra("SHARE_PATH");

        ListView listView = (ListView)findViewById(R.id.share_list);
        Button button = (Button)findViewById(R.id.share_done);

        //Read from file
        //File operations to read
        File shareFile = new File(filePath);
        FileInputStream fis;
        ObjectInputStream objectinputstream = null;
        try {
            fis = new FileInputStream(shareFile);
            objectinputstream = new ObjectInputStream(fis);
            List<Course> readCase = (List<Course>) objectinputstream.readObject();
            courseList = readCase;
            //System.out.println(recordList.get(i));

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(courseList == null || courseList.isEmpty()){
                Toast.makeText(LoadCoursesActivity.this,"E01: Error in reading file",Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            if(objectinputstream != null){
                try{
                    objectinputstream.close();
                }catch (IOException e){
                    e.printStackTrace();
                    Toast.makeText(LoadCoursesActivity.this,"E02: Error in reading file",Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
            }
        }

        checkList = new boolean[courseList.size()];
        for(int i=0;i<checkList.length;i++)
            checkList[i] = true;
        listView.setAdapter(new LoadAdapter(this,courseList,R.layout.single_list_item_share,checkList));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(Course c:courseList){
                    c.setUdBunks(0);
                    c.setAttended(0);
                    c.setBunked(0);
                    c.setCancelled(0);

                    courseDatabaseHandler.addCourse(c);
                }
                Toast.makeText(LoadCoursesActivity.this,courseList.size()+" courses added to your list",Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }
}
