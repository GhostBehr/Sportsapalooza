package com.gamemen.sportsapalooza;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Canvas;
import android.graphics.PointF;

public class Endzone extends Button {
	private boolean isLeftSide;
	
	public static final String SCORE_TYPES[] = {" Touchhomes!", " Rundowns!", " Slamputts!", " Hole-in-Dunks!", " 7-10 Aces!", " Field Birdies!", " Goal-in-Scores!"};
	private int scoreType;
	
	private int dudesAvailable = 3;
	private List<FootballPlayer> dudes;
	
	private List<TempSprite> explosions;
	
	private Football ball;
	private Sprite dugout;
	
	private final float BLAST_FORCE = 10000f;
	private final float BLAST_RADIUS = 80.0f;
	private final float DUGOUT_OFFSET = 240f;
	private final float EXPLOSION_DURATION = 0.75f;

	private int score = 0;
	
	public Endzone(GameView gameView, boolean isLeftSide, PointF pos, Football ball) {
		super(gameView, BitmapLoader.bmpEndzone, pos, false);
		
		Random rand = new Random();
		this.scoreType = rand.nextInt(Endzone.SCORE_TYPES.length);
		
		this.isLeftSide = isLeftSide;
		this.ball = ball;
		dudes = new ArrayList<FootballPlayer>(3);
		explosions = new ArrayList<TempSprite>(3);
		
		PointF dugoutPos;
		
		dugoutPos = new PointF(isLeftSide ? ball.pos.x - DUGOUT_OFFSET : ball.pos.x + DUGOUT_OFFSET, GameView.SCREEN_SIZE.y/2);
		
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
		explosions.add(new TempSprite(gameView, BitmapLoader.bmpExplosion, dude.pos, EXPLOSION_DURATION));
		
		PointF direction = new PointF(ball.getOrigin().x - dude.getOrigin().x, ball.getOrigin().y - dude.getOrigin().y);
		
		float magnitude = direction.length();
		
		if(magnitude <= BLAST_RADIUS) {
			direction.set((direction.x/magnitude) * BLAST_FORCE, (direction.y/magnitude) * BLAST_FORCE);
			ball.addImpulseForce(direction);
		}
	}
	
	public void update(float deltaTime) {
		dugout.pos.set(isLeftSide ? ball.pos.x - DUGOUT_OFFSET : ball.pos.x + DUGOUT_OFFSET, dugout.pos.y);
		
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
						new Button(gameView, BitmapLoader.bmpDetonatorUp, BitmapLoader.bmpDetonatorDown, new PointF((isLeftSide ? 34 : 734), pointerLoc.y - 16))));
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
	
	public void setBall(Football ball) {
		this.ball = ball;
	}
}