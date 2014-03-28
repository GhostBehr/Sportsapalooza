package com.gamemen.sportsapalooza;

import java.util.ArrayList;
import java.util.List;

import com.gamemen.sportsapalooza.Button.ButtonState;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;

public class Endzone extends Button {
	private boolean isLeftSide;
	
	private int dudesAvailable = 3;
	private List<FootballPlayer> dudes;
	
	private List<TempSprite> explosions;
	
	private Football ball;
	private Sprite dugout;
	
	private final float blastForce = 10000f;
	private final float blastRadius = 30.0f;
	private final float dugoutOffset = 200f;
	private final float explosionDuration = 0.5f;

	private int score = 0;
	
	public Endzone(GameView gameView, boolean isLeftSide, PointF pos, Football ball) {
		super(gameView, BitmapLoader.bmpEndzone, pos);
		
		this.isLeftSide = isLeftSide;
		this.ball = ball;
		dudes = new ArrayList<FootballPlayer>(3);
		explosions = new ArrayList<TempSprite>(3);
		
		PointF dugoutPos;
		
		dugoutPos = new PointF(isLeftSide ? ball.pos.x - dugoutOffset : ball.pos.x + dugoutOffset, GameView.SCREEN_SIZE.y/2);
		
		dugout = new Sprite(gameView, BitmapLoader.bmpDugout, dugoutPos);
	}
	
	public int getScore() {
		return score;
	}
	
	public void scorePlusPlus() {
		score += 1;
	}
	
	public void explosion(FootballPlayer dude) {
		explosions.add(new TempSprite(gameView, BitmapLoader.bmpExplosion, dude.pos, explosionDuration));
		
		PointF direction = new PointF(ball.pos.x - dude.pos.x, ball.pos.y - dude.pos.y);
		
		float magnitude = direction.length();
		
		if(magnitude <= blastRadius) {
			direction.set((direction.x/magnitude) * blastForce, (direction.y/magnitude) * blastForce);
			ball.addImpulseForce(direction);
		}
	}
	
	public void update(float deltaTime) {
		for(TempSprite explosion : explosions) {
			
			explosion.update(deltaTime);
			
			if (explosion.isFinished()) {
				explosions.remove(explosion);
			}
		}
		
		for(FootballPlayer dude : dudes) {
			dude.update(deltaTime);
		}
		
		if(isPressed()) {
			for (FootballPlayer dude : dudes) {
				if (dude.detonator.isPressed()){
					explosion(dude);
					dudes.remove(dude);
					dudesAvailable++;
					return;
				}
			}
			
			if(dudesAvailable > 0){
				--dudesAvailable;
				
				dudes.add(new FootballPlayer(
						gameView,
						isLeftSide,
						new PointF(dugout.pos.x, pointerLoc.y),
						new Button(gameView, BitmapLoader.bmpDetonatorUp, BitmapLoader.bmpDetonatorDown, pointerLoc)));
			}
		}
	}
	
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		for(TempSprite explosion : explosions) {
			explosion.onDraw(canvas);
		}
		
		for (FootballPlayer dude : dudes) {
			dude.onDraw(canvas);
		}
		dugout.onDraw(canvas);
	}
}