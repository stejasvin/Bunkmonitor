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
    List<Entry> eList;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.entry_sheet);

        listView = (ListView) findViewById(R.id.listview1);

        final EntryDatabaseHandler entryDatabaseHandler = new EntryDatabaseHandler(this);

        eList = entryDatabaseHandler.getAllNewEntry();

        EntryListAdapter adapter = new EntryListAdapter(this,eList);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Toast.makeText(getApplicationContext(),
                        ""+position, Toast.LENGTH_SHORT).show();

            }
        });

        Button done = (Button)findViewById(R.id.es_b_done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i=0;i<eList.size();i++){
                    eList.get(i).setEntered(1);
                    entryDatabaseHandler.updateEntry(eList.get(i));
                }
                finish();

            }
        });

    }

}
