package com.example.bunkmonitor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
        //TextView tvId = (TextView) row.findViewById(R.id.clist_id);
        TextView tvSlot = (TextView) row.findViewById(R.id.clist_slot);
        ImageView imgHm = (ImageView) row.findViewById(R.id.clist_hm);
        imgHm.setVisibility(View.VISIBLE);
//        TextView tvA = (TextView) row.findViewById(R.id.clist_att);
        TextView tvB = (TextView) row.findViewById(R.id.clist_bunked);
//        TextView tvC = (TextView) row.findViewById(R.id.clist_cancelled);
//        TextView tvPercent = (TextView) row.findViewById(R.id.clist_percent);
//        ProgressBar pb = (ProgressBar)row.findViewById(R.id.clist_pb);
        //LinearLayout layout = (LinearLayout) row.findViewById(R.id.list_viewmes_layout);
        Course c = cList.get(position);
        tvName.setText(c.getName());
//        tvId.setText("ID: "+c.getId());
        tvSlot.setText("Slot: " + c.getSlot());
//        tvA.setText("Attended: "+c.getAttended());
//        tvB.setText("Bunked  : "+c.getBunked());
//        tvC.setText("Cancelled: "+c.getCancelled());
        int creditno = 0;
        String tvbString;
        try {
            creditno = Integer.decode(c.getCredits());
            if (creditno < 0) {
                imgHm.setVisibility(View.GONE);
                tvB.setText("#bunks - "+c.getBunked());
                return row;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            imgHm.setVisibility(View.GONE);
            tvB.setText("#bunks - "+c.getBunked());
            return row;

        }

        if(2*creditno > c.getBunked())
            tvbString = "bunks left - "+(2*creditno - c.getBunked());
        else
            tvbString = "Cross your fingers!";
        tvB.setText(tvbString);

        float fraction = (float) (c.getBunked() * 1.0 / (2 * creditno));
        if (fraction >= 1)
            imgHm.setImageResource(R.drawable.hm_6);
        else {
            int n = (int) (fraction * 6);
            switch (n) {
                case 0:
                    imgHm.setImageResource(R.drawable.hm_0);
                    break;
                case 1:
                    imgHm.setImageResource(R.drawable.hm_1);
                    break;
                case 2:
                    imgHm.setImageResource(R.drawable.hm_2);
                    break;
                case 3:
                    imgHm.setImageResource(R.drawable.hm_3);
                    break;
                case 4:
                    imgHm.setImageResource(R.drawable.hm_4);
                    break;
                case 5:
                    imgHm.setImageResource(R.drawable.hm_5);
                    break;
                case 6:
                    imgHm.setImageResource(R.drawable.hm_6);
                    break;

            }
        }


//        pb.setMax(100);
//        if(c.getAttended()+c.getBunked()!=0){
//            pb.setProgress(percent);
//            tvPercent.setText(percent+"%");
//        }
//        else{
//            pb.setProgress(0);
//            tvPercent.setText("0%");
//        }

        return row;
    }

}