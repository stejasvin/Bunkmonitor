package com.example.bunkmonitor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


/**
 * Created by stejasvin on 12/13/13.
 */
public class EntryActivity extends Activity {

    ListView listView;
    List<Course> cList;
    List<Entry> eList;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.entry_sheet);

        listView = (ListView) findViewById(R.id.listview1);

        final CourseDatabaseHandler courseDatabaseHandler = new CourseDatabaseHandler(this);
        cList = courseDatabaseHandler.getAllActiveCourses();
        eList = Entry.getEntryList(cList);

        EntryListAdapter adapter = new EntryListAdapter(this,eList);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //gen
                int a=1;
            }
        });

        Button done = (Button)findViewById(R.id.es_b_done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i=0;i<cList.size();i++){
                    //Adding the difflist entries to orig list and updating the db
                    cList.get(i).setAttended(cList.get(i).getAttended()+eList.get(i).getAttended());
                    cList.get(i).setBunked(cList.get(i).getBunked() + eList.get(i).getBunked());
                    cList.get(i).setCancelled(cList.get(i).getCancelled() + eList.get(i).getCancelled());
                    courseDatabaseHandler.updateCourse(cList.get(i));
                }
                setResult(RESULT_OK);
                finish();

            }
        });

    }

}
