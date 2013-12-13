package com.example.bunkmonitor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



/**
 * Created by stejasvin on 12/13/13.
 */
public class EntryActivity extends Activity {

    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.entry_sheet);

        listView = (ListView) findViewById(R.id.listview1);

        EntryDatabaseHandler entryDatabaseHandler = new EntryDatabaseHandler(this);
        // Create adapter to set value for grid view
        EntryListAdapter adapter = new EntryListAdapter(this,entryDatabaseHandler.getAllNewEntry());

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Toast.makeText(getApplicationContext(),
                        ""+position, Toast.LENGTH_SHORT).show();

            }
        });

    }

}
