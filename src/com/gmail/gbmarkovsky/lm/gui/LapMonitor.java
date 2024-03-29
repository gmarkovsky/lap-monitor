package com.gmail.gbmarkovsky.lm.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
import com.gmail.gbmarkovsky.lm.io.TraceSerializer;

public class LapMonitor extends Activity implements PropertyChangeListener {
	private Chronometer chronometer;
	private Button startButton;
	private OnChronometerTickListener chronoTick;
	private long startTime;
	private TextView coordinates;
	private TextView distance;
	private TextView speed;
	private TextView speed_av;
//	private FusionTableAdapter2 fusionTableAdapter;
	
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
        DistanceController.getInstance().addPropertyChangeListener(CheckPointsManager.getInstance());
        setContentView(R.layout.main);
        coordinates = (TextView) findViewById(R.id.coordinates);
        coordinates.setText("00:00:00.000 С  00:00:00.000 В");
        
        distance = (TextView) findViewById(R.id.distance_left);
        distance.setText("Пройдено 0 м");
        
        speed = (TextView) findViewById(R.id.speed);
        speed.setText("Скорость 0 км/ч");
        
        speed_av = (TextView) findViewById(R.id.speed_avarage);
        speed_av.setText("Средняя скорость 0 км/ч");
        
        chronometer = (Chronometer) findViewById(R.id.main_chronometer);
        startButton = (Button) findViewById(R.id.button_start);
        chronoTick = new OnChronometerTickListener() {
    		
    		public void onChronometerTick(Chronometer chronometer) {
    			TimeController.getInstance().tick();
    		}
    	};
        initListeners();
    	// тестовые точки
    	CheckPointsManager.getInstance().createTimeCheckPoint(10000, true);
    	CheckPointsManager.getInstance().createTimeCheckPoint(30000, true);
    	CheckPointsManager.getInstance().createDistanceCheckPoint(1000, true);
    	CheckPointsManager.getInstance().createDistanceCheckPoint(2000, true);
    	CheckPointsManager.getInstance().createDistanceCheckPoint(4000, true);
    }
    
    private void initListeners() {
    	startButton.setOnClickListener(new OnClickListener() {
		
    		public void onClick(View arg0) {
    			if (startButton.getText().equals("Start")) {
    				startButton.setText("Stop");
    				startTime = System.currentTimeMillis();
    				chronometer.setBase(SystemClock.elapsedRealtime());
    				chronometer.start();
    				DistanceController.getInstance().resetLogging();
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
					//FusionTableLoader.writeTrace(trace, fileName);
					DistanceController.getInstance().resetLogging();
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
            double v = location.getSpeed(); 
            speed.setText("Скорость " + (int) Math.ceil(v) + " км/ч");
            long t = TimeController.getInstance().getTime() / 1000;
            double s = DistanceController.getInstance().getDistance();
            double v_a = 3.6 * s / (double) t;
            speed_av.setText("Средняя скорость " + (int) Math.ceil(v_a) + " км/ч");
		} else if (event.getPropertyName().equals(DistanceController.DISTANCE_CHANGED)) {
			double s = (Double) event.getNewValue();
			distance.setText("Пройдено " + (int) Math.ceil(s) + " м");
		}
	}
}