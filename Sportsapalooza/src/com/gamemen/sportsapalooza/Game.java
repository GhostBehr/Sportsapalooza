package com.gamemen.sportsapalooza;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
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
		
		bounds = new RectF(0, 40, 800, 480);
		
		ball = new Football(gameView, new PointF(GameView.SCREEN_SIZE.x/2, GameView.SCREEN_SIZE.y/2));
		leftEndzone = new Endzone(gameView, true, new PointF(0, 40), ball);
		rightEndzone = new Endzone(gameView, false, new PointF(700, 40), ball);
		
		currentState = PlayStates.TEE_OFF;
	}
	
	public void update(float deltaTime) {
//		System.out.println("BALL: " + ball.pos.x + ", " + ball.pos.y);
		
		switch(currentState) {
			case TEE_OFF:
				animTime += deltaTime;
				
				if (animTime >= TEE_TIME) {
					animTime = 0;
					currentState = PlayStates.PLAYING;
					
					System.out.println("TEE TIME");
					ball.addImpulseForce(new PointF(0, 10000));
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
				
				// Ball collision
				if (ball.getBounds().left < bounds.left) {
					ball.pos.x = bounds.left;
					ball.bounce(true, false);
				}
				else if (ball.getBounds().right > bounds.right) {
					ball.pos.x = bounds.right - ball.getBounds().width();
					ball.bounce(true, false);
				}
				if (ball.getBounds().top < bounds.top) {
					ball.pos.y = bounds.top;
					ball.bounce(false, true);
				}
				else if (ball.getBounds().bottom > bounds.bottom) {
					ball.pos.y = bounds.bottom - ball.getBounds().height();
					ball.bounce(false, true);
				}
				
				// Check for scoredown
				if (leftEndzone.getBounds().contains(ball.getBounds())) {
					leftEndzone.scorePlusPlus();
					currentState = PlayStates.SCORED;
				}
				if (rightEndzone.getBounds().contains(ball.getBounds())) {
					rightEndzone.scorePlusPlus();
					currentState = PlayStates.SCORED;
				}
				
				break;
				
			case SCORED:
				ball.pos.set(GameView.SCREEN_SIZE.x/2, GameView.SCREEN_SIZE.y/2);
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
		
//		Paint paint = new Paint();
//		paint.setARGB(255, 255, 0, 0);
//		canvas.drawRect(ball.getBounds(), paint);
		
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