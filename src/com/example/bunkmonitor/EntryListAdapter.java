


package com.example.bunkmonitor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * This class is a customized array adapter used to display the List of
 * messages and their details in a list.
 *
 * @author stejasvin
 * @since v1.0
 */

public class EntryListAdapter extends ArrayAdapter {

    List<Entry> diffList;
    int textViewResourceId = R.layout.single_list_item_entry;
    static final String topics[] = new String[]{
            "Courses", "Attended", "Bunk", "Cancelled", "Extra"
    };


    /**
     * Context
     */
    private Context context;
    private ImageView imva, imvb, imvc;
    private Entry entry;

    public EntryListAdapter(Context context, List<Entry> diffList) {
        super(context, R.layout.single_list_item_entry, diffList);
        this.context = context;
        this.diffList = diffList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row = inflater.inflate(textViewResourceId, parent, false); // inflate view from xml file
        }

        entry = diffList.get(position);

        TextView tv = (TextView) row.findViewById(R.id.elist_text);
        imva = (ImageView) row.findViewById(R.id.elist_img_a);
        imvb = (ImageView) row.findViewById(R.id.elist_img_b);
        imvc = (ImageView) row.findViewById(R.id.elist_img_c);

        imva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleTicks(Utilities.ATTENDED);
            }
        });

        imvb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleTicks(Utilities.BUNKED);

            }
        });

        imvc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleTicks(Utilities.CANCELLED);
            }
        });

        //Button b = (Button) row.findViewById(R.id.elist_extra);

        tv.setText(entry.getCourse_id());
        //tvExtra.setVisibility(View.GONE);
        imva.setImageResource(R.drawable.pencil_line);
        imvb.setImageResource(R.drawable.pencil_line);
        imvc.setImageResource(R.drawable.pencil_line);

        if (entry.getAttended() == 1)
            imva.setImageResource(R.drawable.pencil_tick);
        else if (entry.getBunked() == 1)
            imvb.setImageResource(R.drawable.pencil_tick);
        else if (entry.getCancelled() == 1)
            imvc.setImageResource(R.drawable.pencil_tick);

        return row;
    }

    void toggleTicks(int c) {
        entry.setAttended(0);
        entry.setBunked(0);
        entry.setCancelled(0);
        imva.setImageResource(R.drawable.pencil_line);
        imvb.setImageResource(R.drawable.pencil_line);
        imvc.setImageResource(R.drawable.pencil_line);

        switch (c) {
            case Utilities.ATTENDED:
                imva.setImageResource(R.drawable.pencil_tick);
                entry.setAttended(1);
                break;
            case Utilities.BUNKED:
                imvb.setImageResource(R.drawable.pencil_tick);
                entry.setBunked(1);
                break;
            case Utilities.CANCELLED:
                imvc.setImageResource(R.drawable.pencil_tick);
                entry.setCancelled(1);
                break;
        }


    }


}