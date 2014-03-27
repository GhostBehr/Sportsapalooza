package com.gamemen.sportsapalooza;

import java.util.ArrayList;
import java.util.List;

import com.gamemen.sportsapalooza.Button.ButtonID;
import com.gamemen.sportsapalooza.Button.ButtonState;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

public class Game {
	
	private GameView gameView;
	
	private Football ball;
	private Endzone leftEndzone, rightEndzone;
	
	private RectF bounds;
	
	Bitmap dugoutSprite, ballSprite, rightDudeSprite, leftDudeSprite, endzoneSprite;
	
	public Game(GameView gameView) {
		this.gameView = gameView;
		
		loadResources();
		
		ball = new Football(gameView, ballSprite, new PointF(GameView.SCREEN_SIZE.x/2, GameView.SCREEN_SIZE.y/2));
		
		leftEndzone = new Endzone(gameView, endzoneSprite, endzoneSprite, ButtonID.ENDZONE, new PointF(0, 0), leftDudeSprite, ball); //No images 'cause invisible
		rightEndzone = new Endzone(gameView, endzoneSprite, endzoneSprite, ButtonID.ENDZONE, new PointF(gameView.getMeasuredHeight() + 20, 0), rightDudeSprite, ball);
	}
	
	private void loadResources() {
		endzoneSprite = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.endzone);
		leftDudeSprite = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.leftdude);
		rightDudeSprite = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.rightdude);
		ballSprite = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.ball);
	}
	
	public void update(float deltaTime) {
		ball.update(deltaTime);
	}
	
	public void onDraw(Canvas canvas) {
		ball.onDraw(canvas);
	}
}