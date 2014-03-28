package com.gamemen.sportsapalooza;

import android.graphics.PointF;

public class ForceMotion {
	private float mass, friction;
	private PointF vel, constForce, impulseForce;
	
	ForceMotion(float mass) {
		this(mass, new PointF(0, 0), new PointF(0, 0), new PointF(0, 0), 0);
	}
	
	ForceMotion(float mass, float friction) {
		this(mass, new PointF(0, 0), new PointF(0, 0), new PointF(0, 0), friction);
	}
	
	ForceMotion(float mass, PointF initVel) {
		this(mass, initVel, new PointF(0, 0), new PointF(0, 0), 0);
	}
	
	ForceMotion(float mass, PointF initVel, PointF constForce, PointF impulseForce, float friction) {
		this.mass = mass;
		this.vel = initVel;
		this.constForce = constForce;
		this.impulseForce = impulseForce;
		this.friction = friction;
	}
	
	public PointF update(float deltaTime, PointF pos) {
		// Update forces
		PointF totalForce = new PointF(constForce.x + impulseForce.x, constForce.y + impulseForce.y);
		impulseForce.set(0, 0);

		// Stuff for friction checks
		PointF frictionForce;
		PointF oldVel = new PointF(vel.x, vel.y);
		boolean externalForces = totalForce.length() == 0 ? false : true;
		
		// Add friction
		if (vel.length() > 0 && friction > 0) {
			frictionForce = new PointF(vel.x, vel.y);
			ForceMotion.normalizeVec(frictionForce);
			frictionForce.set(frictionForce.x * friction, frictionForce.y * friction);
			totalForce.set(totalForce.x - frictionForce.x, totalForce.y - frictionForce.y);
		}
		
		// Calculate position
		PointF accel = new PointF(totalForce.x / mass, totalForce.y / mass);
		PointF newPos =  new PointF(pos.x + vel.x * deltaTime + 0.5f * accel.x * deltaTime * deltaTime,
				pos.y + vel.y * deltaTime + 0.5f * accel.y * deltaTime * deltaTime);
		vel.set(vel.x + accel.x * deltaTime, vel.y + accel.y * deltaTime);
		
		// If friction would reverse velocity, stop object
		if (friction > 0 && !externalForces) {
			if ((oldVel.x >= 0 && vel.x < 0) || (oldVel.x <= 0 && vel.x > 0)) {	// switched directions in x due to friction
				vel.x = 0;
			}
			if ((oldVel.y >= 0 && vel.y < 0) || (oldVel.y <= 0 && vel.y > 0)) {	// switched directions in y due to friction
				vel.y = 0;
			}
		}
		
		return newPos;
	}
	
	public void addConstForce(PointF force) {
		constForce.set(constForce.x + force.x, constForce.y + force.y);
	}
	
	public void addImpulseForce(PointF force) {
		impulseForce.set(impulseForce.x + force.x, impulseForce.y + force.y);
	}
	
	public void bounce(boolean alongX, boolean alongY) {
		vel.set(vel.x * (alongX ? -1 : 1), vel.y * (alongY ? -1 : 1));
		constForce.set(constForce.x * (alongX ? -1 : 1), constForce.y * (alongY ? -1 : 1));
	}
	
	public void setFriction(float friction) {
		this.friction = friction;
	}
	
	public static void normalizeVec(PointF vec) {
		float length = vec.length();
		if (length > 0) {
			vec.set(vec.x / length, vec.y / length);
		}
	}
	
}
