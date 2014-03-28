package com.gamemen.sportsapalooza;

import java.util.ArrayList;
import java.util.List;

import com.gamemen.sportsapalooza.Button.ButtonState;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class GameView extends SurfaceView implements OnTouchListener {
	
	private SurfaceHolder holder;
	private GameLoop gameLoop;
	private List<OnTouchListener> onTouchListeners;
	
	private GameStates currentState;
	private boolean initialized;
	
	public static PointF SCREEN_SIZE;
	private DisplayMetrics metrics;
	
	// Objects
	private Sprite field, scoreBar;
	
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
		
		SCREEN_SIZE = new PointF(800, 480);
		metrics = getResources().getDisplayMetrics();
		
		onTouchListeners = new ArrayList<OnTouchListener>();
		setOnTouchListener(this);
		
		// GAME STUFF
		////////////////////////////////////////////////////////
		
		currentState = GameStates.PLAYING;
		initialized = false;
		
		BitmapLoader.loadResources(metrics, getResources());
		
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
	
	private void init() {
		initialized = true;
		
		scoreBar = new Sprite(this, BitmapLoader.bmpScoreBar);
		field = new Sprite(this, BitmapLoader.bmpField, new PointF(100, 40));
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
		canvas.scale(metrics.widthPixels / SCREEN_SIZE.x, metrics.heightPixels / SCREEN_SIZE.y);
		
		scoreBar.onDraw(canvas);
		field.onDraw(canvas);
		
		if (currentState == GameStates.PLAYING){
			game.onDraw(canvas);
		}
	}
	
	public void gameOver(int leftScore, int rightScore) {
		this.currentState = GameStates.GAMEOVER;
		
		// Pass Game Over screen the score values
	}
	
	public PointF worldPointToLocal(PointF worldPos) {
		return new PointF(worldPos.x * SCREEN_SIZE.x / metrics.widthPixels, worldPos.y * SCREEN_SIZE.y / metrics.heightPixels);
	}
	
	/////////////////////////////////////////////////////////////
	// LISTENERS
	//////////////////////////////////////////////////////////////
	
	public void addOnTouchListener(OnTouchListener listener) {
		onTouchListeners.add(listener);
	}
	
	public void removeOnTouchListener(OnTouchListener listener) {
		onTouchListeners.remove(listener);
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		for (OnTouchListener listener : onTouchListeners) {
			listener.onTouch(v, event);
		}
		
		return true;
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
