package com.gamemen.sportsapalooza;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.PointF;

public class Game {
	
	List<FootballPlayer> dudes;
	Football ball;
	Sprite dugoutOne;
	Sprite dugoutTwo;
	
	public Game(GameView gameView) {
		dudes = new ArrayList<FootballPlayer>(6);
		for (int i = 0; i < dudes.size(); i++) {
			dudes.add(new FootballPlayer(gameView, null, new PointF(0, 0)));
		}
		
		ball = new Football(gameView, null, null);
		
		
	}
	
	public void update(float deltaTime) {
		for (FootballPlayer dude : dudes){
			dude.update(deltaTime);
		}
		ball.update(deltaTime);
	}
	
	public void onDraw(Canvas canvas) {
		for (FootballPlayer dude : dudes){
			dude.onDraw(canvas);
		}
		ball.onDraw(canvas);
	}
}
