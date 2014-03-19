package com.gamemen.sportsapalooza;

import java.util.ArrayList;
import java.util.List;

import com.gamemen.sportsapalooza.Button.ButtonID;
import com.gamemen.sportsapalooza.Button.ButtonState;
import com.gamemen.sportsapalooza.Players.PlayerID;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

public class Game {
	
	private GameView gameView;
	
	private List<FootballPlayer> dudes;
	private Football ball;
	private Sprite leftDugout, rightDugout;
	private Endzone leftEndzone, rightEndzone;
	
	private RectF bounds;
	
	private float dugoutOffset;
	private final float blastForce = 1.0f;
	private final float blastRadius = 1.0f;
	
	Bitmap dudeSprite, dugoutSprite, ballSprite;
	
	public Game(GameView gameView) {
		this.gameView = gameView;
		
		loadResources();
		
		dudes = new ArrayList<FootballPlayer>(6);
		
		ball = new Football(gameView, ballSprite, new PointF(gameView.getMeasuredWidth()/2, gameView.getMeasuredHeight()/2));
		
		leftDugout = new Sprite(gameView, dugoutSprite, new PointF(gameView.getMeasuredHeight() + dugoutSprite.getHeight(), 0));
		rightDugout = new Sprite(gameView, dugoutSprite, new PointF(dugoutSprite.getHeight(), 0));
		
		dugoutOffset = 150f;
		
		leftEndzone = new Endzone(gameView, dugoutSprite, dugoutSprite, ButtonID.ENDZONE, new PointF(0, 0)); //No images 'cause invisible
		rightEndzone = new Endzone(gameView, dugoutSprite, dugoutSprite, ButtonID.ENDZONE, new PointF(gameView.getMeasuredHeight() + 20, 0));
		
		bounds = new RectF(0, 0, gameView.getMeasuredWidth(), gameView.getMeasuredHeight());
	}
	
	private void loadResources() {
		dudeSprite = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.dude);
		ballSprite = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.ball);
		dugoutSprite = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.dugout);
	}
	
	public void explosion(PointF dudePos) {
		PointF direction = new PointF(dudePos.x - ball.getPosition().x, dudePos.y - ball.getPosition().y);
		float directionMagnitude = (float)Math.sqrt(direction.x * direction.x + direction.y * direction.y);
		
		PointF force = new PointF((direction.x/directionMagnitude) * blastForce, (direction.x/directionMagnitude) * blastForce);
		
		ball.addImpulseForce(force);
	}
	
	public void update(float deltaTime) {
		//make sure bounds.contains(RectF) returns true for all the stuff?
		for (FootballPlayer dude : dudes) {
			dude.update(deltaTime);
			if (dude.isExploding()) {
				explosion(dude.getPosition());
			}
		}
		ball.update(deltaTime);
		leftDugout.pos = new PointF(leftDugout.pos.x, ball.pos.y - dugoutOffset);
		rightDugout.pos = new PointF(rightDugout.pos.x, ball.pos.y + dugoutOffset);
	}
	
	public void onDraw(Canvas canvas) {
		if (leftEndzone.isPressed()) {
			dudes.add(new FootballPlayer(gameView, dudeSprite, new PointF(0, 0), Players.getColor(PlayerID.LEFT)));
		}
        
		if (rightEndzone.isPressed()) {
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