package com.gamemen.sportsapalooza;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class Button extends Sprite implements OnTouchListener {
	
	private Bitmap buttonUp;
	private Bitmap buttonDown;
	private String buttonText;
	private ButtonID id;
	
	public enum ButtonID {
		PLAY,
		SOUND,
		ABOUT,
		REPLAY,
		MAIN_MENU,
		PAUSE;
	}
	
	public Button(GameView gameView, Bitmap buttonUp, Bitmap buttonDown, String buttonText, ButtonID ID, PointF pos, float rot) {
		super(gameView, buttonUp, pos, rot);
		
		this.buttonUp = buttonUp;
		this.buttonDown = buttonDown;
		this.buttonText = buttonText;
		this.id = ID;
	}

	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		System.out.println(buttonText);
		
		return true;
	}
	
}
