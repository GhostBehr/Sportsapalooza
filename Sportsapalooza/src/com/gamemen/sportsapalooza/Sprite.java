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
	private Bitmap bmp;
	protected PointF pos;
	private float rot;
	private RectF bounds;
	
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
		
		bounds = new RectF(0, 0, bmp.getWidth(), bmp.getHeight());
	}
	
	public void onDraw(Canvas canvas) {
		PointF origin = getOrigin();
		canvas.rotate(rot, origin.x, origin.y);
		canvas.drawBitmap(bmp, pos.x, pos.y, null);
		canvas.rotate(-rot, origin.x, origin.y);
	}
	
	//////////////////////////////////////////////////////
	// GETTERS AND SETTERS
	/////////////////////////////////////////////////////
	public PointF getOrigin() {
		return new PointF(pos.x + bounds.centerX(), pos.y + bounds.centerY());
	}
	
	public RectF getBounds() {
		return new RectF(pos.x, pos.y, pos.x + bounds.width(), pos.y + bounds.height());
	}
	
	public Bitmap getBmp() {
		return bmp;
	}
	
	protected void setBmp(Bitmap bmp) {
		this.bmp = bmp;
	}
	
	protected void setBmp(Bitmap bmp, boolean setBounds) {
		this.bmp = bmp;
		bounds = new RectF(0, 0, bmp.getWidth(), bmp.getHeight());
	}
	
	public PointF getPos() {
		return pos;
	}
	
	public float getRot() {
		return rot;
	}
	
	public void setRot(float rot) {
		this.rot = rot % 360f;
	}
	
}
