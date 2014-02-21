package com.gamemen.sportsapalooza;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class Button extends Sprite implements OnTouchListener {
	
	private Bitmap buttonUp, buttonDown;
	private ButtonID id;
	private ButtonState state;
	
	public enum ButtonID {
		PLAY,
		SOUND,
		ABOUT,
		REPLAY,
		MAIN_MENU,
		PAUSE;
	}
	
	public enum ButtonState {
		UP,
		HELD,
		TAPPED
	}
	
	public Button(GameView gameView, Bitmap buttonUp, Bitmap buttonDown, ButtonID ID, PointF pos) {
		this(gameView, buttonUp, buttonDown, ID, pos, 0);
	}
	
	public Button(GameView gameView, Bitmap buttonUp, Bitmap buttonDown, ButtonID ID, PointF pos, float rot) {
		super(gameView, buttonUp, pos, rot);
		
		this.buttonUp = buttonUp;
		this.buttonDown = buttonDown;
		this.id = ID;
		
		state = ButtonState.UP;
	}
	
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		System.out.println("onTouch");
		System.out.println("getX: " + event.getX());
		System.out.println("getRawX: " + event.getRawX());
		System.out.println("getXPrecision: " + event.getXPrecision());
		
		// if in hitbox
		switch(event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (state == ButtonState.UP) {
					state = ButtonState.HELD;
					if (bmp == buttonUp) {
						bmp = buttonDown;
					}
				}
				break;
				
			case MotionEvent.ACTION_UP:
				if (state == ButtonState.HELD) {
					state = ButtonState.TAPPED;
					if (bmp == buttonDown) {
						bmp = buttonUp;
					}
				}
				break;
				
			case MotionEvent.ACTION_MOVE:
				// if outside window, reset
				break;
				
			case MotionEvent.ACTION_CANCEL:
				resetState();
				break;
		}
		
		return true;
	}
	
	public ButtonState getState() {
		return state;
	}
	
	public void resetState() {
		state = ButtonState.UP;
		if (bmp == buttonDown) {
			bmp = buttonUp;
		}
	}
	
}
