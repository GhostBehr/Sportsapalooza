package com.gamemen.sportsapalooza;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import java.util.Random;

import com.gamemen.sportsapalooza.GameOptions.GameModes;
import com.gamemen.sportsapalooza.GameOptions.ScoreLimits;
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
	private final float SCORE_TIME = 3;
	private float animTime = 0;
	private float gameTime = 0;
	private int teeState = 0;
	
	private Sprite teeOffSprite, scoredSprite; 
	private Football ball;
	private Endzone leftEndzone, rightEndzone;
	
	public Game(GameView gameView) {
		rand = new Random();
		this.gameView = gameView;
		
		// Game options
		GameOptions.setGameMode(GameModes.SCORE_LIMIT);
//		GameOptions.setTimeLimit(GameOptions.TimeLimits.ONE_MIN);
		GameOptions.setScoreLimit(ScoreLimits.FIVE);
		
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
						teeOffSprite.setBmp(BitmapLoader.bmpTeeOff[++teeState], true);
						teeOffSprite.pos.set(GameView.SCREEN_SIZE.x / 2 - teeOffSprite.getBounds().width() / 2, GameView.SCREEN_SIZE.y / 2 - teeOffSprite.getBounds().height() / 2);
						
						if (teeState != 3) {
							Audio.play(Audio.countdown);
						}
						else {
							Audio.play(Audio.teeoff);
						}
					}
					
					if (animTime >= TEE_TIME) {
						animTime = 0;
						teeState = 0;
						
						teeOffSprite.setBmp(BitmapLoader.bmpTeeOff[0], true);
						teeOffSprite.pos.set(GameView.SCREEN_SIZE.x / 2 - teeOffSprite.getBounds().width() / 2, GameView.SCREEN_SIZE.y / 2 - teeOffSprite.getBounds().height() / 2);
						currentState = PlayStates.PLAYING;
						
						System.out.println("TEE TIME");
						Audio.play(Audio.bounce);
						
						Random rand = new Random();
						ball.addImpulseForce(new PointF(0, 10000 * (rand.nextBoolean() ? 1 : -1)));
						
						leftEndzone.setActive(true);
						rightEndzone.setActive(true);
					}
					
					break;
					
				case PLAYING:
					if (GameOptions.getGameMode() == GameModes.TIME_LIMIT) {
						gameTime += deltaTime;
						
						if (gameTime >= GameOptions.getTimeLimit().getTime()) {
							gameOver();
						}
					}
					else if (leftEndzone.getScore() >= GameOptions.getScoreLimit().getScore() || rightEndzone.getScore() >= GameOptions.getScoreLimit().getScore()) {
						gameOver();
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
						scoreForEndzone(rightEndzone);
					}
					if (rightEndzone.getBounds().contains(ball.getBounds())) {
						scoreForEndzone(leftEndzone);
					}
					
					break;
					
				case SCORED:
					animTime += deltaTime;
					
					// For explosions
					leftEndzone.update(deltaTime);
					rightEndzone.update(deltaTime);
					
					if (animTime >= SCORE_TIME) {
						animTime = 0;
						
						if (leftEndzone.getScore() >= GameOptions.getScoreLimit().getScore() || rightEndzone.getScore() >= GameOptions.getScoreLimit().getScore()) {
							if (GameOptions.getGameMode() == GameModes.SCORE_LIMIT) {	
								gameOver();
							}
						}
						else {
							currentState = PlayStates.TEE_OFF;
							nextDown();
						}
						
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
	
	private void scoreForEndzone(Endzone endzone) {
		endzone.scorePlusPlus();
		scoredSprite = new Sprite(gameView, BitmapLoader.bmpScoreTypes[endzone.getScoreType()], new PointF(GameView.SCREEN_SIZE.x / 2 - BitmapLoader.bmpScoreTypes[endzone.getScoreType()].getWidth() / 2, GameView.SCREEN_SIZE.y / 2 - BitmapLoader.bmpScoreTypes[endzone.getScoreType()].getHeight() / 2));
		currentState = PlayStates.SCORED;
		
		leftEndzone.setActive(false);
		rightEndzone.setActive(false);
		
		leftEndzone.blowDudes();
		rightEndzone.blowDudes();
	}
	
	private void nextDown() {
		// reset ball
		ball = CreateBall();
		leftEndzone.setBall(ball);
		rightEndzone.setBall(ball);
		
		// reset dugouts
		leftEndzone.update(1);
		rightEndzone.update(1);
	}
	
	public Football CreateBall() {
		int i = rand.nextInt(3);
		
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
		
		return new Football(gameView, new PointF(GameView.SCREEN_SIZE.x / 2 - bmp.getWidth() / 2, GameView.SCREEN_SIZE.y / 2 - bmp.getHeight() / 2), bmp);
	}
	
	private void gameOver() {
		currentState = PlayStates.GAME_OVER;
		gameView.setCurrentState(GameStates.GAME_OVER);
	}
	
	
	//////////////////////////////////////////////////////
	// GETTERS AND SETTERS
	/////////////////////////////////////////////////////
	
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