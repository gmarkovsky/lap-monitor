package com.gmail.gbmarkovsky.lm.distance;

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

import com.gmail.gbmarkovsky.R;
import com.gmail.gbmarkovsky.lm.controllers.CheckPointsManager;
import com.gmail.gbmarkovsky.lm.gui.AddDistanceCheckPoint;

public class DistanceCheckPoints extends Activity implements PropertyChangeListener {
	private Button addButton;
	private Button editButton;
	private Button delButton;
	
	private ListView listView;
	private ArrayAdapter<DistanceCheckPoint> arrayAdapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.distance_check_points);
        initControls();
        initListeners();
        CheckPointsManager.getInstance().addPropertyChangeListener(this);
    }
    
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getPropertyName().equals(CheckPointsManager.ADDED_DISTANCE_CHECK_POINT)) { 
			arrayAdapter.add((DistanceCheckPoint) event.getNewValue());
			listView.setAdapter(arrayAdapter);
		}
	}
	
	private void initControls() {
		addButton = (Button) findViewById(R.id.button_add_CP);
		editButton = (Button) findViewById(R.id.button_edit_CP);
		delButton = (Button) findViewById(R.id.button_del_CP);
		listView = (ListView) findViewById(R.id.listView1);
		arrayAdapter = new ArrayAdapter<DistanceCheckPoint>(this, android.R.layout.simple_list_item_1);
		Collection<DistanceCheckPoint> points = CheckPointsManager.getInstance().getDistanceCheckPoints();
		for (DistanceCheckPoint t: points) {
			arrayAdapter.add(t);
		}
		listView.setAdapter(arrayAdapter);
	}
	
	private void initListeners() {
        addButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				Intent intent = new Intent(DistanceCheckPoints.this, AddDistanceCheckPoint.class);
				startActivity(intent);
			}
		});
        editButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				Intent intent = new Intent(DistanceCheckPoints.this, AddDistanceCheckPoint.class);
				startActivity(intent);
			}
		});
        delButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				DistanceCheckPoint checkPoint = (DistanceCheckPoint) listView.getSelectedItem();
				arrayAdapter.remove(checkPoint);
				CheckPointsManager.getInstance().removeCheckPoint(checkPoint);
				listView.refreshDrawableState();
			}
		});
	}
}
