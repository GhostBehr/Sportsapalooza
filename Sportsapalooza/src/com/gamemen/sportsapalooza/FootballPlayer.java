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
		this.detonator = detonator;
		motion = new SimpleMotion(new PointF(1, 0));
	}
	
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		detonator.onDraw(canvas);
	}
	
	public void update(float deltaTime) {
		if (detonator.isPressed()) {
			//splode
			//shit i guess we need explosion sprites
			System.out.println("SPLOJUN");
		}
		
		pos = motion.update(deltaTime, pos);
	}
}
