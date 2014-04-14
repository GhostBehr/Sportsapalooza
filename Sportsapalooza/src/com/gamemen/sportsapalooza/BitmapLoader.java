package com.gamemen.sportsapalooza;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.DisplayMetrics;

public class BitmapLoader {
	private static Options bitmapOptions;
	
	public static Bitmap bmpTitle, bmpField, bmpEndzone, bmpScoreBar;
	public static Bitmap bmpAbout, bmpPaused, bmpLeftWins, bmpRightWins, bmpTie;
	public static Bitmap bmpBtnAboutUp, bmpBtnAboutDown, bmpBtnBackUp, bmpBtnBackDown, bmpBtnPauseUp, bmpBtnPauseDown, bmpBtnPlayUp, bmpBtnPlayDown;
	public static Bitmap bmpBtnSoundUp, bmpBtnSoundDown, bmpBtnSoundOffUp, bmpBtnSoundOffDown;
	public static Bitmap bmpLeftDude, bmpRightDude, bmpDugout, bmpDetonatorUp, bmpDetonatorDown;
	public static Bitmap bmpFootball, bmpZebraball, bmpBirdyball;
	public static Bitmap bmpTeeOff[];
	public static Bitmap bmpExplosion[];
	
	public static Bitmap bmpScoreTypes[];
	
	static void loadResources(DisplayMetrics metrics, Resources res) {
		bitmapOptions = new Options();
		bitmapOptions.inDensity = metrics.densityDpi;
		
		// GameView
		bmpTitle = BitmapFactory.decodeResource(res, R.drawable.title, bitmapOptions);
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
		bmpBtnSoundOffUp = BitmapFactory.decodeResource(res, R.drawable.btn_soundoff_up, bitmapOptions);
		bmpBtnSoundOffDown = BitmapFactory.decodeResource(res, R.drawable.btn_soundoff_down, bitmapOptions);
		
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
		bmpExplosion = new Bitmap[4];
		bmpExplosion[0] = BitmapFactory.decodeResource(res, R.drawable.explosion_3, bitmapOptions);
		bmpExplosion[1] = BitmapFactory.decodeResource(res, R.drawable.explosion_1, bitmapOptions);
		bmpExplosion[2] = BitmapFactory.decodeResource(res, R.drawable.explosion_2, bitmapOptions);
		bmpExplosion[3] = bmpExplosion[0];
		
		// Score types
		bmpScoreTypes = new Bitmap[Endzone.SCORE_TYPES.length];
		bmpScoreTypes[0] = BitmapFactory.decodeResource(res, R.drawable.scored, bitmapOptions);
		bmpScoreTypes[1] = BitmapFactory.decodeResource(res, R.drawable.scored, bitmapOptions);
		bmpScoreTypes[2] = BitmapFactory.decodeResource(res, R.drawable.scored, bitmapOptions);
		bmpScoreTypes[3] = BitmapFactory.decodeResource(res, R.drawable.scored, bitmapOptions);
		bmpScoreTypes[4] = BitmapFactory.decodeResource(res, R.drawable.scored, bitmapOptions);
		bmpScoreTypes[5] = BitmapFactory.decodeResource(res, R.drawable.scored, bitmapOptions);
		bmpScoreTypes[6] = BitmapFactory.decodeResource(res, R.drawable.scored, bitmapOptions);
		
		// Random screen stuff
		bmpAbout = BitmapFactory.decodeResource(res, R.drawable.about, bitmapOptions);
		bmpPaused = BitmapFactory.decodeResource(res, R.drawable.paused, bitmapOptions);
		bmpLeftWins = BitmapFactory.decodeResource(res, R.drawable.left_wins, bitmapOptions);
		bmpRightWins = BitmapFactory.decodeResource(res, R.drawable.right_wins, bitmapOptions);
		bmpTie = BitmapFactory.decodeResource(res, R.drawable.tie, bitmapOptions);
		
		// Tee-Off
		bmpTeeOff = new Bitmap[4];
		bmpTeeOff[0] = BitmapFactory.decodeResource(res, R.drawable.three, bitmapOptions);
		bmpTeeOff[1] = BitmapFactory.decodeResource(res, R.drawable.two, bitmapOptions);
		bmpTeeOff[2] = BitmapFactory.decodeResource(res, R.drawable.one, bitmapOptions);
		bmpTeeOff[3] = BitmapFactory.decodeResource(res, R.drawable.teeoff, bitmapOptions);
	}
}
