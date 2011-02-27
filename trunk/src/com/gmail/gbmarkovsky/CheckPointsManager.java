package com.gmail.gbmarkovsky;

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
	
	private static CheckPointsManager instance;
	
	private CheckPointsManager() {
		times = new ArrayList<TimeCheckPoint>();
		distances = new ArrayList<DistanceCheckPoint>();
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
		 distances.add(new DistanceCheckPoint(distance, single));
	 }
	
	/**
	 * Вызывается классом, управляющим временем
	 * @param time
	 */
	public void checkTime(long time) {
		
		// Тут будет вызов метода класса-оповестителя о прохождении контрольной точки
	}
	
	/**
	 * Вызывается классом, управляющим дистанцией
	 * @param distance
	 */
	public void checkDistance(double distance) {
		
		// Тут будет вызов метода класса-оповестителя о прохождении контрольной точки
	}
}
