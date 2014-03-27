package com.gamemen.sportsapalooza;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class Button extends Sprite implements OnTouchListener {
	
	private Bitmap buttonUp, buttonDown;
	private ButtonID id;
	private ButtonState state;
	private List<Integer> pointers;
	protected PointF pointerLoc;
	
	public enum ButtonID {
		PLAY,
		SOUND,
		ABOUT,
		REPLAY,
		MAIN_MENU,
		PAUSE,
		ENDZONE,
		DETONATOR
	}
	
	public enum ButtonState {
		UP,
		HELD,
		TAPPED
	}
	
	public Button(GameView gameView, Bitmap buttonUp, ButtonID ID, PointF pos) {
		this(gameView, buttonUp, buttonUp, ID, pos, 0);
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
		pointers = new ArrayList<Integer>();
		pointerLoc = new PointF(-1, -1);
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
							pointerLoc.set(pointerPos.x, pointerPos.y);
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

	public boolean isPressed() {
		if (state == ButtonState.TAPPED) {
			resetState();
			return true;
		}
		return false;
	}
	
	protected void resetState() {
		state = ButtonState.UP;
		pointers.clear();
		pointerLoc.set(-1, -1);
		if (getBmp() == buttonDown) {
			setBmp(buttonUp);
		}
	}
	
}
