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
	
	/**
	 * Количество прохождений контрольной точки	
	 */
	public int count = 0;
	
	public TimeCheckPoint(long time) {
		this.time = time;
		this.single = true;
	}

	public TimeCheckPoint(long time, boolean single) {
		this.time = time;
		this.single = single;
	}

	public long getTime() {
		if (isSingle()) {
			return time;
		}
		return time * (count + 1);
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
	
	public void passed() {
		if (!isSingle()) {
			count++;
		}
	}
	
	@Override
	public String toString() {
		int seconds = (int) (time / 1000);
		int minutes = seconds / 60;
		seconds = seconds % 60;
		String s;
		if (isSingle()) {
			s = "";
		} else {
			s = " *";
		}
		return Integer.toString(minutes) + " m:" + Integer.toString(seconds) + " s" + s;
	}
}
