package com.gamemen.sportsapalooza;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import java.util.Random;

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
	
	private final float TEE_TIME = 3;
	private final float SCORE_TIME = 3;
	private float animTime = 0;
	private float gameTime = 0;
	
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
		
		ball = CreateBall();
		leftEndzone = new Endzone(gameView, true, new PointF(0, 40), ball);
		rightEndzone = new Endzone(gameView, false, new PointF(700, 40), ball);
		
		currentState = PlayStates.TEE_OFF;
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
	
	public void update(float deltaTime) {
		if (!paused) {
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
					ball.stop();
					
					animTime += deltaTime;
					
					if (animTime >= SCORE_TIME) {
						animTime = 0;
						currentState = PlayStates.TEE_OFF;
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
				// draw intro
				break;
				
			case PLAYING:
				// nothin special
				break;
				
			case SCORED:
				// draw you scored thing
				break;
				
			case GAME_OVER:
				// GameView will still draw Game, but will overlap it's own GameOver screen
				// nothing special basically
				break;
		}
	}
	
	public boolean isPaused() {
		return paused;
	}
	
	public void togglePause() {
		paused = !paused;
	}
	
}