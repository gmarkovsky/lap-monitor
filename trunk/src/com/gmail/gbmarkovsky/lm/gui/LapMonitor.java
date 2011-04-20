package com.gmail.gbmarkovsky.lm.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.TextView;

import com.gmail.gbmarkovsky.R;
import com.gmail.gbmarkovsky.lm.controllers.CheckPointsManager;
import com.gmail.gbmarkovsky.lm.controllers.DistanceController;
import com.gmail.gbmarkovsky.lm.controllers.NotificationManager;
import com.gmail.gbmarkovsky.lm.controllers.TimeController;
import com.gmail.gbmarkovsky.lm.distance.DistanceCheckPoints;
import com.gmail.gbmarkovsky.lm.distance.TimeCheckPoints;
import com.gmail.gbmarkovsky.lm.distance.Trace;
import com.gmail.gbmarkovsky.lm.gmaps.FusionTableAdapter;
import com.gmail.gbmarkovsky.lm.io.TraceSerializer;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

public class LapMonitor extends Activity implements PropertyChangeListener {
	private Chronometer chronometer;
	private Button startButton;
	private OnChronometerTickListener chronoTick;
	private long startTime;
	private TextView coordinates;
	private FusionTableAdapter fusionTableAdapter;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CheckPointsManager.create();
        NotificationManager.create(this);
        TimeController.create();
        TimeController.getInstance().addPropertyChangeListener(this);
        TimeController.getInstance().addPropertyChangeListener(CheckPointsManager.getInstance());
        DistanceController.create(this);
        DistanceController.getInstance().addPropertyChangeListener(this);
        setContentView(R.layout.main);
        coordinates = (TextView) findViewById(R.id.coordinates);
        coordinates.setText("00:00:00.000 С  00:00:00.000 В");
        chronometer = (Chronometer) findViewById(R.id.main_chronometer);
        startButton = (Button) findViewById(R.id.button_start);
        chronoTick = new OnChronometerTickListener() {
    		
    		public void onChronometerTick(Chronometer chronometer) {
    			TimeController.getInstance().tick();
    		}
    	};
    	try {
			fusionTableAdapter = new FusionTableAdapter("gbmarkovsky@gmail.com", "vtnfkkjltntrnjh13");
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        initListeners();
    	// тестовые точки
    	CheckPointsManager.getInstance().createTimeCheckPoint(18000, true);
    	CheckPointsManager.getInstance().createTimeCheckPoint(40000, true);
    	CheckPointsManager.getInstance().createDistanceCheckPoint(1000, true);
    	CheckPointsManager.getInstance().createDistanceCheckPoint(2000, true);
    }
    
    private void initListeners() {
    	startButton.setOnClickListener(new OnClickListener() {
		
    		public void onClick(View arg0) {
    			if (startButton.getText().equals("Start")) {
    				startButton.setText("Stop");
    				startTime = System.currentTimeMillis();
    				chronometer.setBase(SystemClock.elapsedRealtime());
    				chronometer.start();
    				DistanceController.getInstance().resetLigging();
    				DistanceController.getInstance().startLogging();
    			} else {
    				startButton.setText("Start");
    				chronometer.stop();
    				chronometer.setBase(SystemClock.elapsedRealtime());
    				DistanceController.getInstance().stopLogging();
    				Trace trace = DistanceController.getInstance().getTrace();
    				Calendar time = new GregorianCalendar();
    				time.setTimeInMillis(startTime);
    				String fileName = String.format("trace_%1$tY-%1$tm-%1$td_%1$tH-%1$tM-%1$tS.kml", time);
					TraceSerializer.writeTrace(trace, fileName);
					try {
						fusionTableAdapter.createQuery("CREATE TABLE '" + fileName +
								"' (description:STRING, name:STRING, geometry:LOCATION)");
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ServiceException e) {
						e.printStackTrace();
					}
    			}
    		}
    	});
    	chronometer.setOnChronometerTickListener(chronoTick);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.main_menu, menu);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	Intent intent;
    	switch (item.getItemId()) {
		case R.id.miDistance:
			intent = new Intent(this, DistanceCheckPoints.class);
			startActivity(intent);
			break;
		case R.id.miTime:
			intent = new Intent(this, TimeCheckPoints.class);
			startActivity(intent);
			break;
		case R.id.miMap:
			intent = new Intent(this, MapViewer.class);
			startActivity(intent);
			break;
			
		default:
			break;
		}
    	return super.onOptionsItemSelected(item);
    }

	public void propertyChange(PropertyChangeEvent event) {
		if (event.getPropertyName().equals(DistanceController.LOCATION_CHANGED)) {
			Location location = (Location) event.getNewValue();
			String latitudeString = "";
			if (location.getLatitude() > 0) {
				latitudeString = Location.convert(location.getLatitude(), Location.FORMAT_SECONDS) + " C";
			} else {
				latitudeString = Location.convert(-location.getLatitude(), Location.FORMAT_SECONDS) + " Ю";
			}
			String longtudeString = "";
			if (location.getLatitude() > 0) {
				longtudeString = Location.convert(location.getLongitude(), Location.FORMAT_SECONDS) + " В";
			} else {
				longtudeString = Location.convert(-location.getLongitude(), Location.FORMAT_SECONDS) + " З";
			}
            coordinates.setText(latitudeString + "  " + longtudeString);
		}
	}
}