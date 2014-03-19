package com.gamemen.sportsapalooza;

import com.gamemen.sportsapalooza.Button.ButtonID;
import com.gamemen.sportsapalooza.Button.ButtonState;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;

public class FootballPlayer extends Sprite {
	
	private SimpleMotion motion;
	private Button detonator;
	private boolean exploding;
	
	public FootballPlayer(GameView gameView, Bitmap bmp, PointF pos, Color color) {
		super(gameView, bmp, pos);
		detonator = new Button(gameView, null, null, ButtonID.DETONATOR, null);
		exploding = false;
		// wait maybe we should just have two different player sprites for each team uhh why did I do this
		// yeah that's definitely less dumb, i'll make the second dude sprite later
	}
	
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		detonator.onDraw(canvas);
	}
	
	public boolean isExploding() {
		return exploding;
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
