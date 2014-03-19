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

	private Bitmap dudeSprite;
	private Bitmap dugoutSprite;
	
	private List<FootballPlayer> dudeList;
	private List<Button> detonators;
	
	private Football ball;
	private Sprite dugout;
	
	private final float blastForce = 1.0f;
	private final float blastRadius = 1.0f;
	private final float dugoutOffset = 150f;
	
	public Endzone(GameView gameView, Bitmap buttonUp, Bitmap buttonDown, ButtonID ID, PointF pos, Bitmap dudeSprite, Football ball) {
		super(gameView, buttonUp, buttonDown, ID, pos);
		
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
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int actionIndex = event.getActionIndex();
		Integer pointerID = event.getPointerId(actionIndex);
		PointF pointerPos = new PointF(event.getX(actionIndex), event.getY(actionIndex));
		
		if (getBounds().contains(pointerPos.x, pointerPos.y)) {		// event in bounds
			switch(event.getActionMasked()) {
				case MotionEvent.ACTION_POINTER_DOWN:
				case MotionEvent.ACTION_DOWN:
					pointers.add(pointerID);						// add pointer to list
					
					if (state == ButtonState.UP) {
						state = ButtonState.HELD;
						if (getBmp() == buttonUp) {
							setBmp(buttonDown);
						}
					}
					break;
					
				case MotionEvent.ACTION_POINTER_UP:
				case MotionEvent.ACTION_UP:
					if (state == ButtonState.HELD) {
						if (pointers.contains(pointerID)) {
							pointers.remove(pointerID);
						}
						
						if (pointers.isEmpty()) {					// if no pointers holding a held button, it was tapped
							state = ButtonState.TAPPED;
							if (getBmp() == buttonDown) {
								setBmp(buttonUp);
							}
						}
					}
					break;
					
				case MotionEvent.ACTION_CANCEL:
					resetState();
					break;
			}
		}
		else if (event.getActionMasked() == MotionEvent.ACTION_MOVE && state == ButtonState.HELD) {	// if ACTION_MOVE away from a held button
			int pointerCount = event.getPointerCount();
			
			for (int p = 0; p < pointerCount; ++p) {				// for each ACTION_MOVE pointer
				pointerID = event.getPointerId(p);
				pointerPos.set(event.getX(p), event.getY(p));

				if (!getBounds().contains(pointerPos.x, pointerPos.y)) {		// ignore pointers still on button
					if (pointers.contains(pointerID)) {				// if pointer was holding button, remove
						pointers.remove(pointerID);
					}
					if (pointers.isEmpty()) {						// if no remaining pointers, reset
						resetState();
					}
				}
			}
		}
		
		return true;
	}

	public void Explosion(PointF dudePos) {
		PointF direction = new PointF(dudePos.x - ball.getPosition().x, dudePos.y - ball.getPosition().y);
		float directionMagnitude = (float)Math.sqrt(direction.x * direction.x + direction.y * direction.y);
		
		PointF force = new PointF((direction.x/directionMagnitude) * blastForce, (direction.x/directionMagnitude) * blastForce);
		
		ball.addImpulseForce(force);
	}
	
	public void update(float deltaTime) {
		for (FootballPlayer dude : dudeList) {
			dude.update(deltaTime);
			if (dude.isExploding()) {
				Explosion(dude.pos);
				dudeList.remove(dude);
			}
		}
		dugout.pos = new PointF(dugout.pos.x, ball.pos.y - dugoutOffset);
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