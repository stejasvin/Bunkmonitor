


package com.example.bunkmonitor;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

/**
 * This class is a customized array adapter used to display the List of
 * messages and their details in a list.
 *
 * @author stejasvin
 * @since v1.0
 */

public class ExtraCourseListAdapter extends ArrayAdapter {

    List<Course> courseList;
    List<String> extraCourse;
    int textViewResourceId = R.layout.single_list_item_xlist;

    /**
     * Context
     */
    private Context context;
    CourseDatabaseHandler courseDatabaseHandler = null;

    public ExtraCourseListAdapter(Context context, List<Course> courseList, List<String> extraCourse) {
        super(context, R.layout.single_list_item_xlist, courseList);
        this.context = context;
        this.courseList = courseList;
        this.extraCourse = extraCourse;
        courseDatabaseHandler = new CourseDatabaseHandler(context);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(textViewResourceId, parent, false); // inflate view from xml file
        }

        //int epos = position - 1;

        row.setEnabled(false);

        TextView tvCourse = (TextView) row.findViewById(R.id.xlist_course);
        tvCourse.setTextColor(context.getResources().getColor(android.R.color.black));
        CheckBox cb = (CheckBox) row.findViewById(R.id.xlist_cb);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b && extraCourse.contains(courseList.get(position).getLocalId()))
                    extraCourse.remove(position);
                if(b){
                    extraCourse.add(courseList.get(position).getLocalId());
                }
            }
        });

//        if (position == 0) {
//
//            tvCourse.setText("Extra Courses");
//            tvCourse.setTextSize(20);
//            tvCourse.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
//            cb.setVisibility(View.GONE);
//            return row;
//        }else{
            tvCourse.setTextSize(15);
            tvCourse.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            cb.setVisibility(View.VISIBLE);



        final Course course = courseList.get(position);

        tvCourse.setText(course.getName());
        return row;
    }

}