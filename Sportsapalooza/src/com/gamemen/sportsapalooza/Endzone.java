package com.gamemen.sportsapalooza;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Canvas;
import android.graphics.PointF;

public class Endzone extends Button {
	private boolean isLeftSide;
	
	public static final String strScoreTypes[] = {" Touchhomes!", " Rundowns!", " Slamputts!", " Hole-in-Salchows!", " 7-10 Aces!", " Field Birdies!"};
	private int scoreType;
	
	private int dudesAvailable = 3;
	private List<FootballPlayer> dudes;
	
	private List<TempSprite> explosions;
	
	private Football ball;
	private Sprite dugout;
	
	private final float blastForce = 10000f;
	private final float blastRadius = 80.0f;
	private final float dugoutOffset = 240f;
	private final float explosionDuration = 0.75f;

	private int score = 0;
	
	public Endzone(GameView gameView, boolean isLeftSide, PointF pos, Football ball) {
		super(gameView, BitmapLoader.bmpEndzone, pos);
		
		Random rand = new Random();
		this.scoreType = rand.nextInt(Endzone.strScoreTypes.length);
		
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
		gameView.removeOnTouchListener(dude.detonator);
		explosions.add(new TempSprite(gameView, BitmapLoader.bmpExplosion, dude.pos, explosionDuration));
		
		PointF direction = new PointF(ball.getOrigin().x - dude.getOrigin().x, ball.getOrigin().y - dude.getOrigin().y);
		
		float magnitude = direction.length();
		
		if(magnitude <= blastRadius) {
			direction.set((direction.x/magnitude) * blastForce, (direction.y/magnitude) * blastForce);
			ball.addImpulseForce(direction);
		}
	}
	
	public void update(float deltaTime) {
		dugout.pos.set(isLeftSide ? ball.pos.x - dugoutOffset : ball.pos.x + dugoutOffset, dugout.pos.y);
		
		for(int i = explosions.size() - 1; i >= 0; --i) {
			
			explosions.get(i).update(deltaTime);
			
			if (explosions.get(i).isFinished()) {
				explosions.remove(i);
			}
		}
		
		for(FootballPlayer dude : dudes) {
			dude.update(deltaTime);
		}
		
		if(isPressed()) {
			for (int i = dudes.size() - 1; i >= 0; --i) {
				if (dudes.get(i).detonator.isPressed()){
					explosion(dudes.get(i));
					dudes.remove(i);
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
						new Button(gameView, BitmapLoader.bmpDetonatorUp, BitmapLoader.bmpDetonatorDown, new PointF(pointerLoc.x, pointerLoc.y))));
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
	
	public int getScoreType() {
		return scoreType;
	}
	
}