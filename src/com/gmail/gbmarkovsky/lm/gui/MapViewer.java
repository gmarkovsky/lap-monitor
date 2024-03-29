package com.gmail.gbmarkovsky.lm.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import android.location.Location;
import android.os.Bundle;

import com.gmail.gbmarkovsky.R;
import com.gmail.gbmarkovsky.lm.controllers.DistanceController;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class MapViewer extends MapActivity implements PropertyChangeListener {    
    private MapView mapView;
    private MapController mapController;
    private MyLocationOverlay myLoc;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_layout);
        mapView = (MapView) findViewById(R.id.googleMapView);
        mapView.setBuiltInZoomControls(true);
        mapView.setSatellite(true);
        mapController = mapView.getController();
        GeoPoint p = new GeoPoint(
                (int) (59.859622 * 1E6), 
                (int) (30.952177 * 1E6));
        mapController.animateTo(p);
        mapController.setZoom(16);                
        mapView.invalidate();
        myLoc = new MyLocationOverlay(this, mapView);
		mapView.getOverlays().add(myLoc);
		myLoc.enableMyLocation();
        DistanceController.getInstance().addPropertyChangeListener(this);
        //mapView.getOverlays().add(new MyLineOverlay(DistanceController.getInstance().getTrace()));
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }        

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getPropertyName().equals(DistanceController.LOCATION_CHANGED)) {
			Location loc = (Location) event.getNewValue();

            if (loc != null) {
//    			Location location = (Location) event.getNewValue();
//    			String latitudeString = "";
//    			if (location.getLatitude() > 0) {
//    				latitudeString = Location.convert(location.getLatitude(), Location.FORMAT_SECONDS) + " C";
//    			} else {
//    				latitudeString = Location.convert(-location.getLatitude(), Location.FORMAT_SECONDS) + " Ю";
//    			}
//    			String longtudeString = "";
//    			if (location.getLatitude() > 0) {
//    				longtudeString = Location.convert(location.getLongitude(), Location.FORMAT_SECONDS) + " В";
//    			} else {
//    				longtudeString = Location.convert(-location.getLongitude(), Location.FORMAT_SECONDS) + " З";
//    			}
//            	
//                Toast.makeText(getBaseContext(), 
//                		latitudeString + "  " + longtudeString, 
//                        Toast.LENGTH_SHORT).show();
                
                GeoPoint p = new GeoPoint(
                        (int) (loc.getLatitude() * 1E6), 
                        (int) (loc.getLongitude() * 1E6));
                mapController.animateTo(p);
                mapController.setZoom(16);                
                mapView.invalidate();
            }
		}
	}    
}
