package com.gamemen.sportsapalooza;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.DisplayMetrics;

public class BitmapLoader {
	private static Options bitmapOptions;
	
	public static Bitmap bmpField, bmpEndzone, bmpScoreBar, bmpLeftDude, bmpRightDude, bmpFootball, bmpDugout, bmpDetonatorUp, bmpDetonatorDown, bmpExplosion;
	
	static void loadResources(DisplayMetrics metrics, Resources res) {
		bitmapOptions = new Options();
		bitmapOptions.inDensity = metrics.densityDpi;
		
		// GameView
		bmpField = BitmapFactory.decodeResource(res, R.drawable.field, bitmapOptions);
		bmpScoreBar = BitmapFactory.decodeResource(res, R.drawable.score, bitmapOptions);
		
		// Endzone
		bmpEndzone = BitmapFactory.decodeResource(res, R.drawable.endzone, bitmapOptions);
		bmpLeftDude = BitmapFactory.decodeResource(res, R.drawable.leftdude, bitmapOptions);
		bmpRightDude = BitmapFactory.decodeResource(res, R.drawable.rightdude, bitmapOptions);
		bmpDetonatorUp = BitmapFactory.decodeResource(res, R.drawable.detonator_up, bitmapOptions);
		bmpDetonatorDown = BitmapFactory.decodeResource(res, R.drawable.detonator_down, bitmapOptions);
		
		// Dugout
		bmpDugout = BitmapFactory.decodeResource(res, R.drawable.dugout, bitmapOptions);
		
		// Football
		bmpFootball = BitmapFactory.decodeResource(res, R.drawable.ball, bitmapOptions);
		
		// Explosion
		bmpExplosion = BitmapFactory.decodeResource(res, R.drawable.explosion, bitmapOptions);
	}
}
