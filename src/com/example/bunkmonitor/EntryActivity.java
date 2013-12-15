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
    List<Entry> eList,diffList;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.entry_sheet);

        listView = (ListView) findViewById(R.id.listview1);

        final EntryDatabaseHandler entryDatabaseHandler = new EntryDatabaseHandler(this);

        eList = entryDatabaseHandler.getAllActiveEntry();
        diffList = entryDatabaseHandler.getActiveDiffList();

        EntryListAdapter adapter = new EntryListAdapter(this,diffList);

        listView.setAdapter(adapter);

        Button done = (Button)findViewById(R.id.es_b_done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i=0;i<eList.size();i++){
                    //Adding the difflist entries to orig list and updating the db
                    eList.get(i).setAttended(eList.get(i).getAttended()+diffList.get(i).getAttended());
                    eList.get(i).setBunked(eList.get(i).getBunked()+diffList.get(i).getBunked());
                    eList.get(i).setCancelled(eList.get(i).getCancelled()+diffList.get(i).getCancelled());
                    entryDatabaseHandler.updateEntry(eList.get(i));
                }
                finish();

            }
        });

    }

}
