package com.gamemen.sportsapalooza;

import com.gamemen.sportsapalooza.Button.ButtonState;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;

public class FootballPlayer extends Sprite {
	
	private SimpleMotion motion;
	
	public Button detonator;
	
	public FootballPlayer(GameView gameView, boolean leftSide, PointF pos, Button detonator) {
		super(gameView, leftSide ? BitmapLoader.bmpLeftDude : BitmapLoader.bmpRightDude, pos);
		gameView.addOnTouchListener(detonator);
		this.detonator = detonator;
		motion = new SimpleMotion(new PointF(leftSide ? 100 : -100, 0));
	}
	
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		detonator.onDraw(canvas);
	}
	
	public void update(float deltaTime) {
		pos = motion.update(deltaTime, pos);
	}
}
