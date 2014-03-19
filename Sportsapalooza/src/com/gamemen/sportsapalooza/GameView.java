package com.gamemen.sportsapalooza;

import com.gamemen.sportsapalooza.Button.ButtonID;
import com.gamemen.sportsapalooza.Button.ButtonState;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
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
	private Options bitmapOptions;
	
	private Bitmap bmpField, bmpEndzone, bmpScoreBar;
	
	// Objects
	private Sprite field, endzoneLeft, endzoneRight, scoreBar;
	
	private Game game;
	
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
		
		bitmapOptions = new Options();
		bitmapOptions.inScaled = false;
		loadResources(bitmapOptions);
		
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
	
	private void loadResources(Options opt) {
		bmpField = BitmapFactory.decodeResource(getResources(), R.drawable.field, opt);
		bmpEndzone = BitmapFactory.decodeResource(getResources(), R.drawable.endzone, opt);
		bmpScoreBar = BitmapFactory.decodeResource(getResources(), R.drawable.score, opt);
	}
	
	private void init() {
		initialized = true;
		
		scoreBar = new Sprite(this, bmpScoreBar);
		endzoneLeft = new Sprite(this, bmpEndzone, new PointF(0, bmpScoreBar.getHeight()));
		field = new Sprite(this, bmpField, new PointF(bmpEndzone.getWidth(), bmpScoreBar.getHeight()));
		endzoneRight = new Sprite(this, bmpEndzone, new PointF(bmpEndzone.getWidth() + bmpField.getWidth(), bmpScoreBar.getHeight()));
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
		
		scoreBar.onDraw(canvas);
		field.onDraw(canvas);
		endzoneLeft.onDraw(canvas);
		endzoneRight.onDraw(canvas);
		
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
