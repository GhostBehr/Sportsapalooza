package com.gamemen.sportsapalooza;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;

public class FootballPlayer extends Sprite {
	SimpleMotion motion;
	
	public FootballPlayer(GameView gameView, Bitmap bmp) {
		super(gameView, bmp);
	}

	public FootballPlayer(GameView gameView, Bitmap bmp, PointF pos) {
		super(gameView, bmp, pos);
	}
	
	public FootballPlayer(GameView gameView, Bitmap bmp, PointF pos, float rot) {
		super(gameView, bmp, pos, rot);
	}
	
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
	
	public void update(float deltaTime) {
		motion.update(deltaTime, pos);
	}
}
