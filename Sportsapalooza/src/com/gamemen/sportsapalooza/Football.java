package com.gamemen.sportsapalooza;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;

public class Football extends Sprite {
	
	private ForceMotion physics;
	private float rotSpeed;
	
	public Football(GameView gameView, PointF pos, Bitmap bmp) {
		super(gameView, bmp, pos, 0);
		physics = new ForceMotion(1, 100);
		rotSpeed = 0;
	}
	
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
	
	public void update(float deltaTime) {
		pos = physics.update(deltaTime, pos);
		setRot(getRot() + rotSpeed * deltaTime);
		
		rotSpeed *= 0.9;
	}
	
	public void addConstForce(PointF force) {
		physics.addConstForce(force);
	}
	
	public void addImpulseForce(PointF force) {
		physics.addImpulseForce(force);
		rotSpeed = force.length();
	}
	
	public void bounce(boolean alongX, boolean alongY) {
		physics.bounce(alongX, alongY);
		Audio.play(Audio.bounce);
	}
	
	public void stop() {
		physics.stop();
	}
	
}
