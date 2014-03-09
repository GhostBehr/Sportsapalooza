package com.gamemen.sportsapalooza;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

public class Football extends Sprite {

	private ForceMotion physics;
	
	public Football(GameView gameView, Bitmap bmp, PointF pos) {
		super(gameView, bmp, pos);
	}
	
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
	
	public void update(float deltaTime) {
		//pos = physics.update(deltaTime, pos);
	}
	
	public void addConstForce(PointF force) {
		physics.addConstForce(force);
	}
	
	public void addImpulseForce(PointF force) {
		physics.addImpulseForce(force);
	}
}
