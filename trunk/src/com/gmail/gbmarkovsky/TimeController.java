package com.gmail.gbmarkovsky;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Управляет работой со временем. Сообщает слушателям о изменении вермени.
 * @author george
 *
 */
public class TimeController {
	private long time = -1000;
	ScheduledThreadPoolExecutor timer;
	Runnable runtask;
	private static TimeController instance;
	private final PropertyChangeSupport propertyChangeSupport;
	
	private TimeController() {
		propertyChangeSupport = new PropertyChangeSupport(this);
		timer = new ScheduledThreadPoolExecutor(5);
		runtask = new Runnable() {
			
			@Override
			public void run() {
				time += 1000;
				firePropertyChange(TICK, time-1000, time);
			}
		};
	}
	
	public static void create() {
		instance = new TimeController();
	}
	
	public static TimeController getInstance() {
		return instance;
	}
	
	public void start() {
		timer.scheduleAtFixedRate(runtask, 0, 1000, TimeUnit.MILLISECONDS);
	}
	
	public void tick() {
		time += 1000;
		firePropertyChange(TICK, time-1000, time);
	}
	
	public void reset() {
		long oldTime = time;
		timer.remove(runtask);
		firePropertyChange(TICK, oldTime, time);
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
	
	public static final String TICK = "tick";
}
