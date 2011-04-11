package com.gmail.gbmarkovsky.lm.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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
import android.widget.Toast;

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
//	private TextView latText;
//	private TextView longText;
	
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
//        latText = (TextView) findViewById(R.id.latitude_text);
//        longText = (TextView) findViewById(R.id.longitude_text);
        chronometer = (Chronometer) findViewById(R.id.main_chronometer);
        startButton = (Button) findViewById(R.id.button_start);
        //text = (TextView) findViewById(R.id.indicator);
        chronoTick = new OnChronometerTickListener() {
    		
    		public void onChronometerTick(Chronometer chronometer) {
    			TimeController.getInstance().tick();
    		}
    	};
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
    				chronometer.setBase(SystemClock.elapsedRealtime());
    				chronometer.start();
    				DistanceController.getInstance().startLogging();
    			} else {
    				startButton.setText("Start");
    				chronometer.stop();
    				chronometer.setBase(SystemClock.elapsedRealtime());
    				DistanceController.getInstance().stopLogging();
    				Trace trace = DistanceController.getInstance().getTrace();
					TraceSerializer.writeTrace(trace);
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
		case R.id.miCheckPoints:
			intent = new Intent(this, DistanceCheckPoints.class);
			startActivity(intent);
			break;
		case R.id.miTimeCheckPoints:
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
//		if (event.getPropertyName().equals(TimeController.TICK)) {
//			text.setText(event.getNewValue().toString());
//		}
		if (event.getPropertyName().equals(DistanceController.LOCATION_CHANGED)) {
			Location location = (Location) event.getNewValue();
            Toast.makeText(getBaseContext(), 
                    "Location changed : \nLat: " + location.getLatitude() + 
                    " Lng: " + location.getLongitude(), 
                    Toast.LENGTH_LONG).show();
		}
	}
	
//	public void saveTrace() {
//		boolean mExternalStorageAvailable = false;
//		boolean mExternalStorageWriteable = false;
//		String state = Environment.getExternalStorageState();
//
//		if (Environment.MEDIA_MOUNTED.equals(state)) {
//		    mExternalStorageAvailable = mExternalStorageWriteable = true;
//		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
//		    mExternalStorageAvailable = true;
//		    mExternalStorageWriteable = false;
//		} else {
//		    mExternalStorageAvailable = mExternalStorageWriteable = false;
//		}
//		
//	    File path = Environment.getExternalStoragePublicDirectory(
//	            Environment.DIRECTORY_PICTURES);
//	    File file = new File(path, "test.txt");
//
//	    try {
//	        path.mkdirs();
//
//	        OutputStream os = new FileOutputStream(file);
//	        String string = new String("Hello world!");
//	        os.write(string.getBytes("utf-8"));
//	        os.close();
//
//	        MediaScannerConnection.scanFile(this,
//	                new String[] { file.toString() }, null,
//	                new MediaScannerConnection.OnScanCompletedListener() {
//	            public void onScanCompleted(String path, Uri uri) {
//	                Log.i("ExternalStorage", "Scanned " + path + ":");
//	                Log.i("ExternalStorage", "-> uri=" + uri);
//	            }
//	        });
//	    } catch (IOException e) {
//	        Log.w("ExternalStorage", "Error writing " + file, e);
//	    }
//	}
}