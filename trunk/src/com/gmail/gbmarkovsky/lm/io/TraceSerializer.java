package com.gmail.gbmarkovsky.lm.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.xmlpull.v1.XmlSerializer;

import android.location.Location;
import android.os.Environment;
import android.text.format.Time;
import android.util.Log;
import android.util.Pair;
import android.util.Xml;

import com.gmail.gbmarkovsky.lm.distance.Trace;

public class TraceSerializer {
	
	public static void writeTrace(Trace trace) {
	       File newxmlfile = new File(Environment.getExternalStorageDirectory()+"/new.kml");
	        try{
	        	newxmlfile.createNewFile();
	        }catch(IOException e) {
	        	Log.e("IOException", "exception in createNewFile() method");
	        }
	        FileOutputStream out = null;       	
	        try{
	        	out = new FileOutputStream(newxmlfile);
	        }catch(FileNotFoundException e) {
	        	Log.e("FileNotFoundException", "can't create FileOutputStream");
	        }
	        XmlSerializer serializer = Xml.newSerializer();
	        try {
				serializer.setOutput(out, "UTF-8");
				serializer.startDocument(null, Boolean.valueOf(true)); 
				serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true); 

				
				serializer.startTag(null, "kml");
				serializer.attribute(null, "xmlns", "http://www.opengis.net/kml/2.2");
				serializer.attribute(null, "xmlns:gx", "http://www.google.com/kml/ext/2.2");
				serializer.attribute(null, "xmlns:kml", "http://www.opengis.net/kml/2.2");
				serializer.attribute(null, "xmlns:atom", "http://www.w3.org/2005/Atom");
				serializer.startTag(null, "Document");
				writeStyles(serializer);
				
				Pair<Location, Long> point= trace.getStart();
				Location location = point.first;
				double lat = location.getLatitude();
				double lon = location.getLongitude();
				double alt = location.getAltitude();
				Time time = new Time();
				time.set(point.second);
				String timeString = time.hour + ":" + time.minute + ":" + time.second;
				serializer.startTag(null, "Placemark");
				serializer.startTag(null, "name");
				serializer.text("Start");
				serializer.endTag(null, "name");
				serializer.startTag(null, "description");
				serializer.cdsect("<div dir=\"ltr\">Это точка старта. Время старта "+timeString+"</div>");
				serializer.endTag(null, "description");
				serializer.startTag(null, "styleUrl");
				serializer.text("#startMarker");
				serializer.endTag(null, "styleUrl");
				serializer.startTag(null, "Point");
				serializer.startTag(null, "coordinates");
				serializer.text(lon + "," + lat + "," + alt + " ");
				serializer.endTag(null, "coordinates");
				serializer.endTag(null, "Point");
				serializer.endTag(null, "Placemark");
				
				point= trace.getFinish();
				location = point.first;
				lat = location.getLatitude();
				lon = location.getLongitude();
				alt = location.getAltitude();
				time.set(point.second);
				timeString = time.hour + ":" + time.minute + ":" + time.second;
				
				serializer.startTag(null, "Placemark");
				serializer.startTag(null, "name");
				serializer.text("Start");
				serializer.endTag(null, "name");
				serializer.startTag(null, "description");
				serializer.cdsect("<div dir=\"ltr\">Это точка финиша. Время финиша "+timeString+"</div>");
				serializer.endTag(null, "description");
				serializer.startTag(null, "styleUrl");
				serializer.text("#finishMarker");
				serializer.endTag(null, "styleUrl");
				serializer.startTag(null, "Point");
				serializer.startTag(null, "coordinates");

				serializer.text(lon + "," + lat + "," + alt + " ");
				serializer.endTag(null, "coordinates");
				serializer.endTag(null, "Point");
				serializer.endTag(null, "Placemark");
				
				serializer.startTag(null, "Placemark");
				
				serializer.startTag(null, "name");
				serializer.text("Линия 1");
				serializer.endTag(null, "name");
				
				serializer.startTag(null, "LineString");
				
				serializer.startTag(null, "tessellate");
				serializer.text("1");
				serializer.endTag(null, "tessellate");
				
				serializer.startTag(null, "coordinates");
				
				String text = new String("");
				for (Pair<Location, Long> pnt: trace.getTrace()) {
					location = pnt.first;
					lat = location.getLatitude();
					lon = location.getLongitude();
					alt = location.getAltitude();
					text += lon + "," + lat + "," + alt + " ";
				}
				
				serializer.text(text);
				serializer.endTag(null, "coordinates");
				
				serializer.endTag(null, "LineString");
				
				serializer.endTag(null, "Placemark");
				

				text = new String("");
				for (Pair<Location, Long> pnt: trace.getTimeChecks()) {
					location = pnt.first;
					lat = location.getLatitude();
					lon = location.getLongitude();
					alt = location.getAltitude();
					time = new Time();
					time.set(point.second);
					timeString = time.hour + ":" + time.minute + ":" + time.second;
					serializer.startTag(null, "Placemark");
					serializer.startTag(null, "name");
					serializer.text("TimeCheck "+timeString);
					serializer.endTag(null, "name");
					serializer.startTag(null, "description");
					serializer.cdsect("<div dir=\"ltr\">Контрольная точка по времени"+timeString+"</div>");
					serializer.endTag(null, "description");
					serializer.startTag(null, "styleUrl");
					serializer.text("#timeCheckMarker");
					serializer.endTag(null, "styleUrl");
					serializer.startTag(null, "Point");
					serializer.startTag(null, "coordinates");
					serializer.text(lon + "," + lat + "," + alt + " ");
					serializer.endTag(null, "coordinates");
					serializer.endTag(null, "Point");
					serializer.endTag(null, "Placemark");
				}
				
				serializer.endTag(null, "Document");
				serializer.endTag(null, "kml");

				serializer.endDocument();
				serializer.flush();
				out.close();
				
			} catch (Exception e) {
				Log.e("Exception","error occurred while creating xml file");
			}
	}
	
	private static void writeStyles(XmlSerializer serializer) {
		try {
			serializer.startTag(null, "Style");
			serializer.attribute(null, "id", "startMarker");
			serializer.startTag(null, "IconStyle");
			serializer.startTag(null, "Icon");
			serializer.startTag(null, "href");
			serializer.text("http://maps.google.com/mapfiles/ms/icons/green-dot.png");
			serializer.endTag(null, "href");
			serializer.endTag(null, "Icon");
			serializer.endTag(null, "IconStyle");
			serializer.endTag(null, "Style");
			
			serializer.startTag(null, "Style");
			serializer.attribute(null, "id", "finishMarker");
			serializer.startTag(null, "IconStyle");
			serializer.startTag(null, "Icon");
			serializer.startTag(null, "href");
			serializer.text("http://maps.google.com/mapfiles/ms/icons/red-dot.png");
			serializer.endTag(null, "href");
			serializer.endTag(null, "Icon");
			serializer.endTag(null, "IconStyle");
			serializer.endTag(null, "Style");
			
			serializer.startTag(null, "Style");
			serializer.attribute(null, "id", "timeCheckMarker");
			serializer.startTag(null, "IconStyle");
			serializer.startTag(null, "Icon");
			serializer.startTag(null, "href");
			serializer.text("http://www.google.com/mapfiles/ms/micons/grn-pushpin.png");
			serializer.endTag(null, "href");
			serializer.endTag(null, "Icon");
			serializer.endTag(null, "IconStyle");
			serializer.endTag(null, "Style");
			
			serializer.startTag(null, "Style");
			serializer.attribute(null, "id", "line");
			serializer.startTag(null, "LineStyle");
			serializer.startTag(null, "color");
			serializer.text("7fff0000");
			serializer.endTag(null, "color");
			serializer.startTag(null, "width");
			serializer.text("5");
			serializer.endTag(null, "width");
			serializer.endTag(null, "LineStyle");
			serializer.endTag(null, "Style");
		} catch (Exception e) {
			Log.e("Exception","error occurred while creating xml file");
		}
	}
	
}
