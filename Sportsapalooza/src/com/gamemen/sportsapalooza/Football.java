package com.gamemen.sportsapalooza;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

public class Football extends Sprite {

	private ForceMotion physics;
	RectF hitbox;
	
	public Football(GameView gameView, Bitmap bmp) {
		super(gameView, bmp);
	}

	public Football(GameView gameView, Bitmap bmp, PointF pos) {
		super(gameView, bmp, pos);
	}
	
	public Football(GameView gameView, Bitmap bmp, PointF pos, float rot) {
		super(gameView, bmp, pos, rot);
	}
	
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
	
	public void update(float deltaTime) {
		physics.update(deltaTime, pos);
	}
}
