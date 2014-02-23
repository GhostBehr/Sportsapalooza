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
		PointF touchPos = new PointF(event.getX(), event.getY());
		
		if (getBounds().contains(touchPos.x, touchPos.y)) {
			switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if (state == ButtonState.UP) {
						state = ButtonState.HELD;
						if (getBmp() == buttonUp) {
							setBmp(buttonDown);
						}
						
						System.out.println("BtnDown");
					}
					break;
					
				case MotionEvent.ACTION_UP:
					if (state == ButtonState.HELD) {
						state = ButtonState.TAPPED;
						if (getBmp() == buttonDown) {
							setBmp(buttonUp);
						}
						
						System.out.println("BtnUp");
					}
					break;
					
				case MotionEvent.ACTION_CANCEL:
					resetState();
					
					System.out.println("CancelBtn");
					break;
			}
		}
		else {		// Moved off of button while held
			if (event.getAction() == MotionEvent.ACTION_MOVE && state == ButtonState.HELD) {
				resetState();
				
				System.out.println("BtnMoveAway");
			}
		}
		
		return true;
	}
	
	public ButtonState getState() {
		return state;
	}
	
	public void resetState() {
		state = ButtonState.UP;
		if (getBmp() == buttonDown) {
			setBmp(buttonUp);
		}
	}
	
}
