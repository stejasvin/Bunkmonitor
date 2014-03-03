package com.example.bunkmonitor;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by stejasvin on 3/3/14.
 */
public class CoursesExpListAdapter implements ExpandableListAdapter {

    List<Course> cList;
    boolean[] isExpanded;

    int textViewResourceId,textViewResourceId2;
    /**
     * Context
     */
    private Context context;

    public CoursesExpListAdapter(Context context, int textViewResourceId, int textViewResourceId2, List<Course> cList) {
        //super(context, textViewResourceId, cList);
        this.textViewResourceId = textViewResourceId;
        this.textViewResourceId2 = textViewResourceId2;
        this.context = context;
        this.cList = cList;

        isExpanded = new boolean[cList.size()];
        for(int i=0;i<cList.size();i++){
            isExpanded[i]=false;
        }

    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        return cList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        View row = convertView;

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row = inflater.inflate(textViewResourceId, parent, false); // inflate view from xml file
        }

        if(isExpanded)
            row.setBackgroundResource(R.drawable.unfolded_up1);
        else
            row.setBackgroundResource(R.drawable.note1);

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
        Course c = cList.get(groupPosition);
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

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        View row = convertView;

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row = inflater.inflate(textViewResourceId2, parent, false); // inflate view from xml file
        }

        TextView tvCid = (TextView) row.findViewById(R.id.clist1_cid);
        TextView tvCred = (TextView) row.findViewById(R.id.clist1_cred);
        TextView tvProf = (TextView) row.findViewById(R.id.clist1_prof);
        TextView tvA = (TextView) row.findViewById(R.id.clist1_a);
        TextView tvB = (TextView) row.findViewById(R.id.clist1_b);
        TextView tvC = (TextView) row.findViewById(R.id.clist1_c);

        tvCid.setText("ID: "+cList.get(groupPosition).getId());
        tvCred.setText("Cr: "+ cList.get(groupPosition).getCredits());
        tvProf.setText("Prof: "+cList.get(groupPosition).getProf());
        tvA.setText("Attended: "+cList.get(groupPosition).getAttended());
        tvB.setText("Bunked: "+cList.get(groupPosition).getBunked());
        tvC.setText("Cancelled: "+cList.get(groupPosition).getCancelled());


        return row;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        isExpanded[groupPosition]=true;

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        isExpanded[groupPosition]=false;
    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

    public boolean[] getIsExpanded(){
        return isExpanded;
    }
}
