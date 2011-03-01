package com.gmail.gbmarkovsky;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Хранит в себе набор контрольных точек и управляет их жизненным циклом.
 * 
 * @author george
 *
 */
public class CheckPointsManager {
	
	/**
	 * Набор для хранения контрольных точек по времени
	 */
	private Collection<TimeCheckPoint> times;
	
	/**
	 * Набор для хранения контрольных точек по расстоянию
	 */
	private Collection<DistanceCheckPoint> distances;
	
	private final PropertyChangeSupport propertyChangeSupport;
	
	private static CheckPointsManager instance;
	
	private CheckPointsManager() {
		times = new ArrayList<TimeCheckPoint>();
		distances = new ArrayList<DistanceCheckPoint>();
		propertyChangeSupport = new PropertyChangeSupport(this);
	}
	
	public static void create() {
		instance = new CheckPointsManager();
	}
	
	public static CheckPointsManager getInstance() {
		return instance;
	}
	
	/**
	 * Запрос на создание контрольной точки по времени
	 */
	public void createTimeCheckPoint(long time, boolean single) {
		times.add(new TimeCheckPoint(time, single));
	}
	
	/**
	 * Запрос на создание контрольной точки по расстоянию
	 */
	public void createDistanceCheckPoint(double distance, boolean single) {
		DistanceCheckPoint newValue = new DistanceCheckPoint(distance, single);
		distances.add(newValue);
		firePropertyChange(ADDED_DISTANCE_CHECK_POINT, null, newValue);
	}
	
	public Collection<DistanceCheckPoint> getDistanceCheckPoints() {
		return distances;
	}
	
	public Collection<TimeCheckPoint> getTimeCheckPoints() {
		return times;
	}
	
	/**
	 * Вызывается классом, управляющим временем
	 * @param time
	 */
	public void checkTime(long time) {
		for(TimeCheckPoint t: times) {
			if (t.getTime() - time >= 0) {
				if (t.isSingle()) {
					times.remove(t);
				}
				// Тут будет вызов метода класса-оповестителя о прохождении контрольной точки
			}
		}
		
	}
	
	/**
	 * Вызывается классом, управляющим дистанцией
	 * @param distance
	 */
	public void checkDistance(double distance) {
		for(DistanceCheckPoint d: distances) {
			if (d.getDistance() - distance >= 0) {
				if (d.isSingle()) {
					distances.remove(d);
				}
				// Тут будет вызов метода класса-оповестителя о прохождении контрольной точки
			}
		}
	}
	
	public void removeCheckPoint(DistanceCheckPoint checkPoint) {
		distances.remove(checkPoint);
	}
	
	public void removeCheckPoint(TimeCheckPoint checkPoint) {
		times.remove(checkPoint);
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
	
	public static final String ADDED_DISTANCE_CHECK_POINT = "addedDistanceCheckPoint";
	public static final String ADDED_TIME_CHECK_POINT = "addedTimeCheckPoint";
}
