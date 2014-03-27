package com.gamemen.sportsapalooza;

import java.util.ArrayList;
import java.util.List;

import com.gamemen.sportsapalooza.Button.ButtonState;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;

public class Endzone extends Button {
	private boolean isLeftSide;
	
	private int dudesAvailable = 3;
	private List<FootballPlayer> dudes;
	
	private List<Sprite> explosions;
	private List<Float> explosionDurations;
	
	private Football ball;
	private Sprite dugout;
	
	private final float blastForce = 100000f;
	private final float blastRadius = 1.0f;
	private final float dugoutOffset = 150f;
	private final float explosionDuration = 0.5f;

	private int score = 0;
	
	public Endzone(GameView gameView, boolean isLeftSide, PointF pos, Football ball) {
		super(gameView, BitmapLoader.bmpEndzone, pos);
		
		this.isLeftSide = isLeftSide;
		this.ball = ball;
		dudes = new ArrayList<FootballPlayer>(3);
		explosions = new ArrayList<Sprite>(3);
		explosionDurations = new ArrayList<Float>(3);
		
		PointF dugoutPos;
		if (isLeftSide) {
			dugoutPos = new PointF(dugoutOffset, gameView.SCREEN_SIZE.y/2);
		} else {
			dugoutPos = new PointF(pos.x - dugoutOffset, gameView.SCREEN_SIZE.y/2);			
		}
		
		dugout = new Sprite(gameView, BitmapLoader.bmpDugout, dugoutPos);
	}
	
	public int getScore() {
		return score;
	}
	
	public void scorePlusPlus() {
		score += 1;
	}
	
	public void explosion(FootballPlayer dude) {
		explosions.add(new Sprite(gameView, BitmapLoader.bmpExplosion, dude.pos));
		PointF direction = new PointF(ball.pos.x - dude.pos.x, ball.pos.y - dude.pos.y);
		float magnitude = direction.length();
		if(magnitude <= 1){
			direction.set(direction.x/magnitude * blastForce, direction.y/magnitude * blastForce);
			ball.addImpulseForce(direction);
		}
	}
	
	public void update(float deltaTime) {
		for(int i = 0; i < explosionDurations.size(); ++i) {
			Float duration = explosionDurations.get(i);
			duration -= deltaTime;
			if (duration <= 0) {
				explosionDurations.remove(duration);
				explosions.remove(i);
			}
			++i;
		}
		
		if(this.isPressed()) {
			for (int i = 0; i < dudes.size(); i++) {
				if (dudes.get(i).detonator.getBounds().contains(pointerLoc.x, pointerLoc.y)){
					explosion(dudes.get(i));
					dudes.remove(i);
					return;
				}
			}
			if(dudesAvailable > 0){
				--dudesAvailable;
				dudes.add(new FootballPlayer(
						gameView,
						isLeftSide,
						new PointF(pointerLoc.x, pointerLoc.y + dugoutOffset),
						new Button(gameView, BitmapLoader.bmpDetonatorUp, BitmapLoader.bmpDetonatorDown, pointerLoc)));
			}
		}
	}
	
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		for(Sprite explosion : explosions) {
			explosion.onDraw(canvas);
		}
		
		for (FootballPlayer dude : dudes) {
			dude.onDraw(canvas);
		}
		dugout.onDraw(canvas);
	}
}