 package com.gamemen.sportsapalooza;

import android.graphics.PointF;

public class SimpleMotion {
	private PointF speed;
	
	SimpleMotion() {
		this(new PointF(0, 0));
	}
	SimpleMotion(PointF speed) {
		this.speed = speed;
	}
	
	public void setSpeed(PointF speed) {
		this.speed = speed;
	}
	
	public PointF update(float deltaTime, PointF pos) {
		return new PointF(pos.x + speed.x * deltaTime, pos.y + speed.y * deltaTime);
	}
	
	public void bounce(boolean alongX, boolean alongY) {
		speed.set(speed.x * (alongX ? -1 : 1), speed.y * (alongY ? -1 : 1));
	}
	
}