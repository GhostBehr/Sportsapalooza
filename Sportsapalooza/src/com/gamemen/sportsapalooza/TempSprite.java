package com.gamemen.sportsapalooza;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.PointF;

public class TempSprite extends Sprite {

	private float duration, timer;
	private boolean finished;
	private Bitmap bmpAnim[];
	private int animState = 0;
	
	public TempSprite(GameView gameView, Bitmap bmpAnim[], PointF pos, float duration) {
		super(gameView, bmpAnim[0], pos);
		this.bmpAnim = bmpAnim;
		this.duration = duration;
		this.timer = 0;
	}
	
	public void update(float deltaTime) {
		timer += deltaTime;
		
		if (timer >= duration / bmpAnim.length * (animState + 1)) {
			setBmp(BitmapLoader.bmpExplosion[++animState]);
			
			Random rand = new Random();
			setRot(rand.nextFloat() * 360);
		}
		
		if (timer >= duration) {
			finished = true;
		}
	}
	
	public boolean isFinished() {
		return finished;
	}
}
