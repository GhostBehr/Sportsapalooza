package com.gamemen.sportsapalooza;

import com.gamemen.sportsapalooza.Button.ButtonID;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

public class Game {
	
	public enum PlayStates {
		TEE_OFF,
		PLAYING,
		SCORED,
		PAUSED,
		GAME_OVER
	}
	
	private GameView gameView;
	private RectF bounds;
	
	private PlayStates currentState;
	private final float TEE_TIME = 3;
	private final float SCORE_TIME = 3;
	private float animTime = 0;
	private float gameTime = 0;
	
	private Football ball;
	private Endzone leftEndzone, rightEndzone;
	
	public Game(GameView gameView) {
		this.gameView = gameView;
		GameOptions.setTimeLimit(GameOptions.TimeLimits.ONE_MIN);
		
		ball = new Football(gameView, new PointF(GameView.SCREEN_SIZE.x/2, GameView.SCREEN_SIZE.y/2));
		
		leftEndzone = new Endzone(gameView, endzoneSprite, ButtonID.ENDZONE, new PointF(0, 40), leftDudeSprite, ball); //No images 'cause invisible
		rightEndzone = new Endzone(gameView, endzoneSprite, ButtonID.ENDZONE, new PointF(700, 40), rightDudeSprite, ball);
		
		currentState = PlayStates.TEE_OFF;
	}
	
	public void update(float deltaTime) {
		System.out.println(currentState + "; " + gameTime);
		
		switch(currentState) {
			case TEE_OFF:
				animTime += deltaTime;
				
				if (animTime >= TEE_TIME) {
					animTime = 0;
					currentState = PlayStates.PLAYING;
					
					// TEE OFF DA FOOSBALL
				}
				
				break;
				
			case PLAYING:
				gameTime += deltaTime;
				
				if (gameTime >= GameOptions.getTimeLimit().getTime()) {
					currentState = PlayStates.GAME_OVER;
					gameView.gameOver(leftEndzone.getScore(), rightEndzone.getScore());
				}
				
				ball.update(deltaTime);
				leftEndzone.update(deltaTime);
				rightEndzone.update(deltaTime);
				
				// check for a home touchdown
				
				break;
				
			case SCORED:
				animTime += deltaTime;
				
				if (animTime >= SCORE_TIME) {
					animTime = 0;
					currentState = PlayStates.TEE_OFF;
				}
				break;
				
			case PAUSED:
				// stop updating, update pause screen
				break;
				
			case GAME_OVER:
				// GameView will still draw Game, but will overlap it's own GameOver screen
				// So nothing happens here
				break;
			
		}
	}
	
	public void onDraw(Canvas canvas) {
		leftEndzone.onDraw(canvas);
		rightEndzone.onDraw(canvas);
		ball.onDraw(canvas);
		
		switch(currentState) {
			case TEE_OFF:
				// draw intro
				break;
				
			case PLAYING:
				// nothin special
				break;
				
			case SCORED:
				// draw you scored thing
				break;
				
			case PAUSED:
				// draw pause screen
				break;
				
			case GAME_OVER:
				// GameView will still draw Game, but will overlap it's own GameOver screen
				// nothing special basically
				break;
		}
	}
}