package com.gmail.gbmarkovsky;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * Управляет работой с пройденной дистанцией.
 * @author george
 *
 */
public class DistanceController {
	private static DistanceController instance;
	private final PropertyChangeSupport propertyChangeSupport;
	
	private Location location;
	private LocationManager locationManager;
	
	private DistanceController(Activity activity) {
		propertyChangeSupport = new PropertyChangeSupport(this);
		locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
		
		LocationListener locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				Location oldLocation = DistanceController.this.location;
				DistanceController.this.location = location;
				firePropertyChange(LOCATION, oldLocation, location);
			}
			
			public void onStatusChanged(String provider, int status, Bundle extras) {
				
			}
			
			public void onProviderEnabled(String provider) {
				
			}
			
			public void onProviderDisabled(String provider) {
				
			}
		};
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
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
	
	public void addPropertyChangeListener(PropertyChangeListener p) {
		propertyChangeSupport.addPropertyChangeListener(p);
	}

	public void removePropertyChangeListener(PropertyChangeListener p) {
		propertyChangeSupport.removePropertyChangeListener(p);
	}

	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}
	
	public static final String LOCATION = "Location";
}
