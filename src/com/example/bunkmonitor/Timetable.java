package com.example.bunkmonitor;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Timetable extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timetable);
		
	}
	
	public void textclick(View v){
		
		final TextView glob = (TextView)v;

		//Toast.makeText(this, v.toString(), Toast.LENGTH_LONG).show();
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.slotdialog);
		dialog.setTitle("Enter slot");
		final EditText t = (EditText)dialog.findViewById(R.id.editslot);
		
		
		
		Button dialogButton = (Button) dialog.findViewById(R.id.slotOk);
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final String slot = t.getText().toString();
				dialog.dismiss();
				glob.setText(slot);
			}
		});

		dialog.show();
		
	}
}
