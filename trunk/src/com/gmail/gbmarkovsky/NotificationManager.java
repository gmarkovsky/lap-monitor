package com.gmail.gbmarkovsky;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;

public class NotificationManager {

	private static NotificationManager instance;
	private LapMonitor mainFrame;
	
	private NotificationManager(LapMonitor mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	public static void create(LapMonitor mainFrame) {
		instance = new NotificationManager(mainFrame);
	}
	
	public static NotificationManager getInstance() {
		return instance;
	}
	
	public void notifyTime(TimeCheckPoint checkPoint) {
		AlertDialog.Builder builder = new AlertDialog.Builder(mainFrame);
		builder.setMessage("Пройдена контрольная точка по времени " + checkPoint)
		       .setCancelable(false)
		       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
		final AlertDialog alert = builder.create();
		ScheduledThreadPoolExecutor se = new ScheduledThreadPoolExecutor(5);
		Runnable runtask = new Runnable() {
			

			public void run() {
				alert.cancel();
			}
		};
		MediaPlayer mp = MediaPlayer.create(mainFrame, R.raw.mavrik);
	    mp.start();
	    se.schedule(runtask, 10000, TimeUnit.MILLISECONDS);
		alert.show();
	}
}
