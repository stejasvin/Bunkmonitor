package com.example.bunkmonitor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by stejasvin on 3/3/14.
 */
public class CoursesExpListAdapter implements ExpandableListAdapter {

    List<Course> cList;
    boolean[] isExpanded;
    HashMap<String, ArrayList<String>> hashMap;
    int textViewResourceId, textViewResourceId2;
    /**
     * Context
     */
    private Context context;
    private Activity activity;
    View row;

    public CoursesExpListAdapter(Activity activity, int textViewResourceId, int textViewResourceId2,
                                 List<Course> cList, HashMap<String, ArrayList<String>> hashMap) {
        //super(context, textViewResourceId, cList);
        this.textViewResourceId = textViewResourceId;
        this.textViewResourceId2 = textViewResourceId2;
        this.context = activity;
        this.activity = activity;
        this.cList = cList;
        this.hashMap = hashMap;
        isExpanded = new boolean[cList.size()];
        for (int i = 0; i < cList.size(); i++) {
            isExpanded[i] = false;
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

        row = convertView;

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row = inflater.inflate(textViewResourceId, parent, false); // inflate view from xml file
        }

        if (isExpanded)
            row.setBackgroundResource(R.drawable.unfolded_up1);
        else
            row.setBackgroundResource(R.drawable.note1);

        TextView tvName = (TextView) row.findViewById(R.id.clist_name);
        //TextView tvId = (TextView) row.findViewById(R.id.clist_id);
//        TextView tvSlot = (TextView) row.findViewById(R.id.clist_slot);
        ImageView imgHm = (ImageView) row.findViewById(R.id.clist_hm);
        imgHm.setVisibility(View.VISIBLE);
        TextView tvSlot = (TextView) row.findViewById(R.id.clist_slot);
        TextView tvB = (TextView) row.findViewById(R.id.clist_bunked);

        Course c = cList.get(groupPosition);
        int totalBunks = c.getBunked()+c.getUdBunks();

        tvName.setText(c.getName());

        if(c.getSlot_char()!=null && !c.getSlot_char().equals(""))
            tvSlot.setText("Slot: " + c.getSlot_char());
        else
            tvSlot.setVisibility(View.GONE);

        int maxBunks = c.getMaxBunks();
        String tvbString;
//        try {
//            if (maxBunks < 0) {
//                imgHm.setVisibility(View.GONE);
//                tvB.setText("#bunks - " + totalBunks);
//                return row;
//            }
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//            imgHm.setVisibility(View.GONE);
//            tvB.setText("#bunks - " + c.getBunked()+c.getUdBunks());
//            return row;
//        }

        if (maxBunks > (totalBunks))
            tvbString = "bunks left - " + (maxBunks - totalBunks);
        else
            tvbString = "Cross your fingers! Your bunks are over";
        tvB.setText(tvbString);

        float fraction = (float) ( (totalBunks) * 1.0 / (maxBunks));
        if (fraction >= 1)
            imgHm.setImageResource(R.drawable.hm_6);
        else {
            int n = (int) (fraction * 6);
            switch (n) {
                case 0:
                    imgHm.setImageResource(R.drawable.hangman_7);
                    break;
                case 1:
                    imgHm.setImageResource(R.drawable.hangman_6);
                    break;
                case 2:
                    imgHm.setImageResource(R.drawable.hangman_5);
                    break;
                case 3:
                    imgHm.setImageResource(R.drawable.hangman_4);
                    break;
                case 4:
                    imgHm.setImageResource(R.drawable.hangman_3);
                    break;
                case 5:
                    imgHm.setImageResource(R.drawable.hangman_2);
                    break;
                case 6:
                    imgHm.setImageResource(R.drawable.hangman_1);
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
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        View childRow = convertView;
        final Course course = cList.get(groupPosition);
        final CourseDatabaseHandler courseDatabaseHandler = new CourseDatabaseHandler(context);

        if (childRow == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            childRow = inflater.inflate(textViewResourceId2, parent, false); // inflate view from xml file
        }

        final TextView tvNob = (TextView) childRow.findViewById(R.id.clist1_nob);
        final LinearLayout linearLayout = (LinearLayout) childRow.findViewById(R.id.clist1_ll);
        final LinearLayout llDatedBunks = (LinearLayout) childRow.findViewById(R.id.clist1_ll4);
        linearLayout.removeAllViews();
        final TextView tvUd = (TextView)childRow.findViewById(R.id.clist1_udbunk_tv);
        tvUd.setText(""+course.getUdBunks());
        final Button bUdInc = (Button)childRow.findViewById(R.id.clist1_udbunk_inc);
        final ArrayList<String> dateList = hashMap.get(cList.get(groupPosition).getLocalId());

        if(course.getUdBunks()==0)
            tvNob.setVisibility(View.VISIBLE);
        else
            tvNob.setVisibility(View.GONE);

        bUdInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bUdInc.setEnabled(false);

                int i;
                try {
                    i = Integer.decode(tvUd.getText().toString());
                }catch (NumberFormatException e){
                    i=0;
                }
                if(i>0)
                    bUdInc.setEnabled(true);
                i++;
                tvUd.setText(i+"");
                course.setUdBunks(i);
                courseDatabaseHandler.updateCourse(course);
                row.destroyDrawingCache();
                row.setVisibility(View.INVISIBLE);
                row.setVisibility(View.VISIBLE);
                tvNob.setVisibility(View.GONE);

                bUdInc.setEnabled(true);
            }
        });

        final Button bUddec = (Button)childRow.findViewById(R.id.clist1_udbunk_dec);
        bUddec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bUddec.setEnabled(false);

                int i = Integer.decode(tvUd.getText().toString());
                if(i<=0){
                    bUddec.setEnabled(false);
                }
                else{
                    i--;
                    tvUd.setText(i+"");
                    course.setUdBunks(i);
                    courseDatabaseHandler.updateCourse(course);
                    if(i==0 && (dateList==null || dateList.isEmpty()))
                        tvNob.setVisibility(View.VISIBLE);
                }

                bUddec.setEnabled(true);
            }
        });

        if (dateList != null && !dateList.isEmpty()) {
            tvNob.setVisibility(View.GONE);
            //llTotal.setVisibility(View.VISIBLE);
            llDatedBunks.setVisibility(View.VISIBLE);
            final CheckBox[] checkBoxes = new CheckBox[dateList.size()];
            for (int i = 0; i < dateList.size(); i++) {
                String s = dateList.get(i);
                //CheckBox checkBox = new CheckBox(context);
                checkBoxes[i] = new CheckBox(context);
                checkBoxes[i].setText(s);
                checkBoxes[i].setId(i);
                linearLayout.addView(checkBoxes[i]);
            }

            final Button bAdd = (Button) childRow.findViewById(R.id.clist1_addb);
            bAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bAdd.setEnabled(false);
                    Intent intent = new Intent(context, EditEntryActivity.class);
                    intent.putExtra("COURSE_LOCAL_ID",course.getLocalId());
                    activity.startActivityForResult(intent,MainActivity.REFRESH);
                    bAdd.setEnabled(true);

                }
            });

            final Button bDel = (Button) childRow.findViewById(R.id.clist1_delb);
            bDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    bDel.setEnabled(false);
                    if(dateList!=null && !dateList.isEmpty()){
                    new AlertDialog.Builder(context)
                            .setTitle("Confirmation")
                            .setMessage("Are you sure you want to remove selected bunk(s)?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    EntryDetailsDatabaseHandler entryDetailsDatabaseHandler = new EntryDetailsDatabaseHandler(context);
                                    //done in reverse so that the index of dates doesnt change
                                    for (int i = checkBoxes.length-1; i >= 0; i--) {
                                        if (checkBoxes[i].isChecked()) {
                                            entryDetailsDatabaseHandler.changeBunkToAttEntry(context, cList.get(groupPosition), dateList.get(i));
                                            linearLayout.removeView(checkBoxes[i]);
                                            hashMap.get(cList.get(groupPosition).getLocalId());
                                            //dateList.remove(i);

                                        }
                                    }


                                    if (hashMap.get(cList.get(groupPosition).getLocalId()).isEmpty()) {

                                        llDatedBunks.setVisibility(View.GONE);
                                        if(course.getUdBunks()==0)
                                            tvNob.setVisibility(View.VISIBLE);
                                        //llTotal.setVisibility(View.GONE);
                                    }

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                    }else{
                        Toast.makeText(context,"Tick any date",Toast.LENGTH_SHORT).show();
                    }

                    bDel.setEnabled(true);
                }
            });
        }else {
            llDatedBunks.setVisibility(View.GONE);
        }
        return childRow;
    }

    private void deleteBunk(String cId, String sDate) {

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
        isExpanded[groupPosition] = true;

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        isExpanded[groupPosition] = false;
    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

    public boolean[] getIsExpanded() {
        return isExpanded;
    }
}
