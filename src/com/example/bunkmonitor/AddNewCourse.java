package com.example.bunkmonitor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Tejas on 11/2/13.
 */

//Intent params - IS_EDIT,COURSE_ID
public class AddNewCourse extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_courses);

        Intent iThis = getIntent();
        final boolean isEdit = iThis.getBooleanExtra("IS_EDIT", false);
        final CourseDatabaseHandler courseDatabaseHandler = new CourseDatabaseHandler(this);
        Course c = null;

        final EditText etName = (EditText)findViewById(R.id.et_name);
        final EditText etId = (EditText)findViewById(R.id.et_id);
        final EditText etCredits = (EditText)findViewById(R.id.et_credits);
        final EditText etProf = (EditText)findViewById(R.id.et_prof);
        final EditText etSlot = (EditText)findViewById(R.id.et_slot);

        if(isEdit){
            c = courseDatabaseHandler.getCourse(iThis.getStringExtra("COURSE_ID"));
            etName.setText(c.getName());
            etId.setText(c.getId());
            etCredits.setText(c.getCredits());
            etProf.setText(c.getProf());
            etSlot.setText(c.getSlot());
        }

        Button bCreate = (Button)findViewById(R.id.b_create);
        final Course finalC = c;
        bCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    if(Integer.decode(etCredits.getText().toString())<=0){
                        Toast.makeText(AddNewCourse.this,"Enter valid credits",Toast.LENGTH_LONG).show();
                        return;
                    }
                }catch (NumberFormatException e){
                    e.printStackTrace();
                    Toast.makeText(AddNewCourse.this,"Enter valid credits",Toast.LENGTH_LONG).show();
                    return;
                }

                Course cnew = new Course();
                cnew.setId(etId.getText().toString());
                cnew.setName(etName.getText().toString());
                cnew.setCredits(etCredits.getText().toString());
                cnew.setProf(etProf.getText().toString());
                cnew.setSlot(etSlot.getText().toString());
                cnew.setActive(1);
                if(isEdit)
                    cnew.setLocalId(finalC.getLocalId());

                if(!isEdit)
                    courseDatabaseHandler.addCourse(cnew);
                else
                    courseDatabaseHandler.updateCourse(cnew);

                //startActivity(new Intent(AddNewCourse.this,MainActivity.class));
                setResult(RESULT_OK);
                finish();
            }
        });

    }
}
