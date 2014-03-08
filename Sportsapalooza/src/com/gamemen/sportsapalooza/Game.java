package com.gamemen.sportsapalooza;

import java.util.ArrayList;
import java.util.List;

import com.gamemen.sportsapalooza.Button.ButtonState;
import com.gamemen.sportsapalooza.Players.PlayerID;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

public class Game {
	
	GameView gameView;
	
	private List<FootballPlayer> dudes;
	private Football ball;
	private Sprite leftDugout, rightDugout;
	private Button leftEndzone, rightEndzone;
	private float dugoutOffset;
	private RectF bounds;
	
	Bitmap dudeSprite, dugoutSprite, ballSprite, dudeButtonUp, dudeButtonDown;
	
	public Game(GameView gameView) {
		this.gameView = gameView;
		
		loadResources();
		
		dudes = new ArrayList<FootballPlayer>(6);
		
		ball = new Football(gameView, ballSprite, new PointF(gameView.getMeasuredWidth()/2, gameView.getMeasuredHeight()/2));
		
		leftDugout = new Sprite(gameView, dugoutSprite, new PointF(gameView.getMeasuredHeight() + dugoutSprite.getHeight(), 0));
		rightDugout = new Sprite(gameView, dugoutSprite, new PointF(dugoutSprite.getHeight(), 0));
		
		dugoutOffset = 150f;
		
		leftEndzone = new Button(gameView, null, null, null, null); //No images 'cause invisible
		rightEndzone = new Button(gameView, null, null, null, null);
		
		bounds.set(0, 0, gameView.getMeasuredWidth(), gameView.getMeasuredHeight());
	}
	
	private void loadResources() {
		dudeSprite = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.dude);
		ballSprite = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.ball);
		dugoutSprite = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.dugout);
	}
	
	public void update(float deltaTime) {
		//make sure bounds.contains(RectF) returns true for all the stuff?
		for (FootballPlayer dude : dudes){
			dude.update(deltaTime);
		}
		ball.update(deltaTime);
		leftDugout.pos = new PointF(leftDugout.pos.x, ball.pos.y - dugoutOffset);
		rightDugout.pos = new PointF(rightDugout.pos.x, ball.pos.y + dugoutOffset);
	}
	
	public void onDraw(Canvas canvas) {
		if (leftEndzone.getState() == ButtonState.TAPPED) {
			leftEndzone.resetState();
			dudes.add(new FootballPlayer(gameView, dudeSprite, new PointF(0, 0), Players.getColor(PlayerID.LEFT)));
		}
        
		if (leftEndzone.getState() == ButtonState.TAPPED) {
			rightEndzone.resetState();
			dudes.add(new FootballPlayer(gameView, dudeSprite, new PointF(0, 0), Players.getColor(PlayerID.RIGHT)));
		}
		
		for (FootballPlayer dude : dudes){
			dude.onDraw(canvas);
		}
		
		ball.onDraw(canvas);
		leftDugout.onDraw(canvas);
		rightDugout.onDraw(canvas);
	}
}
