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
	
	// Buttons
	private Button playBtn, aboutBtn, backBtn, soundBtn;
	
	// Objects
	private Sprite field, scoreBar;
	
	private Game game;
	
	public enum GameStates {
		MAIN_MENU,
		ABOUT,
		SETUP,
		PLAYING,
		GAME_OVER
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
		
		currentState = GameStates.MAIN_MENU;
		initialized = false;
	}
	
	public void onResume() {
		gameLoop = new GameLoop(this);
		gameLoop.setRunning(true);
		gameLoop.start();
		
		BitmapLoader.loadResources(metrics, getResources());
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
		
		game = new Game(this);
		
		scoreBar = new Sprite(this, BitmapLoader.bmpScoreBar);
		field = new Sprite(this, BitmapLoader.bmpField, new PointF(100, 40));
		
		playBtn = new Button(this, BitmapLoader.bmpBtnPlayUp, BitmapLoader.bmpBtnPlayDown, new PointF(SCREEN_SIZE.x / 2 - BitmapLoader.bmpBtnPlayUp.getWidth() / 2, SCREEN_SIZE.y / 5));
		aboutBtn = new Button(this, BitmapLoader.bmpBtnAboutUp, BitmapLoader.bmpBtnAboutDown, new PointF(SCREEN_SIZE.x / 2 - BitmapLoader.bmpBtnAboutUp.getWidth() / 2, SCREEN_SIZE.y / 5 * 2));
		backBtn = new Button(this, BitmapLoader.bmpBtnBackUp, BitmapLoader.bmpBtnBackDown, new PointF(SCREEN_SIZE.x / 2 - BitmapLoader.bmpBtnBackUp.getWidth() / 2, SCREEN_SIZE.y / 5 * 3));
		soundBtn = new Button(this, BitmapLoader.bmpBtnSoundUp, BitmapLoader.bmpBtnSoundDown, new PointF(SCREEN_SIZE.x / 2 - BitmapLoader.bmpBtnSoundUp.getWidth() / 2, SCREEN_SIZE.y / 5 * 4));
	}
	
	protected void update(float deltaTime) {
		if (!initialized) {
			init();
		}
		
		switch(currentState) {
			case MAIN_MENU:
				if (playBtn.isPressed()) {
					currentState = GameStates.PLAYING;
				}
				if (aboutBtn.isPressed()) {
					currentState = GameStates.ABOUT;
				}
				if (soundBtn.isPressed()) {
					GameOptions.toggleSound();
				}
				break;
				
			case ABOUT:
				if (backBtn.isPressed()) {
					currentState = GameStates.MAIN_MENU;
				}
				break;
				
			case SETUP:
				// nothing right now
				break;
				
			case PLAYING:
				game.update(deltaTime);
				break;
				
			case GAME_OVER:
				if (backBtn.isPressed()) {
					currentState = GameStates.MAIN_MENU;
				}
				break;
		}
		
	}
	
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		canvas.scale(metrics.widthPixels / SCREEN_SIZE.x, metrics.heightPixels / SCREEN_SIZE.y);
		
		scoreBar.onDraw(canvas);
		field.onDraw(canvas);
		
		switch(currentState) {
			case MAIN_MENU:
				playBtn.onDraw(canvas);
				aboutBtn.onDraw(canvas);
				soundBtn.onDraw(canvas);
				break;
				
			case ABOUT:
				// draw about stuff
				backBtn.onDraw(canvas);
				break;
				
			case SETUP:
				break;
				
			case PLAYING:
				game.onDraw(canvas);
				break;
				
			case GAME_OVER:
				game.onDraw(canvas);
				backBtn.onDraw(canvas);
				// DRAW GAME OVER STUFF
				break;
		}
		
	}
	
	public void gameOver(int leftScore, int rightScore) {
		this.currentState = GameStates.GAME_OVER;
		
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
