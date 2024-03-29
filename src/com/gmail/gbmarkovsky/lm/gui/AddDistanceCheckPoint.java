package com.gmail.gbmarkovsky.lm.gui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.gmail.gbmarkovsky.R;
import com.gmail.gbmarkovsky.lm.controllers.CheckPointsManager;

public class AddDistanceCheckPoint extends Activity {
	protected ArrayAdapter<CharSequence> arrayAdapter;
	private String mes = "m";
	private Button okButton;
	private Button cancelButton;
	private Spinner mesure;
	private EditText distance;
	private CheckBox isMultiple;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_distance_check_point);
        initControls();
        initListeners();
    }
    
	private void initControls() {
		okButton = (Button) findViewById(R.id.addDisCPOKButton);
		cancelButton = (Button) findViewById(R.id.addDisCPCancelButton);
		distance = (EditText) findViewById(R.id.edDistance);
        arrayAdapter = ArrayAdapter.createFromResource(this, R.array.Measures, 
        		android.R.layout.simple_spinner_dropdown_item);
        mesure = (Spinner) findViewById(R.id.spDisMes);
        mesure.setAdapter(arrayAdapter);
        isMultiple = (CheckBox) findViewById(R.id.isMultiple_check_box);
	}
	
	private void initListeners() {
        okButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				double numDis = 0;
				try {
					numDis = Double.parseDouble(AddDistanceCheckPoint.this.distance.getText().toString());
				} catch (Exception e) {
					AlertDialog.Builder builder = new AlertDialog.Builder(AddDistanceCheckPoint.this);
					builder.setMessage("Задано не число!")
					       .setCancelable(false)
					       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					                dialog.cancel();
					           }
					       });
					AlertDialog alert = builder.create();
					alert.show();
					return;
				}
				double distance = numDis;
				if (mes.equals("km")) {
					distance = numDis * 1000;
				}
				boolean isSingle = !isMultiple.isChecked();
				CheckPointsManager.getInstance().createDistanceCheckPoint(distance, isSingle);
				finish();
			}
		});
        cancelButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				finish();
			}
		});
        mesure.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View v, int pos, long row) {
				mes = parent.getItemAtPosition(pos).toString();
			}

			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
	}
}
