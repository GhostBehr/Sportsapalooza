package com.gamemen.sportsapalooza;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import java.util.Random;

import com.gamemen.sportsapalooza.GameView.GameStates;

public class Game {
	
	public enum PlayStates {
		TEE_OFF,
		PLAYING,
		SCORED,
		GAME_OVER
	}
	
	private Random rand;
	
	private GameView gameView;
	private RectF bounds;
	private Paint paint;
	
	private PlayStates currentState;
	private boolean paused = false;
	
	private final float TEE_TIME = 4;
	private final float SCORE_TIME = 5;
	private float animTime = 0;
	private float gameTime = 0;
	private int teeState = 0;
	
	private Sprite teeOffSprite, scoredSprite; 
	private Football ball;
	private Endzone leftEndzone, rightEndzone;
	
	public Game(GameView gameView) {
		rand = new Random();
		this.gameView = gameView;
		GameOptions.setTimeLimit(GameOptions.TimeLimits.ONE_MIN);
		
		bounds = new RectF(0, 40, 800, 480);
		paint = new Paint();
		paint.setARGB(255, 255, 255, 255);
		paint.setTextSize(25);
		
		teeOffSprite = new Sprite(gameView, BitmapLoader.bmpTeeOff[0], new PointF(GameView.SCREEN_SIZE.x / 2 - BitmapLoader.bmpTeeOff[0].getWidth() / 2, GameView.SCREEN_SIZE.y / 2 - BitmapLoader.bmpTeeOff[0].getHeight() / 2));
		
		ball = CreateBall();
		leftEndzone = new Endzone(gameView, true, new PointF(0, 40), ball);
		rightEndzone = new Endzone(gameView, false, new PointF(700, 40), ball);
		
		currentState = PlayStates.TEE_OFF;
	}
	
	public void update(float deltaTime) {
		if (!paused) {
			switch(currentState) {
				case TEE_OFF:
					animTime += deltaTime;
					
					if (animTime >= TEE_TIME / 4 * (teeState + 1)) {
						teeOffSprite.setBmp(BitmapLoader.bmpTeeOff[++teeState]);
					}
					
					if (animTime >= TEE_TIME) {
						animTime = 0;
						teeState = 0;
						currentState = PlayStates.PLAYING;
						
						System.out.println("TEE TIME");
						ball.addImpulseForce(new PointF(0, 10000));
						
						leftEndzone.setActive(true);
						rightEndzone.setActive(true);
					}
					
					break;
					
				case PLAYING:
					gameTime += deltaTime;
					
					if (gameTime >= GameOptions.getTimeLimit().getTime()) {
						currentState = PlayStates.GAME_OVER;
						gameView.setCurrentState(GameStates.GAME_OVER);
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
						scoredSprite = new Sprite(gameView, BitmapLoader.bmpScoreTypes[leftEndzone.getScoreType()], new PointF(GameView.SCREEN_SIZE.x / 2 - BitmapLoader.bmpScoreTypes[leftEndzone.getScoreType()].getWidth() / 2, GameView.SCREEN_SIZE.y / 2 - BitmapLoader.bmpScoreTypes[leftEndzone.getScoreType()].getHeight() / 2));
						currentState = PlayStates.SCORED;
						
						leftEndzone.setActive(false);
						rightEndzone.setActive(false);
					}
					if (rightEndzone.getBounds().contains(ball.getBounds())) {
						rightEndzone.scorePlusPlus();
						scoredSprite = new Sprite(gameView, BitmapLoader.bmpScoreTypes[rightEndzone.getScoreType()], new PointF(GameView.SCREEN_SIZE.x / 2 - BitmapLoader.bmpScoreTypes[rightEndzone.getScoreType()].getWidth() / 2, GameView.SCREEN_SIZE.y / 2 - BitmapLoader.bmpScoreTypes[rightEndzone.getScoreType()].getHeight() / 2));
						currentState = PlayStates.SCORED;
						
						leftEndzone.setActive(false);
						rightEndzone.setActive(false);
					}
					
					break;
					
				case SCORED:
					animTime += deltaTime;
					
					if (animTime >= SCORE_TIME) {
						animTime = 0;
						currentState = PlayStates.TEE_OFF;
						
						nextDown();
					}
					break;
					
				case GAME_OVER:
					// GameView will still draw Game, but will overlap it's own GameOver screen
					// So nothing happens here
					break;
				
			}
		}
	}
	
	public void onDraw(Canvas canvas) {
		leftEndzone.onDraw(canvas);
		rightEndzone.onDraw(canvas);
		ball.onDraw(canvas);
		
		canvas.drawText(leftEndzone.getScore() + Endzone.SCORE_TYPES[leftEndzone.getScoreType()], 20, 30, paint);
		canvas.drawText(rightEndzone.getScore() + Endzone.SCORE_TYPES[rightEndzone.getScoreType()], GameView.SCREEN_SIZE.x - 200, 30, paint);
		
		switch(currentState) {
			case TEE_OFF:
				teeOffSprite.onDraw(canvas);
				break;
				
			case PLAYING:
				// nothin special
				break;
				
			case SCORED:
				scoredSprite.onDraw(canvas);
				break;
				
			case GAME_OVER:
				// GameView will still draw Game, but will overlap it's own GameOver screen
				// nothing special basically
				break;
		}
	}
	
	private void nextDown() {
		// reset ball
		ball = CreateBall();
		leftEndzone.setBall(ball);
		rightEndzone.setBall(ball);
		
		// reset dugouts
		
		// delete all players/detonators/explosions
	}
	
	public Football CreateBall() {
		int i = rand.nextInt(2);
		
		Bitmap bmp;
		
		switch(i){
		default:
		case 0:
			bmp = BitmapLoader.bmpFootball;
			break;
		case 1:
			bmp = BitmapLoader.bmpZebraball;
			break;
		case 2:
			bmp = BitmapLoader.bmpBirdyball;
			break;
		}
		
		return new Football(gameView, new PointF(GameView.SCREEN_SIZE.x/2, GameView.SCREEN_SIZE.y/2), bmp);
	}
	
	public boolean isPaused() {
		return paused;
	}
	
	public void setPaused(boolean paused) {
		this.paused = paused;
		
		leftEndzone.setActive(!paused);
		rightEndzone.setActive(!paused);
	}
	
	public int[] getScores() {
		int[] scores = { leftEndzone.getScore(), rightEndzone.getScore() };
		return scores;
	}
	
}