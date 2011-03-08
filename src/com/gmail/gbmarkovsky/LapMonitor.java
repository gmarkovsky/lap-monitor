package com.gmail.gbmarkovsky;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class LapMonitor extends Activity implements PropertyChangeListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CheckPointsManager.create();
        TimeController.create();
        TimeController.getInstance().addPropertyChangeListener(this);
        setContentView(R.layout.main);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.main_menu, menu);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
		case R.id.miCheckPoints:
			Intent intent = new Intent(this, DistanceCheckPoints.class);
			startActivity(intent);
			break;

		default:
			break;
		}
    	return super.onOptionsItemSelected(item);
    }

	@Override
	public void propertyChange(PropertyChangeEvent event) {
			
	}
}