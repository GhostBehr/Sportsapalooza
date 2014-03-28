package com.gamemen.sportsapalooza;

import android.graphics.Bitmap;
import android.graphics.PointF;

public class TempSprite extends Sprite {

	private float duration;
	private boolean finished;
	
	public TempSprite(GameView gameView, Bitmap bmp, PointF pos, float duration) {
		super(gameView, bmp, pos);
		this.duration = duration;
	}

	public boolean isFinished() {
		return finished;
	}
	
	public void update(float deltaTime) {
		duration -= deltaTime;
		
		if (duration <= 0) {
			finished = true;
		}
	}
}
