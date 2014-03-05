package com.example.bunkmonitor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by stejasvin on 3/5/14.
 */
public class optionsLongPress extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.option_longpress);

        Intent iThis = getIntent();
        final String courseLocalId = iThis.getStringExtra("COURSE_LID");

        Button bEdit = (Button)findViewById(R.id.longp_edit);
        bEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(optionsLongPress.this, AddNewCourse.class);
                intent.putExtra("IS_EDIT", true);
                intent.putExtra("COURSE_LID", courseLocalId);
                startActivity(intent);
                finish();
            }
        });

        Button bRem = (Button)findViewById(R.id.longp_rem);
        bRem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(optionsLongPress.this)
                        .setTitle("Confirmation")
                        .setMessage("Are you sure you want to remove this course?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                CourseDatabaseHandler courseDatabaseHandler = new CourseDatabaseHandler(optionsLongPress.this);
                                courseDatabaseHandler.deleteCourse(courseLocalId);

                                Toast.makeText(optionsLongPress.this,
                                        "Course removed",
                                        Toast.LENGTH_LONG).show();

                                setResult(RESULT_OK);
                                finish();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();



            }
        });
    }
}
