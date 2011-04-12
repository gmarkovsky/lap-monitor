package com.gmail.gbmarkovsky.lm.gui;

import android.graphics.Paint;
import android.graphics.Point;
import android.location.Location;
import android.util.Pair;

import com.gmail.gbmarkovsky.lm.distance.Trace;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class MyLineOverlay extends Overlay {
	private Trace trace;

	public MyLineOverlay(Trace trace) {
		super();
		this.trace = trace;
	}
	
	public void draw(android.graphics.Canvas canvas,
            MapView mapView,
            boolean shadow) {
		super.draw(canvas, mapView, shadow);
		float[] pts = new float[4 * trace.getTrace().size()];
		Projection projection = mapView.getProjection();
		int i = 0;
		for (Pair<Location, Long> pnt: trace.getTrace()) {
            GeoPoint gp = new GeoPoint(
                    (int) (pnt.first.getLatitude() * 1E6), 
                    (int) (pnt.first.getLongitude() * 1E6));
            Point p = new Point();
			projection.toPixels(gp, p);
			pts[i] = p.x;
			pts[i + 1] = p.y;
			i += 2;
		}
		for (int j = 0; j < pts.length/2 - 2; j++) {
			canvas.drawLine(pts[j], pts[j+1], pts[j+2], pts[j+3], new Paint());
		}
		
	}
}
