package com.gmail.gbmarkovsky.lm.controllers;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.gmail.gbmarkovsky.lm.distance.Trace;

/**
 * Управляет работой с пройденной дистанцией.
 * @author george
 *
 */
public class DistanceController {	
	private static DistanceController instance;
	private final PropertyChangeSupport propertyChangeSupport;
	
	Trace trace = new Trace();
	
	private double distance;
	
	private Location location;
	private long time;
	private LocationManager locationManager;
	
	private boolean logging;
	
	private DistanceController(Activity activity) {
		propertyChangeSupport = new PropertyChangeSupport(this);
		locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
		
		LocationListener locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				Location oldLocation = DistanceController.this.location;
				DistanceController.this.location = location;
				DistanceController.this.time = System.currentTimeMillis();
				//DistanceController.this.time = location.getTime();
				if (logging) {
					trace.addPoint(DistanceController.this.location, DistanceController.this.time);
					double oldDistance = distance;
					distance += calculateDistance(oldLocation, location);
					firePropertyChange(DISTANCE_CHANGED, oldDistance, distance);
				}
					firePropertyChange(LOCATION_CHANGED, oldLocation, location);
			}
			
			public void onStatusChanged(String provider, int status, Bundle extras) {
				
			}
			
			public void onProviderEnabled(String provider) {
				
			}
			
			public void onProviderDisabled(String provider) {
				
			}
		};
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
	}
	
	public void startLogging() {
		logging = true;
	}
	
	public void resetLogging() {
		trace.reset();
		double oldDistance = distance;
		distance = 0;
		firePropertyChange(DISTANCE_CHANGED, oldDistance, distance);
	}
	
	public void stopLogging() {
		logging = false;
	}

	public static void create(Activity activity) {
		instance = new DistanceController(activity);
	}
	
	public static DistanceController getInstance() {
		return instance;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public Trace getTrace() {
		return trace;
	}
	
	/**
	 * Вычисляет Great Circle Distance между <code>loc1</code> и <code>loc2</code>.
	 * @param loc1
	 * @param loc2
	 * @return
	 */
	private double calculateDistance(Location loc1, Location loc2) {
		double result = 0;
		if (loc1 == null) {
			return result;
		}
		double lat1 = Math.toRadians(loc1.getLatitude());
		double lat2 = Math.toRadians(loc2.getLatitude());
		double lon1 = Math.toRadians(loc1.getLongitude());
		double lon2 = Math.toRadians(loc2.getLongitude());
		
		double delta = 2 * Math.asin(Math.sqrt(
					Math.pow(Math.sin((lat1 - lat2) / 2), 2) +
					Math.cos(lat1) * 
					Math.cos(lat2) *
					Math.pow(Math.sin((lon1 - lon2) / 2), 2)
		));

		result = delta * C_RADIUS_EARTH_M;
		return result;
	}
	
	public double getDistance() {
		return distance;
	}
	
	public void addLocationListener(LocationListener locationListener) {
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
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
	
	public static final String LOCATION_CHANGED = "locationChanged";
	public static final String DISTANCE_CHANGED = "distanceChanged";
	
	private static final double C_RADIUS_EARTH_M = 6370973.27862;
}
