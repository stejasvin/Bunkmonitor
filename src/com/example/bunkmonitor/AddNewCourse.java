package com.example.bunkmonitor;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Tejas on 11/2/13.
 */

//Intent params - IS_EDIT,COURSE_ID
public class AddNewCourse extends Activity {

    String[] slotsDays;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_courses);

        final SharedPreferences mPrefs = getSharedPreferences(
                "bunkmonitor.SHARED_PREF", 0);
        final SharedPreferences.Editor mEditor = mPrefs.edit();

        slotsDays = Utilities.getSlotsPerDay(this);

        Intent iThis = getIntent();
        final boolean isEdit = iThis.getBooleanExtra("IS_EDIT", false);
        final CourseDatabaseHandler courseDatabaseHandler = new CourseDatabaseHandler(this);
        Course c = null;

        final EditText etName = (EditText)findViewById(R.id.et_name);
        final EditText etId = (EditText)findViewById(R.id.et_id);
        final EditText etCredits = (EditText)findViewById(R.id.et_credits);
        final EditText etProf = (EditText)findViewById(R.id.et_prof);
        final CheckBox cbLab = (CheckBox)findViewById(R.id.et_lab);

        final CheckBox[] cbArray = new CheckBox[6];
        cbArray[0] = (CheckBox)findViewById(R.id.add_mon);
        cbArray[1] = (CheckBox)findViewById(R.id.add_tue);
        cbArray[2] = (CheckBox)findViewById(R.id.add_wed);
        cbArray[3] = (CheckBox)findViewById(R.id.add_thu);
        cbArray[4] = (CheckBox)findViewById(R.id.add_fri);
        cbArray[5] = (CheckBox)findViewById(R.id.add_sat);

        //final EditText etSlot = (EditText)findViewById(R.id.et_slot);

        if(isEdit){
            c = courseDatabaseHandler.getCourse(iThis.getStringExtra("COURSE_LID"));
            etName.setText(c.getName());
            etId.setText(c.getId());
            etCredits.setText(c.getCredits());
            etProf.setText(c.getProf());

            for(int i=0;i<6;i++)
                if(slotsDays[i].contains(c.getSlot()))
                    cbArray[i].setChecked(true);

        }

        final Button bCreate = (Button)findViewById(R.id.b_create);
        final Course finalC = c;
        bCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bCreate.setEnabled(false);
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
                if(etName.getText().toString().equals("")){
                    Toast.makeText(AddNewCourse.this,"Enter valid name",Toast.LENGTH_LONG).show();
                    return;
                }

                boolean flag = false;
                for(CheckBox cb:cbArray)
                    if(cb.isChecked()){
                        flag = true;
                        break;
                    }
                if(!flag){
                    Toast.makeText(AddNewCourse.this,"Tick atleast one day",Toast.LENGTH_LONG).show();
                    return;
                }

                Course cnew = new Course();
                cnew.setId(etId.getText().toString());
                cnew.setName(etName.getText().toString());
                cnew.setProf(etProf.getText().toString());
                cnew.setCredits(etCredits.getText().toString());

                if(cbLab.isChecked())
                    cnew.setIsLab(1);
                else
                    cnew.setIsLab(0);

                String lastSlot = mPrefs.getString("LAST_SLOT", "a");
                int charValue = lastSlot.charAt(0);
                String nextSlot = String.valueOf((char) (charValue + 1));

                cnew.setActive(1);
                if(isEdit){
                    cnew.setLocalId(finalC.getLocalId());
                    if(cnew.getSlot()==null || cnew.getSlot().equals(""))
                        cnew.setSlot(nextSlot);
                    else{
                        nextSlot = cnew.getSlot();
                        mEditor.putString("LAST_SLOT",nextSlot).commit();
                    }

                }else{
                    cnew.setSlot(nextSlot);
                    mEditor.putString("LAST_SLOT",nextSlot).commit();
                }

//                if(!cnew.getSlot().equals("") && isEdit)
//
//                else{
//
//                }

                for(int i=0;i<6;i++){
                    slotsDays[i].replace(cnew.getSlot(),"");
                }
                for(int i=0;i<6;i++){
                    if(cbArray[i].isChecked())
                        slotsDays[i] = slotsDays[i]+nextSlot;
                }

                Utilities.setSlotsPerDay(AddNewCourse.this,slotsDays);

                if(!isEdit)
                    courseDatabaseHandler.addCourse(cnew);
                else
                    courseDatabaseHandler.updateCourse(cnew);

                //startActivity(new Intent(AddNewCourse.this,MainActivity.class));
                setResult(RESULT_OK);
                finish();
                bCreate.setEnabled(true);
            }
        });

    }
}
