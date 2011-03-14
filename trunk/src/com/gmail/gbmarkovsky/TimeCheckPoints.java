package com.gmail.gbmarkovsky;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TimeCheckPoints extends Activity {
	private Button addButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_check_points);
        initControls();
        initListeners();
    }
    
	
	private void initControls() {
		addButton = (Button) findViewById(R.id.add_time_cp_button);
	}
	
	private void initListeners() {
        addButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				Intent intent = new Intent(TimeCheckPoints.this, AddTimeCheckPoint.class);
				startActivity(intent);
			}
		});
	}
}
