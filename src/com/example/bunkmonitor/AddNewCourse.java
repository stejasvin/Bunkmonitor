package com.example.bunkmonitor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Tejas on 11/2/13.
 */
public class AddNewCourse extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_courses);

        final EditText etName = (EditText)findViewById(R.id.et_name);
        final EditText etId = (EditText)findViewById(R.id.et_id);
        final EditText etCredits = (EditText)findViewById(R.id.et_credits);
        final EditText etProf = (EditText)findViewById(R.id.et_prof);
        final EditText etSlot = (EditText)findViewById(R.id.et_slot);

        Button bCreate = (Button)findViewById(R.id.b_create);
        bCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Course c = new Course();
                c.setId(etId.getText().toString());
                c.setName(etName.getText().toString());
                c.setCredits(etCredits.getText().toString());
                c.setProf(etProf.getText().toString());
                c.setSlot(etSlot.getText().toString());
                c.setActive(1);

                CourseDatabaseHandler courseDatabaseHandler = new CourseDatabaseHandler(AddNewCourse.this);
                courseDatabaseHandler.addCourse(c);

                //startActivity(new Intent(AddNewCourse.this,MainActivity.class));
                setResult(RESULT_OK);
                finish();
            }
        });

    }
}
