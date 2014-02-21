package com.gamemen.sportsapalooza;

import com.gamemen.sportsapalooza.Button.ButtonID;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView {
	
	private SurfaceHolder holder;
	private GameLoop gameLoop;
	
	private GameStates currentState;
	private boolean initialized;
	
	// Bitmaps
	private Bitmap btnUp;
	private Bitmap btnDown;
	
	// Objects
	private Button testButt;
	
	public enum GameStates {
		MAIN_MENU,
		SETUP,
		PLAYING,
		GAMEOVER,
		ABOUT
	}
	
	public GameView(Context context) {
		super(context);
		
		gameLoop = new GameLoop(this);
		
		holder = getHolder();
		holder.addCallback(new SurfaceHolder.Callback() {
			
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				boolean retry = true;
				gameLoop.setRunning(false);
				
				while(retry) {
					try {
						gameLoop.join();	// blocks thread
						retry = false;		// until not interrupted and sets true
					}
					catch (InterruptedException e) { }
				}
			}
			
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				gameLoop.setRunning(true);
				gameLoop.start();
			}
			
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) { }
		});
		
		// GAME STUFF
		////////////////////////////////////////////////////////
		
		currentState = GameStates.MAIN_MENU;
		initialized = false;
		loadResources();
	}
	
	private void loadResources() {
		btnUp = BitmapFactory.decodeResource(getResources(), R.drawable.btn_up);
		btnDown = BitmapFactory.decodeResource(getResources(), R.drawable.btn_down);
	}
	
	private void init() {
		testButt = new Button(this, btnUp, btnDown, ButtonID.PLAY, new PointF(10, 10), 0);
		this.setOnTouchListener(testButt);
		
		initialized = true;
	}
	
	protected void update(float deltaTime) {
		if (!initialized) {
			init();
		}
		
		if (currentState == GameStates.PLAYING) {
			// game.update()
		}
	}
	
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		
		// draw stuff
		testButt.onDraw(canvas);
	}
	
	/////////////////////////////////////////////////////////////
	// GETTERS AND SETTERS
	//////////////////////////////////////////////////////////////
	
	public GameStates getCurrentState() {
		return currentState;
	}
	public void setCurrentState(GameStates currentState) {
		this.currentState = currentState;
	}
	
}
