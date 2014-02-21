package com.gamemen.sportsapalooza;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

public class Game {
	
	private List<FootballPlayer> dudes;
	private Football ball;
	private Sprite leftDugout;
	private Sprite rightDugout;
	private Button leftEndzone;
	private Button rightEndzone;
	private float endzoneOffset;
	private RectF bounds;
	
	public Game(GameView gameView) {
		dudes = new ArrayList<FootballPlayer>(6);
		for (int i = 0; i < dudes.size(); i++) {
			dudes.add(new FootballPlayer(gameView, null, new PointF(0, 0)));
		}
		
		ball = new Football(gameView, null, null);
		
		leftDugout = new Sprite(gameView, null, null);
		rightDugout = new Sprite(gameView, null, null);
		
		leftEndzone = new Button(gameView, null, null, null, null);
		rightEndzone = new Button(gameView, null, null, null, null);
		
		bounds.set(0, 0, 200, 200);
	}
	
	public void update(float deltaTime) {
		//make sure bounds.contains(RectF) returns true for all the stuff?
		for (FootballPlayer dude : dudes){
			dude.update(deltaTime);
		}
		ball.update(deltaTime);
		//Endzone.pos = new PointF(Endzone.pos.x, ball.pos.y +/- endzoneoffset);
	}
	
	public void onDraw(Canvas canvas) {
		for (FootballPlayer dude : dudes){
			dude.onDraw(canvas);
		}
		ball.onDraw(canvas);
		leftDugout.onDraw(canvas);
		rightDugout.onDraw(canvas);
	}
}
