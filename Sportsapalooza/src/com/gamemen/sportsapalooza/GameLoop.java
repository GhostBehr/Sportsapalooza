package com.gamemen.sportsapalooza;

import android.graphics.Canvas;

public class GameLoop extends Thread {

	private GameView view;
	private boolean running = false;
	private long lastTime;

	public GameLoop(GameView view) {
		this.view = view;
		lastTime = System.nanoTime();
	}

	public void setRunning(boolean run) {
		running = run;
	}

	@Override
	public void run() {
		while (running) {
			/// UPDATE ///
			long nextTime = System.nanoTime();	// saved locally for consistency
			float deltaTime = (float)((nextTime - lastTime) * 0.000000001);
			lastTime = nextTime;

			view.update(deltaTime);
			
			/// DRAW ///
			Canvas c = null;
			try {
				c = view.getHolder().lockCanvas();
				synchronized (view.getHolder()) {
					view.onDraw(c);
				}
			}
			finally {
				if (c != null) {
					view.getHolder().unlockCanvasAndPost(c);
				}
			}
		}
	}

}
