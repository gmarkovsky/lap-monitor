package com.gmail.gbmarkovsky.lm.gmaps;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.location.Location;
import android.util.Pair;

import com.gmail.gbmarkovsky.lm.distance.Trace;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

public class FusionTableLoader {

	public static void writeTrace(Trace trace, String fileName) {
		FusionTableAdapter fusionTableAdapter = null;
		try {
			fusionTableAdapter = new FusionTableAdapter("test.george.mail@gmail.com", "vfhrjdcrbq");
		} catch (AuthenticationException e1) {
			e1.printStackTrace();
		}
		String tableId = null;
		try {
			tableId = fusionTableAdapter.createQuery("CREATE TABLE '" + fileName +
					"' (description:STRING, name:STRING, geometry:LOCATION)");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		try {
			Pair<Location, Long> point = trace.getStart();
			
			Location location = point.first;
			double lat = location.getLatitude();
			double lon = location.getLongitude();
			double alt = location.getAltitude();
			Calendar time = new GregorianCalendar();
			time.setTimeInMillis(point.second);
			String timeString = String.format("%1$tH:%1$tM:%1$tS", time);
			
			fusionTableAdapter.insertQuery("INSERT INTO '"+tableId+
					"' (description, name, geometry) VALUES ('"+"<div dir=\"ltr\">Это точка старта. Время старта "+timeString+"</div>"+"','Start','"+	      "<Point>" +
			        "<coordinates>" + lon + "," + lat + "," + alt + "</coordinates>" +
				      "</Point>" +"')");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		try {
			Pair<Location, Long> point = trace.getFinish();
			
			Location location = point.first;
			double lat = location.getLatitude();
			double lon = location.getLongitude();
			double alt = location.getAltitude();
			Calendar time = new GregorianCalendar();
			time.setTimeInMillis(point.second);
			String timeString = String.format("%1$tH:%1$tM:%1$tS", time);
			
			fusionTableAdapter.insertQuery("INSERT INTO '"+tableId+
					"' (description, name, geometry) VALUES ('"+"<div dir=\"ltr\">Это точка старта. Время старта "+timeString+"</div>"+"','Start','"+	      "<Point>" +
			        "<coordinates>" + lon + "," + lat + "," + alt + "</coordinates>" +
				      "</Point>" +"')");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		try {
			Pair<Location, Long> point = trace.getFinish();
			
			Location location = point.first;
			double lat = location.getLatitude();
			double lon = location.getLongitude();
			double alt = location.getAltitude();
			Calendar time = new GregorianCalendar();
			time.setTimeInMillis(point.second);
			String timeString = String.format("%1$tH:%1$tM:%1$tS", time);
			
			String text = new String("");
			for (Pair<Location, Long> pnt: trace.getTrace()) {
				location = pnt.first;
				lat = location.getLatitude();
				lon = location.getLongitude();
				alt = location.getAltitude();
				text += lon + "," + lat + "," + alt + " ";
			}
			
			
			fusionTableAdapter.insertQuery("INSERT INTO '"+tableId+
					"' (description, name, geometry) VALUES ('"+"<div dir=\"ltr\">Это точка старта. Время старта "+timeString+"</div>"+"','Start','"+	      "<LineString>" +
			        "<coordinates>" + text + "</coordinates>" +
				      "</LineString>" +"')");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
}
