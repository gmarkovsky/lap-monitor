package com.gmail.gbmarkovsky;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
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

public class LapMonitor extends Activity implements PropertyChangeListener {
	private Chronometer chronometer;
	private Button startButton;
	private TextView text;
	private MediaPlayer mp;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CheckPointsManager.create();
        TimeController.create();
        TimeController.getInstance().addPropertyChangeListener(this);
        setContentView(R.layout.main);
        chronometer = (Chronometer) findViewById(R.id.main_chronometer);
        startButton = (Button) findViewById(R.id.button_start);
        text = (TextView) findViewById(R.id.textView2);
        initListeners();
        mp = MediaPlayer.create(this, R.raw.mavrik);
    }
    
    private void initListeners() {
      startButton.setOnClickListener(new OnClickListener() {
		
		public void onClick(View arg0) {
			if (startButton.getText().equals("Start")) {
				startButton.setText("Stop");
				chronometer.setBase(SystemClock.elapsedRealtime());
				chronometer.start();
				mp.start();
			} else {
				startButton.setText("Start");
				chronometer.stop();
				mp.stop();		
			}
		}
	});
      chronometer.setOnChronometerTickListener(new OnChronometerTickListener() {
		
		public void onChronometerTick(Chronometer chronometer) {
			text.setText(chronometer.getText());
			TimeController.getInstance().tick();
		}
	});
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
			
		default:
			break;
		}
    	return super.onOptionsItemSelected(item);
    }

	public void propertyChange(PropertyChangeEvent event) {
		text.setText(event.getNewValue().toString());
	}
}