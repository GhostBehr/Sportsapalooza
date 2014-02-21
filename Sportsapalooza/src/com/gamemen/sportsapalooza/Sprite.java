package com.gamemen.sportsapalooza;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;

// TODO:
// - apply rotation to draw somehow
// - possibly make SimpleRotation later

public class Sprite {
	protected GameView gameView;
	protected Bitmap bmp;
	protected PointF pos;
	protected float rot;
	
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
	}
	
	public void onDraw(Canvas canvas) {
		canvas.drawBitmap(bmp, pos.x, pos.y, null);
	}
}
