package com.gamemen.sportsapalooza;

import android.graphics.Canvas;

public class GameLoop extends Thread {

	private GameView view;
	private boolean running = false;
	private long lastTime;

	public GameLoop(GameView view) {
		this.view = view;
	}

	public void setRunning(boolean run) {
		lastTime = System.nanoTime();
		running = run;
	}

	@Override
	public void run() {
		while (running) {
			/// UPDATE ///
			long nextTime = System.nanoTime();	// saved locally for consistency
			float deltaTime = (float)((nextTime - lastTime) * 0.000000001);
			lastTime = nextTime;
			
			try {
				view.update(deltaTime);
			}
			catch (Exception e) { }
			
			/// DRAW ///
			Canvas c = null;
			try {
				c = view.getHolder().lockCanvas();
				synchronized (view.getHolder()) {
					view.onDraw(c);
				}
			}
			catch (Exception e) { }
			finally {
				if (c != null) {
					view.getHolder().unlockCanvasAndPost(c);
				}
			}
		}
	}
	
	
	// COPE PASTE UNDER HERE
	/////////////////////////////////////
//	private GameView view;
//	private boolean running = false;
//	
//	public GameLoop(GameView view) {
//		this.view = view;
//	}
//
//	public void setRunning(boolean run) {
//		running = run;
//	}
//	
//
//	// desired fps
//	private final static int 	MAX_FPS = 50;	
//	// maximum number of frames to be skipped
//	private final static int	MAX_FRAME_SKIPS = 5;	
//	// the frame period
//	private final static int	FRAME_PERIOD = 1000 / MAX_FPS;	
//
//
//	@Override
//	public void run() {
//		Canvas canvas;
//
//		long beginTime;		// the time when the cycle begun
//		long timeDiff;		// the time it took for the cycle to execute
//		int sleepTime;		// ms to sleep (<0 if we're behind)
//		int framesSkipped;	// number of frames being skipped 
//
//		sleepTime = 0;
//		
//		while (running) {
//			canvas = null;
//			// try locking the canvas for exclusive pixel editing
//			// in the surface
//			try {
//				canvas = view.getHolder().lockCanvas();
//				synchronized (view.getHolder()) {
//					beginTime = System.currentTimeMillis();
//					framesSkipped = 0;	// resetting the frames skipped
//					// update game state 
//					view.update(0.02f);
//					// render state to the screen
//					// draws the canvas on the panel
//					view.onDraw(canvas);				
//					// calculate how long did the cycle take
//					timeDiff = System.currentTimeMillis() - beginTime;
//					// calculate sleep time
//					sleepTime = (int)(FRAME_PERIOD - timeDiff);
//					
//					if (sleepTime > 0) {
//						// if sleepTime > 0 we're OK
//						try {
//							// send the thread to sleep for a short period
//							// very useful for battery saving
//							Thread.sleep(sleepTime);	
//						} catch (InterruptedException e) {}
//					}
//					
//					while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
//						// we need to catch up
//						// update without rendering
//						view.update(0.02f); 
//						// add frame period to check if in next frame
//						sleepTime += FRAME_PERIOD;	
//						framesSkipped++;
//					}
//				}
//			} catch (Exception e) {
//			} finally {
//				// in case of an exception the surface is not left in 
//				// an inconsistent state
//				if (canvas != null) {
//					view.getHolder().unlockCanvasAndPost(canvas);
//				}
//			}	// end finally
//		}
//	}

	

}
