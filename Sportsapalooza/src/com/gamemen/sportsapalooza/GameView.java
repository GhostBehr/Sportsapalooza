package com.gamemen.sportsapalooza;

import com.gamemen.sportsapalooza.Button.ButtonID;
import com.gamemen.sportsapalooza.Button.ButtonState;

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
	
	private Game game;
	
	// Bitmaps
	private Bitmap btnUp;
	private Bitmap btnDown;
	
	// Objects
	
	public enum GameStates {
		MAIN_MENU,
		SETUP,
		PLAYING,
		GAMEOVER,
		ABOUT
	}
	
	public GameView(Context context) {
		super(context);
		
		holder = getHolder();
		holder.addCallback(new SurfaceHolder.Callback() {
			
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) { }
			
			@Override
			public void surfaceCreated(SurfaceHolder holder) { }
			
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) { }
		});
		
		// GAME STUFF
		////////////////////////////////////////////////////////
		
		currentState = GameStates.PLAYING;
		initialized = false;
		loadResources();
		
		game = new Game(this);
	}
	
	public void onResume() {
		gameLoop = new GameLoop(this);
		gameLoop.setRunning(true);
		gameLoop.start();
		
		// possibly should move loadResources or whatever to here
	}
	
	public void onPause() {
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
	
	private void loadResources() {
		btnUp = BitmapFactory.decodeResource(getResources(), R.drawable.btn_up);
		btnDown = BitmapFactory.decodeResource(getResources(), R.drawable.btn_down);
	}
	
	private void init() {
		initialized = true;
	}
	
	protected void update(float deltaTime) {
		if (!initialized) {
			init();
		}
		
		// BUTTONS
		/////////////////////////////////////
		
		// UPDATES
		/////////////////////////////////////
		
		if (currentState == GameStates.PLAYING) {
			game.update(deltaTime);
		}
	}
	
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		
		if (currentState == GameStates.PLAYING){
			game.onDraw(canvas);
		}
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
