


package com.example.bunkmonitor;

import android.content.Context;
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

public class LoadAdapter extends ArrayAdapter {

    List<Course> courseList;
    int textViewResourceId;
    boolean[] checkList;

    /**
     * Context
     */
    private Context context;
    CourseDatabaseHandler courseDatabaseHandler = null;

    public LoadAdapter(Context context, List<Course> courseList, int textViewResourceId, boolean[] checkList) {
        super(context, textViewResourceId, courseList);
        this.context = context;
        this.courseList = courseList;
        this.textViewResourceId = textViewResourceId;
        this.checkList = checkList;
        courseDatabaseHandler = new CourseDatabaseHandler(context);

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(textViewResourceId, parent, false); // inflate view from xml file
        }

        row.setEnabled(false);

        TextView tvCourse = (TextView) row.findViewById(R.id.share_course);
        CheckBox checkBox = (CheckBox) row.findViewById(R.id.share_cb);

        checkBox.setChecked(checkList[position]);

        final int fpos = position;
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkList[fpos] = isChecked;
            }
        });

        final Course course = courseList.get(position);

        tvCourse.setText(course.getName());
        //tvbunks.setText(tvBString);

        return row;
    }


}