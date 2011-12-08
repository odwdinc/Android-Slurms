package com.isitbroken.Slurms;

import android.graphics.AvoidXfermode;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
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



    public Sprite(Bitmap hitmap, int x, int y, int width, int height) {
		bitmap = Bitmap.createScaledBitmap(hitmap, width, height, false);
		X = x;
		Y = y;
		Width = width;
		Height = height;
		spriteWidth = bitmap.getWidth();
		spriteHeight = bitmap.getHeight();
		sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);

		BoundRect = new Rect(getX(), getY(), getX() + Width, getY() + Height);
	}

    public void draw(Canvas canvas) {
		// where to draw the sprite
    	BoundRect = new Rect(getX(), getY(), getX() + Width, getY() + Height);

		canvas.drawBitmap(bitmap, sourceRect, BoundRect, null);
	}

	public Rect getBounds() {
		return this.BoundRect;
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
		BoundRect = new Rect(getX(), getY(), getX() + Width, getY() + Height);
	}

	public void Fall(int height2) {
		int Tempost = getY()+1;

		if(Tempost < height2){
			SetY(Tempost);
		}
	}

	public void Addhole(float f, float g, int k) {
		Canvas thiscan = new Canvas();
		thiscan.setBitmap(bitmap);
		Paint thisp = new Paint();
		thisp.setARGB(255, 0, 10, 30);
		thiscan.drawCircle( f, g, (float)k, thisp);

		Paint p = new Paint();
		p.setARGB(255, 0, 10, 30); // ARGB for the color, in this case red
		int removeColor = p.getColor();
		p.setAlpha(0);
		p.setXfermode(new AvoidXfermode(removeColor, 0, AvoidXfermode.Mode.TARGET));
		thiscan.drawPaint(p);

	}

}
