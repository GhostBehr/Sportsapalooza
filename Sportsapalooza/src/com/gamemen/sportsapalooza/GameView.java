package com.gamemen.sportsapalooza;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
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
	private Button playBtn, aboutBtn, backBtn, soundBtn, pauseBtn;
	
	// Objects
	private Sprite fieldSprite, scoreBar, aboutSprite, pausedSprite, leftWinsSprite, rightWinsSprite, tieSprite;
	
	private Game game;
	
	public enum GameStates {
		MAIN_MENU,
		ABOUT,
		PLAYING,
		PAUSED,
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
		fieldSprite = new Sprite(this, BitmapLoader.bmpField, new PointF(100, 40));
		aboutSprite = new Sprite(this, BitmapLoader.bmpAbout, new PointF(SCREEN_SIZE.x / 2 - BitmapLoader.bmpAbout.getWidth() / 2, SCREEN_SIZE.y / 2 - BitmapLoader.bmpAbout.getHeight() / 2));
		pausedSprite = new Sprite(this, BitmapLoader.bmpPaused, new PointF(SCREEN_SIZE.x / 2 - BitmapLoader.bmpPaused.getWidth() / 2, SCREEN_SIZE.y / 2 - BitmapLoader.bmpPaused.getHeight() / 2));
		leftWinsSprite = new Sprite(this, BitmapLoader.bmpLeftWins, new PointF(SCREEN_SIZE.x / 2 - BitmapLoader.bmpLeftWins.getWidth() / 2, SCREEN_SIZE.y / 2 - BitmapLoader.bmpLeftWins.getHeight() / 2));
		rightWinsSprite = new Sprite(this, BitmapLoader.bmpRightWins, new PointF(SCREEN_SIZE.x / 2 - BitmapLoader.bmpRightWins.getWidth() / 2, SCREEN_SIZE.y / 2 - BitmapLoader.bmpRightWins.getHeight() / 2));
		tieSprite = new Sprite(this, BitmapLoader.bmpTie, new PointF(SCREEN_SIZE.x / 2 - BitmapLoader.bmpTie.getWidth() / 2, SCREEN_SIZE.y / 2 - BitmapLoader.bmpTie.getHeight() / 2));
		
		playBtn = new Button(this, BitmapLoader.bmpBtnPlayUp, BitmapLoader.bmpBtnPlayDown, new PointF(SCREEN_SIZE.x / 2 - BitmapLoader.bmpBtnPlayUp.getWidth() / 2, SCREEN_SIZE.y / 3), false);
		aboutBtn = new Button(this, BitmapLoader.bmpBtnAboutUp, BitmapLoader.bmpBtnAboutDown, new PointF(SCREEN_SIZE.x / 4 - BitmapLoader.bmpBtnAboutUp.getWidth() / 2, SCREEN_SIZE.y / 3 * 2), false);
		backBtn = new Button(this, BitmapLoader.bmpBtnBackUp, BitmapLoader.bmpBtnBackDown, new PointF(SCREEN_SIZE.x / 2 - BitmapLoader.bmpBtnBackUp.getWidth() / 2, SCREEN_SIZE.y / 3 * 2), false);
		soundBtn = new Button(this, BitmapLoader.bmpBtnSoundUp, BitmapLoader.bmpBtnSoundDown, new PointF(SCREEN_SIZE.x / 4 * 3 - BitmapLoader.bmpBtnSoundUp.getWidth() / 2, SCREEN_SIZE.y / 3 * 2), false);
		pauseBtn = new Button(this, BitmapLoader.bmpBtnPauseUp, BitmapLoader.bmpBtnPauseDown, new PointF(SCREEN_SIZE.x / 2 - BitmapLoader.bmpBtnPauseUp.getWidth() / 2, 0), false);
		
		setCurrentState(GameStates.MAIN_MENU);
	}
	
	protected void update(float deltaTime) {
		if (!initialized) {
			init();
		}
		
		switch(currentState) {
			case MAIN_MENU:
				if (playBtn.isPressed()) {
					setCurrentState(GameStates.PLAYING);
				}
				if (aboutBtn.isPressed()) {
					setCurrentState(GameStates.ABOUT);
				}
				if (soundBtn.isPressed()) {
					GameOptions.toggleSound();
				}
				break;
				
			case ABOUT:
				if (backBtn.isPressed()) {
					if (game.isPaused()) {
						setCurrentState(GameStates.PAUSED);
					}
					else {
						setCurrentState(GameStates.MAIN_MENU);
					}
				}
				break;
				
			case PAUSED:
				if (aboutBtn.isPressed()) {
					setCurrentState(GameStates.ABOUT);
				}
				if (soundBtn.isPressed()) {
					GameOptions.toggleSound();
				}
				if (backBtn.isPressed()) {
					setCurrentState(GameStates.PLAYING);
				}
				break;
				
			case PLAYING:
				if (pauseBtn.isPressed()) {
					setCurrentState(GameStates.PAUSED);
				}
				game.update(deltaTime);
				break;
				
			case GAME_OVER:
				if (backBtn.isPressed()) {
					setCurrentState(GameStates.MAIN_MENU);
					game = new Game(this);
				}
				break;
		}
		
	}
	
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		canvas.scale(metrics.widthPixels / SCREEN_SIZE.x, metrics.heightPixels / SCREEN_SIZE.y);
		
		scoreBar.onDraw(canvas);
		fieldSprite.onDraw(canvas);
		
		switch(currentState) {
			case MAIN_MENU:
				playBtn.onDraw(canvas);
				aboutBtn.onDraw(canvas);
				soundBtn.onDraw(canvas);
				break;
				
			case ABOUT:
				// can still be called from pause screen
				if (game.isPaused()) {
					game.onDraw(canvas);
				}
				
				aboutSprite.onDraw(canvas);
				backBtn.onDraw(canvas);
				break;
				
			case PAUSED:
				game.onDraw(canvas);
				pausedSprite.onDraw(canvas);
				aboutBtn.onDraw(canvas);
				soundBtn.onDraw(canvas);
				backBtn.onDraw(canvas);
				break;
				
			case PLAYING:
				game.onDraw(canvas);
				pauseBtn.onDraw(canvas);
				break;
				
			case GAME_OVER:
				game.onDraw(canvas);
				backBtn.onDraw(canvas);
				
				int[] scores = game.getScores();
				if (scores[0] > scores[1]) {
					leftWinsSprite.onDraw(canvas);
				}
				else if (scores[0] < scores[1]) {
					rightWinsSprite.onDraw(canvas);
				}
				else {
					tieSprite.onDraw(canvas);
				}
				
				break;
		}
		
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
		for (int i = onTouchListeners.size() - 1; i >= 0; --i) {
			onTouchListeners.get(i).onTouch(v, event);
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
		
		// Activate/disable buttons, pause
		switch(currentState) {
			case MAIN_MENU:
				playBtn.setActive(true);
				aboutBtn.setActive(true);
				backBtn.setActive(false);
				soundBtn.setActive(true);
				pauseBtn.setActive(false);
				break;
				
			case ABOUT:
				playBtn.setActive(false);
				aboutBtn.setActive(false);
				backBtn.setActive(true);
				soundBtn.setActive(false);
				pauseBtn.setActive(false);
				break;
				
			case PAUSED:
				playBtn.setActive(false);
				aboutBtn.setActive(true);
				backBtn.setActive(true);
				soundBtn.setActive(true);
				pauseBtn.setActive(false);
				
				game.setPaused(true);
				break;
				
			case PLAYING:
				playBtn.setActive(false);
				aboutBtn.setActive(false);
				backBtn.setActive(false);
				soundBtn.setActive(false);
				pauseBtn.setActive(true);
				
				game.setPaused(false);
				break;
				
			case GAME_OVER:
				playBtn.setActive(false);
				aboutBtn.setActive(false);
				backBtn.setActive(true);
				soundBtn.setActive(false);
				pauseBtn.setActive(false);
				break;
		}
	}
	
}
