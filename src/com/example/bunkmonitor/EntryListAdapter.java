


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

        final Entry entry = diffList.get(position);

        TextView tv = (TextView) row.findViewById(R.id.elist_text);
        final ImageView imva = (ImageView) row.findViewById(R.id.elist_img_a);
        final ImageView imvb = (ImageView) row.findViewById(R.id.elist_img_b);
        final ImageView imvc = (ImageView) row.findViewById(R.id.elist_img_c);

        imva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imva.setImageResource(R.drawable.pencil_tick);
                imvb.setImageResource(R.drawable.pencil_line);
                imvc.setImageResource(R.drawable.pencil_line);

                entry.setAttended(1);
                entry.setBunked(0);
                entry.setCancelled(0);

            }
        });

        imvb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imva.setImageResource(R.drawable.pencil_line);
                imvb.setImageResource(R.drawable.pencil_tick);
                imvc.setImageResource(R.drawable.pencil_line);

                entry.setAttended(0);
                entry.setBunked(1);
                entry.setCancelled(0);


            }
        });

        imvc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imva.setImageResource(R.drawable.pencil_line);
                imvb.setImageResource(R.drawable.pencil_line);
                imvc.setImageResource(R.drawable.pencil_tick);

                entry.setAttended(0);
                entry.setBunked(0);
                entry.setCancelled(1);

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

    /*void toggleTicks(int c) {
        entry.setAttended(0);
        entry.setBunked(0);
        entry.setCancelled(0);
        switch (c) {
            case Utilities.ATTENDED:
                entry.setAttended(1);
                break;
            case Utilities.BUNKED:
                entry.setBunked(1);
                break;
            case Utilities.CANCELLED:
                entry.setCancelled(1);
                break;
        }




    }
*/

}