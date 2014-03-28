package com.gamemen.sportsapalooza;

import android.graphics.Canvas;
import android.graphics.PointF;

public class Football extends Sprite {

	private ForceMotion physics;
	
	public Football(GameView gameView, PointF pos) {
		super(gameView, BitmapLoader.bmpFootball, pos);
		
		physics = new ForceMotion(1, 100);
	}
	
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
	
	public void update(float deltaTime) {
		pos = physics.update(deltaTime, pos);
	}
	
	public void addConstForce(PointF force) {
		physics.addConstForce(force);
	}
	
	public void addImpulseForce(PointF force) {
		physics.addImpulseForce(force);
	}
	
	public void bounce(boolean alongX, boolean alongY) {
		physics.bounce(alongX, alongY);
	}
	
	public void stop() {
		physics.stop();
	}
	
}
