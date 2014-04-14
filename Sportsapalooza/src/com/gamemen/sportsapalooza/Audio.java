package com.gamemen.sportsapalooza;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class Audio {
//	public static MediaPlayer countdown, teeoff, button, crowd, bounce, explosion;
	private static SoundPool pool;
	private static int volume = 100;
	public static int countdown, teeoff, button, crowd, bounce, explosion;
	
	public static void loadAudio(Context context) {
//		countdown = MediaPlayer.create(context, R.raw.countdown);
//		teeoff = MediaPlayer.create(context, R.raw.teeoff);
//		button = MediaPlayer.create(context, R.raw.button);
//		crowd = MediaPlayer.create(context, R.raw.crowd);
//		bounce = MediaPlayer.create(context, R.raw.bounce);
//		explosion = MediaPlayer.create(context, R.raw.explosion);
		
		pool = new SoundPool(6, AudioManager.STREAM_MUSIC, 0);
		
		countdown = pool.load(context, R.raw.countdown, 1);
		teeoff = pool.load(context, R.raw.teeoff, 1);
		button = pool.load(context, R.raw.button, 1);
		crowd = pool.load(context, R.raw.crowd, 1);
		bounce = pool.load(context, R.raw.bounce, 1);
		explosion = pool.load(context, R.raw.explosion, 1);
	}
	
	public static void release() {
//		MediaPlayer[] players = { countdown, teeoff, button, crowd, bounce, explosion };
//		
//		for (int i = 0; i < players.length; ++i) {
//			players[i].reset();
//			players[i].release();
//			players[i] = null;
//		}
		
		pool.release();
	}
	
	public static void play(int soundID) {
//		if (player.isPlaying()) {
//			MediaPlayer copy = new MediaPlayer(player);
//		}
//		else {
//			player.start();
//		}
		
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

}
