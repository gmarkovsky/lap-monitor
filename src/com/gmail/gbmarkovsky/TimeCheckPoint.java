package com.gmail.gbmarkovsky;

/**
 * Контрольная точка по времени
 * @author george
 *
 */
public class TimeCheckPoint {
	/**
	 * Значение контрольной точки
	 */
	private long time;
	
	/**
	 * Признак того, что точка является одинарной. Если равен
	 * {@code true}, то точка срабатывает один раз. Если равен 
	 * {@code false}, то точка срабатывает каждые пройденные
	 * {@code distance} метров дистанции.
	 */
	public boolean single;
	
	public TimeCheckPoint(long time) {
		this.time = time;
		this.single = true;
	}

	public TimeCheckPoint(long time, boolean single) {
		this.time = time;
		this.single = single;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public boolean isSingle() {
		return single;
	}

	public void setSingle(boolean single) {
		this.single = single;
	}
}
