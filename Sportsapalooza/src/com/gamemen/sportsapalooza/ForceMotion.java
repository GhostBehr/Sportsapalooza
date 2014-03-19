package com.gamemen.sportsapalooza;

import android.graphics.PointF;

public class ForceMotion {
	private float mass;
	private PointF vel, constForce, impulseForce;
	
	ForceMotion(float mass) {
		this(mass, new PointF(0, 0), new PointF(0, 0), new PointF(0, 0));
	}
	
	ForceMotion(float mass, PointF initVel) {
		this(mass, initVel, new PointF(0, 0), new PointF(0, 0));
	}
	
	ForceMotion(float mass, PointF initVel, PointF constForce, PointF impulseForce) {
		this.mass = mass;
		this.vel = initVel;
		this.constForce = constForce;
		this.impulseForce = impulseForce;
	}
	
	public PointF update(float deltaTime, PointF pos) {
		PointF totalForce = new PointF(constForce.x + impulseForce.x, constForce.y + impulseForce.y);
		impulseForce.set(0, 0);
		
		PointF accel = new PointF(totalForce.x / mass, totalForce.y / mass);
		PointF newPos =  new PointF(pos.x + vel.x * deltaTime + 0.5f * accel.x * deltaTime * deltaTime,
				pos.y + vel.y * deltaTime + 0.5f * accel.y * deltaTime * deltaTime);
		vel.set(vel.x + accel.x * deltaTime, vel.y + accel.y * deltaTime);
		
		return newPos;
	}
	
	public void addConstForce(PointF force) {
		constForce.set(constForce.x + force.x, constForce.y + force.y);
	}
	
	public void addImpulseForce(PointF force) {
		impulseForce.set(impulseForce.x + force.x, impulseForce.y + force.y);
	}
}
