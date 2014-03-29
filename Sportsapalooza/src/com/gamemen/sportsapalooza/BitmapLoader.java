package com.gamemen.sportsapalooza;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.DisplayMetrics;

public class BitmapLoader {
	private static Options bitmapOptions;
	
	public static Bitmap bmpField, bmpEndzone, bmpScoreBar;
	public static Bitmap bmpBtnAboutUp, bmpBtnAboutDown, bmpBtnBackUp, bmpBtnBackDown, bmpBtnPauseUp, bmpBtnPauseDown, bmpBtnPlayUp, bmpBtnPlayDown, bmpBtnSoundUp, bmpBtnSoundDown;
	public static Bitmap bmpLeftDude, bmpRightDude, bmpDugout, bmpDetonatorUp, bmpDetonatorDown, bmpExplosion;
	public static Bitmap bmpFootball, bmpZebraball, bmpBirdyball;
	
	public static Bitmap bmpScoreTypes[];
	
	static void loadResources(DisplayMetrics metrics, Resources res) {
		bitmapOptions = new Options();
		bitmapOptions.inDensity = metrics.densityDpi;
		
		// GameView
		bmpField = BitmapFactory.decodeResource(res, R.drawable.field, bitmapOptions);
		bmpScoreBar = BitmapFactory.decodeResource(res, R.drawable.score, bitmapOptions);
		
		// Buttons
		bmpBtnAboutUp = BitmapFactory.decodeResource(res, R.drawable.btn_about_up, bitmapOptions);
		bmpBtnAboutDown = BitmapFactory.decodeResource(res, R.drawable.btn_about_down, bitmapOptions);
		bmpBtnBackUp = BitmapFactory.decodeResource(res, R.drawable.btn_back_up, bitmapOptions);
		bmpBtnBackDown = BitmapFactory.decodeResource(res, R.drawable.btn_back_down, bitmapOptions);
		bmpBtnPauseUp = BitmapFactory.decodeResource(res, R.drawable.btn_pause_up, bitmapOptions);
		bmpBtnPauseDown = BitmapFactory.decodeResource(res, R.drawable.btn_pause_down, bitmapOptions);
		bmpBtnPlayUp = BitmapFactory.decodeResource(res, R.drawable.btn_play_up, bitmapOptions);
		bmpBtnPlayDown = BitmapFactory.decodeResource(res, R.drawable.btn_play_down, bitmapOptions);
		bmpBtnSoundUp = BitmapFactory.decodeResource(res, R.drawable.btn_sound_up, bitmapOptions);
		bmpBtnSoundDown = BitmapFactory.decodeResource(res, R.drawable.btn_sound_down, bitmapOptions);
		
		// Endzone
		bmpEndzone = BitmapFactory.decodeResource(res, R.drawable.endzone, bitmapOptions);
		bmpLeftDude = BitmapFactory.decodeResource(res, R.drawable.leftdude, bitmapOptions);
		bmpRightDude = BitmapFactory.decodeResource(res, R.drawable.rightdude, bitmapOptions);
		bmpDetonatorUp = BitmapFactory.decodeResource(res, R.drawable.detonator_up, bitmapOptions);
		bmpDetonatorDown = BitmapFactory.decodeResource(res, R.drawable.detonator_down, bitmapOptions);
		
		// Dugout
		bmpDugout = BitmapFactory.decodeResource(res, R.drawable.dugout, bitmapOptions);
		
		// Football
		bmpFootball = BitmapFactory.decodeResource(res, R.drawable.ball_football, bitmapOptions);
		bmpZebraball = BitmapFactory.decodeResource(res, R.drawable.ball_zebra, bitmapOptions);
		bmpBirdyball = BitmapFactory.decodeResource(res, R.drawable.ball_birdy, bitmapOptions);
		
		// Explosion
		bmpExplosion = BitmapFactory.decodeResource(res, R.drawable.explosion, bitmapOptions);
		
		// Score types
		bmpScoreTypes = new Bitmap[6];
		bmpScoreTypes[0] = BitmapFactory.decodeResource(res, R.drawable.btn_pause_down, bitmapOptions);
		bmpScoreTypes[1] = BitmapFactory.decodeResource(res, R.drawable.btn_pause_down, bitmapOptions);
		bmpScoreTypes[2] = BitmapFactory.decodeResource(res, R.drawable.btn_pause_down, bitmapOptions);
		bmpScoreTypes[3] = BitmapFactory.decodeResource(res, R.drawable.btn_pause_down, bitmapOptions);
		bmpScoreTypes[4] = BitmapFactory.decodeResource(res, R.drawable.btn_pause_down, bitmapOptions);
		bmpScoreTypes[5] = BitmapFactory.decodeResource(res, R.drawable.btn_pause_down, bitmapOptions);
	}
}
