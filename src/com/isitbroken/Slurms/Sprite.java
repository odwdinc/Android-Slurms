package com.isitbroken.Slurms;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {

	private Bitmap bitmap;      // the animation sequence
    private Rect sourceRect;    // the rectangle to be drawn from the animation bitmap
    private int spriteWidth;    // the width of the sprite to calculate the cut out rectangle
    private int spriteHeight;   // the height of the sprite

    private int X;              // the X coordinate of the object (top left of the image)
    private int Y;              // the Y coordinate of the object (top left of the image)
	private Rect BoundRect;
	private int Width;
	private int Height;
	String ID;
	GameBoard Gameactivity;


    public Sprite(Bitmap bitmap2, int x, int y, GameBoard gameBoard) {

		setBitmap(bitmap2);
		SetX(x);
		Y = y;
		ID = "ID:"+getX()+":"+Y+bitmap2.hashCode();
		setWidth(bitmap2.getWidth());
		setHeight(bitmap2.getHeight());
		spriteWidth = getBitmap().getWidth();
		spriteHeight = getBitmap().getHeight();
		setSourceRect(new Rect(0, 0, spriteWidth, spriteHeight));
		Gameactivity = gameBoard;
		calBound();
	}

    public void calBound() {
    	setBoundRect(new Rect(getX() , getY(), getX() + getWidth() , getY() + getHeight()));
	}

	public void draw(Canvas canvas) {
		// where to draw the sprite
		calBound();
		canvas.drawBitmap(getBitmap(), getSourceRect(), BoundRect, null);
	}

	public Rect getBounds() {
		return this.getBoundRect();
	}

	public int getY() {
		return Y;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public int getX() {
		return X;
	}

	public void SetY(int i) {
		Y = i;
		calBound();
	}

	public int getHeight() {
		return Height;
	}

	public void setHeight(int height) {
		Height = height;
	}

	public int getWidth() {
		return Width;
	}

	public void setWidth(int width) {
		Width = width;
	}

	public Rect getBoundRect() {
		return BoundRect;
	}

	public void setBoundRect(Rect boundRect) {
		BoundRect = boundRect;
	}

	public Rect getSourceRect() {
		return sourceRect;
	}

	public void setSourceRect(Rect sourceRect) {
		this.sourceRect = sourceRect;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public void SetX(int x) {
		X = x;
	}

}
