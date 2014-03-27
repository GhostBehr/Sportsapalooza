package com.gamemen.sportsapalooza;

import com.gamemen.sportsapalooza.Button.ButtonID;
import com.gamemen.sportsapalooza.Button.ButtonState;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;

public class FootballPlayer extends Sprite {
	
	private SimpleMotion motion;
	
	public Button detonator;
	
	public FootballPlayer(GameView gameView, Bitmap bmp, PointF pos, Button detonator) {
		super(gameView, bmp, pos);
		this.detonator = detonator;
	}
	
	public void explode() {
		
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
