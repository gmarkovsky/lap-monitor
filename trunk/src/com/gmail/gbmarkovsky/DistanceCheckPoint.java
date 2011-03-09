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
	 * Количество прохождений контрольной точки	
	 */
	public int count = 0;
	
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

	public double getDistance() {
		if (isSingle()) {
			return distance;
		}
		return distance * (count + 1);
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public boolean isSingle() {
		return single;
	}

	public void setSingle(boolean single) {
		this.single = single;
	}
	
	public void passed() {
		if (!isSingle()) {
			count++;
		}
	}
	
	@Override
	public String toString() {
		String s;
		if (isSingle()) {
			s = "";
		} else {
			s = " *";
		}
		return Double.toString(distance) + " m" + s;
	}
}
