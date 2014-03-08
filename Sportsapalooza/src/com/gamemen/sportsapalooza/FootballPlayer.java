package com.gamemen.sportsapalooza;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;

public class FootballPlayer extends Sprite {
	
	SimpleMotion motion;
	Color color;
	
	public FootballPlayer(GameView gameView, Bitmap bmp, PointF pos, Color color) {
		super(gameView, bmp, pos);
		
		// wait maybe we should just have two different player sprites for each time uhh why did I do this
	}
	
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
	
	public void update(float deltaTime) {
		pos = motion.update(deltaTime, pos);
	}
}
