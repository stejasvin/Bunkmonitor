package com.example.bunkmonitor;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Tejas on 11/2/13.
 */

//Intent params - IS_EDIT,COURSE_ID
public class AddNewCourse extends Activity {

    String[] slotsDays;
    boolean flagDaysSetEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_courses);

        final SharedPreferences mPrefs = getSharedPreferences(
                "bunkmonitor.SHARED_PREF", 0);
        final SharedPreferences.Editor mEditor = mPrefs.edit();

        slotsDays = Utilities.getSlotsPerDay();

        Intent iThis = getIntent();
        final boolean isEdit = iThis.getBooleanExtra("IS_EDIT", false);
        final CourseDatabaseHandler courseDatabaseHandler = new CourseDatabaseHandler(this);
        Course c = null;

        final EditText etName = (EditText) findViewById(R.id.et_name);
        final EditText etId = (EditText) findViewById(R.id.et_id);
        final EditText etCredits = (EditText) findViewById(R.id.et_credits);
        final EditText etProf = (EditText) findViewById(R.id.et_prof);
        final EditText etMaxBunks = (EditText) findViewById(R.id.et_maxBunks);
        final TextView tvSlotChar = (TextView) findViewById(R.id.add_slot_tv);
        final Spinner spSlotChar = (Spinner) findViewById(R.id.add_slot_spinner);
        final CheckBox cbLab = (CheckBox) findViewById(R.id.et_lab);
        final CheckBox cbMaxBunks = (CheckBox) findViewById(R.id.add_maxbunks);
        final LinearLayout llMaxBunks = (LinearLayout) findViewById(R.id.add_llmaxbunks);

        final CheckBox[] cbArray = new CheckBox[7];

        cbArray[0] = (CheckBox) findViewById(R.id.add_sun);
        cbArray[1] = (CheckBox) findViewById(R.id.add_mon);
        cbArray[2] = (CheckBox) findViewById(R.id.add_tue);
        cbArray[3] = (CheckBox) findViewById(R.id.add_wed);
        cbArray[4] = (CheckBox) findViewById(R.id.add_thu);
        cbArray[5] = (CheckBox) findViewById(R.id.add_fri);
        cbArray[6] = (CheckBox) findViewById(R.id.add_sat);



        spSlotChar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(!isEdit || flagDaysSetEdit){
                String[] slotsArray = AddNewCourse.this.getResources().getStringArray(R.array.slots);
                tvSlotChar.setText(slotsArray[position]);
                for(int i=0;i<7;i++){
                    if(slotsDays[i].contains(slotsArray[position]))
                        cbArray[i].setChecked(true);
                    else
                        cbArray[i].setChecked(false);
                }
                }else{
                    flagDaysSetEdit=true;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//                new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                String[] slotsArray = AddNewCourse.this.getResources().getStringArray(R.array.slots);
//                tvSlotChar.setText("Slot selected - "+slotsArray[position]);
//                for(int i=0;i<7;i++){
//                    if(slotsDays[i].contains(slotsArray[position]))
//                        cbArray[i].setChecked(true);
//                    else
//                        cbArray[i].setChecked(false);
//
//                }
//            }
//        });

//        etSlotChar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                Toast.makeText(AddNewCourse.this,"Clicked - "+v.getText().toString(),Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });



        cbMaxBunks.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    llMaxBunks.setVisibility(View.GONE);
                else
                    llMaxBunks.setVisibility(View.VISIBLE);
            }
        });

        cbLab.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cbMaxBunks.setChecked(false);
                    cbMaxBunks.setVisibility(View.GONE);
                    llMaxBunks.setVisibility(View.VISIBLE);
                }else{
                    cbMaxBunks.setChecked(false);
                    cbMaxBunks.setVisibility(View.VISIBLE);
                    llMaxBunks.setVisibility(View.GONE);
                }
            }
        });

        //final EditText etSlot = (EditText)findViewById(R.id.et_slot);


        if (isEdit) {
            c = courseDatabaseHandler.getCourse(iThis.getStringExtra("COURSE_LID"));
            etName.setText(c.getName());
            etId.setText(c.getId());
            etCredits.setText(c.getCredits());
            etProf.setText(c.getProf());
            spSlotChar.setPrompt("Current slot - " + c.getSlot_char());
            tvSlotChar.setText(c.getSlot_char());

            if (c.getIs85() == 1) {
                cbMaxBunks.setChecked(true);
                llMaxBunks.setVisibility(View.GONE);
            } else {
                cbMaxBunks.setChecked(false);
                llMaxBunks.setVisibility(View.VISIBLE);
                etMaxBunks.setText("" + c.getMaxBunks());
            }

            if (c.getIsLab() == 1)
                cbLab.setChecked(true);
            else
                cbLab.setChecked(false);

            if(c.getSlot()!=null){

                for (int i = 0; i < 7; i++){
                    if(c.getSlot().contains(i+1+""))
                        cbArray[i].setChecked(true);
                    else
                        cbArray[i].setChecked(false);
                }

                //String[] slotsArray = c.getSlot().split("#");
//                int iDay;
//                for(int i=0;i<slotsArray.length;i++){
//                    iDay = Integer.decode(slotsArray[i]);
//                    cbArray[iDay-1].setChecked(true);
//
//                }
            }

        }

        final Button bCreate = (Button) findViewById(R.id.b_create);
        final Course finalC = c;
        bCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bCreate.setEnabled(false);
                try {
                    if (Integer.decode(etCredits.getText().toString()) <= 0) {
                        Toast.makeText(AddNewCourse.this, "Enter valid credits", Toast.LENGTH_LONG).show();
                        return;
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    Toast.makeText(AddNewCourse.this, "Enter valid credits", Toast.LENGTH_LONG).show();
                    return;
                }
                if (etName.getText().toString().equals("")) {
                    Toast.makeText(AddNewCourse.this, "Enter valid name", Toast.LENGTH_LONG).show();
                    return;
                }
                if(!cbMaxBunks.isChecked() && (etMaxBunks.getText().equals(""))){

                    Toast.makeText(AddNewCourse.this, "Enter max. bunks", Toast.LENGTH_LONG).show();
                    return;
                }

                boolean flag = false;
                for (CheckBox cb : cbArray)
                    if (cb.isChecked()) {
                        flag = true;
                        break;
                    }
                if (!flag) {
                    Toast.makeText(AddNewCourse.this, "Tick atleast one day", Toast.LENGTH_LONG).show();
                    return;
                }

                Course cnew = new Course();
                cnew.setId(etId.getText().toString());
                cnew.setName(etName.getText().toString());
                cnew.setProf(etProf.getText().toString());
                cnew.setCredits(etCredits.getText().toString());
                cnew.setSlot_char(tvSlotChar.getText().toString());

                if (cbLab.isChecked())
                    cnew.setIsLab(1);
                else
                    cnew.setIsLab(0);

                if (cbMaxBunks.isChecked()) {
                    cnew.setIs85(1);
                    int maxBunks;

                    maxBunks = 2 * Integer.decode(cnew.getCredits());
                    if (cnew.getIsLab() == 1)
                        maxBunks /= 2;

                    cnew.setMaxBunks(maxBunks);
                } else {
                    cnew.setIs85(0);
                    cnew.setMaxBunks(Integer.decode(etMaxBunks.getText().toString()));
                }

//                String lastSlot = mPrefs.getString("LAST_SLOT", "a");
//                int charValue = lastSlot.charAt(0);
//                String nextSlot = String.valueOf((char) (charValue + 1));

                String slots = "";
                for(int i=0;i<cbArray.length;i++)
                    if(cbArray[i].isChecked())
                        //i+1 to use Calendar enumeration
                        slots = slots.concat((i+1)+"#");

                cnew.setActive(1);
                if (isEdit) {
                    cnew.setLocalId(finalC.getLocalId());
                    if (cnew.getSlot() == null || cnew.getSlot().equals(""))
                        cnew.setSlot(slots);

                } else {
                    cnew.setSlot(slots);

                }

                if (!isEdit)
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
