package com.gmail.gbmarkovsky;

/**
 * Контрольная точка по расстоянию
 * @author george
 *
 */
public class DistanceCheckPoint {
	/**
	 * Значение контрольной точки
	 */
	public double distance;
	
	/**
	 * Признак того, что точка является одинарной. Если равен
	 * {@code true}, то точка срабатывает один раз. Если равен 
	 * {@code false}, то точка срабатывает каждые пройденные
	 * {@code distance} метров дистанции.
	 */
	public boolean single;
	
	/**
	 * Создание контрольной точки
	 * @param distance
	 */
	public DistanceCheckPoint(double distance) {
		this.distance = distance;
		this.single = true;
	}
	
	/**
	 * Создание контрольной точки с заданной кратностью {@code single}
	 * @param distance
	 */
	public DistanceCheckPoint(double distance, boolean single) {
		this.distance = distance;
		this.single = single;
	}
}
