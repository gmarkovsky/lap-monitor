package com.gmail.gbmarkovsky;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class TimeCheckPoints extends Activity implements PropertyChangeListener {
	private Button addButton;
	private ListView listView;
	private ArrayAdapter<TimeCheckPoint> arrayAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_check_points);
        initControls();
        initListeners();
        CheckPointsManager.getInstance().addPropertyChangeListener(this);
    }
    
	
	private void initControls() {
		addButton = (Button) findViewById(R.id.add_time_cp_button);
		listView = (ListView) findViewById(R.id.time_cp_listview);
		arrayAdapter = new ArrayAdapter<TimeCheckPoint>(this, android.R.layout.simple_list_item_1);
		Collection<TimeCheckPoint> points = CheckPointsManager.getInstance().getTimeCheckPoints();
		for (TimeCheckPoint t: points) {
			arrayAdapter.add(t);
		}
		listView.setAdapter(arrayAdapter);
	}
	
	private void initListeners() {
        addButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				CheckPointsManager.getInstance().createTimeCheckPoint(15000, true);
				CheckPointsManager.getInstance().createTimeCheckPoint(150000, true);
				CheckPointsManager.getInstance().createTimeCheckPoint(2300000, true);
				Intent intent = new Intent(TimeCheckPoints.this, AddTimeCheckPoint.class);
				startActivity(intent);
			}
		});
	}


	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getPropertyName().equals(CheckPointsManager.ADDED_TIME_CHECK_POINT)) { 
			arrayAdapter.add((TimeCheckPoint) event.getNewValue());
			listView.setAdapter(arrayAdapter);
		}
		
	}
}
