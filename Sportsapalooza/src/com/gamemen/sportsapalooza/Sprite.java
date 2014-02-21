package com.gamemen.sportsapalooza;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

// TODO:
// - apply rotation to draw somehow
// - possibly make SimpleRotation later

public class Sprite {
	protected GameView gameView;
	protected Bitmap bmp;
	protected PointF pos;
	protected float rot;
	protected RectF hitBox;
	
	public Sprite(GameView gameView, Bitmap bmp) {
		this(gameView, bmp, new PointF(0, 0), 0);
	}
	public Sprite(GameView gameView, Bitmap bmp, PointF pos) {
		this(gameView, bmp, pos, 0);
	}
	public Sprite(GameView gameView, Bitmap bmp, PointF pos, float rot) {
		this.gameView = gameView;
		this.bmp = bmp;
		this.pos = pos;
		this.rot = rot;
		
		if (bmp != null) { 													// this if doesn't solve anything
//			hitBox.set(pos.x, pos.y, bmp.getWidth(), bmp.getHeight());		// this line crashes shit
		}
	}
	
	public void onDraw(Canvas canvas) {
		canvas.drawBitmap(bmp, pos.x, pos.y, null);
	}
}
