


package com.example.bunkmonitor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bunkmonitor.Utilities;

import java.util.List;

/**
 * This class is a customized array adapter used to display the List of
 * messages and their details in a list.
 *
 * @author stejasvin
 * @since v1.0
 */

public class EntryListAdapter extends ArrayAdapter {

    List<Entry> eList;
    int textViewResourceId = R.layout.single_list_item_entry;
    static final String topics[] = new String[]{
            "Courses", "Attended", "Bunk", "Cancelled", "Extra"
    };


    /**
     * Context
     */
    private Context context;

    public EntryListAdapter(Context context, List<Entry> eList) {
        super(context, R.layout.single_list_item_entry, eList);
        this.context = context;
        this.eList = eList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row = inflater.inflate(textViewResourceId, parent, false); // inflate view from xml file
        }

        final Entry entry = eList.get(position);

        TextView tv = (TextView) row.findViewById(R.id.elist_text);
        TextView tvExtra = (TextView) row.findViewById(R.id.elist_extra_text);
        final ImageView imva = (ImageView) row.findViewById(R.id.elist_img_a);
        final ImageView imvb = (ImageView) row.findViewById(R.id.elist_img_b);
        final ImageView imvc = (ImageView) row.findViewById(R.id.elist_img_c);

        imva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entry.setStatus(Utilities.ATTENDED);

                imva.setImageResource(R.drawable.pencil_tick);
                imvb.setImageResource(R.drawable.pencil_line);
                imvc.setImageResource(R.drawable.pencil_line);


            }
        });

        imvb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entry.setStatus(Utilities.BUNKED);

                imva.setImageResource(R.drawable.pencil_line);
                imvb.setImageResource(R.drawable.pencil_tick);
                imvc.setImageResource(R.drawable.pencil_line);


            }
        });

        imvc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entry.setStatus(Utilities.CANCELLED);

                imva.setImageResource(R.drawable.pencil_line);
                imvb.setImageResource(R.drawable.pencil_line);
                imvc.setImageResource(R.drawable.pencil_tick);


            }
        });

        Button b = (Button) row.findViewById(R.id.elist_extra);

        tv.setText(entry.getCourse_id());
        tvExtra.setVisibility(View.GONE);
        imva.setImageResource(R.drawable.pencil_line);
        imvb.setImageResource(R.drawable.pencil_line);
        imvc.setImageResource(R.drawable.pencil_line);

        switch(entry.getStatus()){
            case Utilities.ATTENDED:
                    imva.setImageResource(R.drawable.pencil_tick);
                    break;

            case Utilities.BUNKED:
                    imvb.setImageResource(R.drawable.pencil_tick);
                    break;

            case Utilities.CANCELLED:
                    imvc.setImageResource(R.drawable.pencil_tick);
                    break;

            case Utilities.EXTRA:
                    tvExtra.setVisibility(View.VISIBLE);
                    b.setVisibility(View.GONE);
                    break;

        }

    return row;
}


}