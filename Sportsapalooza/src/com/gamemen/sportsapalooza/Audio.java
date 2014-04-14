package com.gamemen.sportsapalooza;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class Audio {
	private static SoundPool pool;
	private static int volume = 100;
	public static int countdown, teeoff, button, crowd, bounce, explosion;
	
	public static void loadAudio(Context context) {
		pool = new SoundPool(6, AudioManager.STREAM_MUSIC, 0);
		
		countdown = pool.load(context, R.raw.countdown, 1);
		teeoff = pool.load(context, R.raw.teeoff, 1);
		button = pool.load(context, R.raw.button, 1);
		crowd = pool.load(context, R.raw.crowd, 1);
		bounce = pool.load(context, R.raw.bounce, 1);
		explosion = pool.load(context, R.raw.explosion, 1);
	}
	
	public static void release() {
		pool.release();
	}
	
	public static void play(int soundID) {
		pool.play(soundID, volume, volume, 1, 0, 1);
	}
	
	public static void toggleSound() {
		if (volume == 100) {
			volume = 0;
		}
		else {
			volume = 100;
		}
	}
	
	public static boolean getSound() {
		return volume == 0 ? false : true;
	}

}

	