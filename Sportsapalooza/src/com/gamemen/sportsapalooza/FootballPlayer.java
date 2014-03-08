package com.gamemen.sportsapalooza;

import com.gamemen.sportsapalooza.Button.ButtonID;
import com.gamemen.sportsapalooza.Button.ButtonState;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;

public class FootballPlayer extends Sprite {
	
	SimpleMotion motion;
	Color color;
	Button detonator;
	
	public FootballPlayer(GameView gameView, Bitmap bmp, PointF pos, Color color) {
		super(gameView, bmp, pos);
		detonator = new Button(gameView, null, null, ButtonID.DETONATOR, null);
		// wait maybe we should just have two different player sprites for each team uhh why did I do this
	}
	
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		detonator.onDraw(canvas);
	}
	
	public void update(float deltaTime) {
		if (detonator.getState() == ButtonState.TAPPED) {
			//splode
			//shit i guess we need explosion sprites
		}
		
		pos = motion.update(deltaTime, pos);
	}
}
