package com.gmail.gbmarkovsky;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class AddTimeCheckPoint extends Activity{
	private Button okButton;
	private Button cancelButton;
	private EditText minutes;
	private EditText seconds;
	private CheckBox isMultiple;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_time_check_point);
        initControls();
        initListeners();
    }
    
	private void initControls() {
		okButton = (Button) findViewById(R.id.button_ok_add_cp_time);
		cancelButton = (Button) findViewById(R.id.button_cancel_add_cp_time);
		minutes = (EditText) findViewById(R.id.time_minutes);
		seconds = (EditText) findViewById(R.id.time_seconds);
        isMultiple = (CheckBox) findViewById(R.id.isMultiple_time_cp);
	}
	
	private void initListeners() {
        okButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				double mins = 0;
				double secs = 0;
				try {
					mins = Integer.parseInt(minutes.getText().toString());
					secs = Integer.parseInt(seconds.getText().toString());
				} catch (Exception e) {
					AlertDialog.Builder builder = new AlertDialog.Builder(AddTimeCheckPoint.this);
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
				if (mins > 60) {
					AlertDialog.Builder builder = new AlertDialog.Builder(AddTimeCheckPoint.this);
					builder.setMessage("Минут не может быть больше 60!")
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
				if (secs > 60) {
					AlertDialog.Builder builder = new AlertDialog.Builder(AddTimeCheckPoint.this);
					builder.setMessage("Минут не может быть больше 60!")
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
				long time = (long) ((mins * 60 + secs) * 1000);
				boolean isSingle = !isMultiple.isChecked();
				CheckPointsManager.getInstance().createTimeCheckPoint(time, isSingle);
				finish();
			}
		});
        cancelButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				finish();
			}
		});
	}
}
