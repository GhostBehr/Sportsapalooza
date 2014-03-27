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
	private int dudesAvailable = 3;

	private Bitmap dudeSprite;
	private Bitmap dugoutSprite;
	
	private List<FootballPlayer> dudeList;
	private List<Button> detonators;
	
	private Football ball;
	private Sprite dugout;
	
	private final float blastForce = 1.0f;
	private final float blastRadius = 1.0f;
	private final float dugoutOffset = 150f;

	private int score = 0;
	
	public Endzone(GameView gameView, Bitmap buttonUp, ButtonID ID, PointF pos, Bitmap dudeSprite, Football ball) {
		super(gameView, buttonUp, ID, pos);
		
		LoadResources();
		
		this.dudeSprite = dudeSprite;
		
		this.ball = ball;
		detonators = new ArrayList<Button>(3);
		dudeList = new ArrayList<FootballPlayer>(3);
		
		dugout = new Sprite(gameView, dugoutSprite, new PointF(gameView.getMeasuredHeight() + dugoutSprite.getHeight(), 0));
	}
	
	private void LoadResources() {
		dugoutSprite = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.dugout);
	}
	
	public int getScore() {
		return score;
	}
	
	public void scorePlusPlus() {
		score += 1;
	}
	
	public void update(float deltaTime) {
		if(this.isPressed()) {
			
		}
	}
	
	public void onDraw(Canvas canvas) {
		for (FootballPlayer dude : dudeList) {
			dude.onDraw(canvas);
		}
		for (Button butt : detonators) {
			butt.onDraw(canvas);
		}
		dugout.onDraw(canvas);
	}
}