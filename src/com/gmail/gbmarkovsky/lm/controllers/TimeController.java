package com.gmail.gbmarkovsky.lm.controllers;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Управляет работой со временем. Сообщает слушателям о изменении вермени.
 * @author george
 *
 */
public class TimeController {
	private long time = -1000;
	private static TimeController instance;
	private final PropertyChangeSupport propertyChangeSupport;
	
	private TimeController() {
		propertyChangeSupport = new PropertyChangeSupport(this);
	}
	
	public static void create() {
		instance = new TimeController();
	}
	
	public static TimeController getInstance() {
		return instance;
	}
	
	public void tick() {
		time += 1000;
		firePropertyChange(TICK, time-1000, time);
	}
	
	public void reset() {
		time = 0;
	}
	
	public long getTime() {
		return time;
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
