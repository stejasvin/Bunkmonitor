package com.example.bunkmonitor;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;

/**
 * This class is a customized array adapter used to display the List of
 * messages and their details in a list.
 *
 * @author stejasvin
 * @since v1.0
 */

public class CoursesListAdapter extends ArrayAdapter {

    List<Course> cList;

    int textViewResourceId;
    /**
     * Context
     */
    private Context context;

    public CoursesListAdapter(Context context, int textViewResourceId, List<Course> cList) {
        super(context, textViewResourceId, cList);
        this.textViewResourceId = textViewResourceId;
        this.context = context;
        this.cList = cList;

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row = inflater.inflate(textViewResourceId, parent, false); // inflate view from xml file
        }

        TextView tvName = (TextView) row.findViewById(R.id.clist_name);
        TextView tvId = (TextView) row.findViewById(R.id.clist_id);
        TextView tvSlot = (TextView) row.findViewById(R.id.clist_slot);
        TextView tvA = (TextView) row.findViewById(R.id.clist_att);
        TextView tvB = (TextView) row.findViewById(R.id.clist_bunked);
        TextView tvC = (TextView) row.findViewById(R.id.clist_cancelled);
        //LinearLayout layout = (LinearLayout) row.findViewById(R.id.list_viewmes_layout);

        tvName.setText(cList.get(position).getName());
        tvId.setText(cList.get(position).getId());
        tvSlot.setText(cList.get(position).getSlot());
        tvA.setText("Attended: "+cList.get(position).getAttended());
        tvB.setText("Bunked  : "+cList.get(position).getBunked());
        tvC.setText("Canclled: "+cList.get(position).getCancelled());

        return row;
    }

}