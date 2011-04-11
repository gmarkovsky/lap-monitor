package com.gmail.gbmarkovsky.lm.distance;

import java.util.ArrayList;
import java.util.List;

import android.location.Location;
import android.util.Pair;

public class Trace {
	private List<Pair<Location, Long>> trace = new ArrayList<Pair<Location, Long>>();
	private List<Pair<Location, Long>> timeChecks = new ArrayList<Pair<Location, Long>>();
	private Pair<Location, Long> start;
	private Pair<Location, Long> finish;
	
	public void addPoint(Location location, Long time) {
		Pair<Location, Long> point = new Pair<Location, Long>(location, time);
		if (trace.isEmpty()) {
			start = point;
		}
		trace.add(point);
		finish = point;
	}
	
	public void fixTimeCheckPoint(long l) {
		timeChecks.add(finish);
	}
	
	public boolean isEmpty() {
		return trace.isEmpty();
	}
	
	public Pair<Location, Long> getStart() {
		return start;
	}

	public List<Pair<Location, Long>> getTrace() {
		return trace;
	}

	public Pair<Location, Long> getFinish() {
		return finish;
	}
	
	public List<Pair<Location, Long>> getTimeChecks() {
		return timeChecks;
	}

	public void reset() {
		start = null;
		finish = null;
		trace.clear();
		timeChecks.clear();
	}
}
