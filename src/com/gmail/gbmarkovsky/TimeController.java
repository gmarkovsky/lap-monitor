package com.gmail.gbmarkovsky;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Управляет работой со временем. Сообщает слушателям о изменении вермени.
 * @author george
 *
 */
public class TimeController {

	private long time;
	
	private static TimeController instance;
	private final PropertyChangeSupport propertyChangeSupport;
	private Timer timer;
	
	private TimeController() {
		propertyChangeSupport = new PropertyChangeSupport(this);
		timer = new Timer(true);
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				time += 50;
			}
		};
		timer.schedule(task, 0, 50);
	}
	
	public static void create() {
		instance = new TimeController();
	}
	
	public static TimeController getInstance() {
		return instance;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener p) {
		propertyChangeSupport.addPropertyChangeListener(p);
	}

	public void removePropertyChangeListener(PropertyChangeListener p) {
		propertyChangeSupport.removePropertyChangeListener(p);
	}

	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}
}
