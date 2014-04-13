


package com.example.bunkmonitor;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * This class is a customized array adapter used to display the List of
 * messages and their details in a list.
 *
 * @author stejasvin
 * @since v1.0
 */

public class BunkListAdapter extends ArrayAdapter {

    List<Course> courseList;
    int textViewResourceId = R.layout.single_list_item_bunk;

    /**
     * Context
     */
    private Context context;
    CourseDatabaseHandler courseDatabaseHandler = null;

    public BunkListAdapter(Context context, List<Course> courseList) {
        super(context, R.layout.single_list_item_bunk, courseList);
        this.context = context;
        this.courseList = courseList;
        courseDatabaseHandler = new CourseDatabaseHandler(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(textViewResourceId, parent, false); // inflate view from xml file
        }

        int epos = position - 1;

        row.setEnabled(false);

        TextView tvCourse = (TextView) row.findViewById(R.id.bunk_course);
        tvCourse.setTextColor(context.getResources().getColor(android.R.color.black));
        TextView tvbunks = (TextView) row.findViewById(R.id.bunk_bunks_left);
        tvbunks.setTextColor(context.getResources().getColor(android.R.color.black));

        if (position == 0) {

            tvCourse.setText("Course");
            tvCourse.setTextSize(20);
            tvCourse.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tvbunks.setText("Bunks Left");
            tvbunks.setTextSize(20);
            tvbunks.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            return row;
        }else{
            tvCourse.setTextSize(15);
            tvCourse.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            tvbunks.setTextSize(20);
            tvbunks.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

        }

        final Course course = courseList.get(epos);

        int maxBunks = course.getMaxBunks(), bLeft = 0;
        String tvBString;

        bLeft = maxBunks - course.getBunked() - course.getUdBunks();
        if (bLeft < 0)
            bLeft = 0;
        tvBString = bLeft + "";

        tvCourse.setText(course.getName());
        tvbunks.setText(tvBString);

        return row;
    }

    @Override
    public int getCount() {
        return courseList.size() + 1;
    }
}