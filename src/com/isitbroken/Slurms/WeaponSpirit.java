package com.isitbroken.Slurms;

import java.io.IOException;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

public class WeaponSpirit {

	float angle = 20;
	private AssetManager mgr;
	private Bitmap bitmap;
	boolean Crated = false;
	private Rect Pos;
	private Rect img;
	private PlayerSprite playerSprite;

	public WeaponSpirit(PlayerSprite playerSprite, GameBoard gameBoard) {
	    try {
	    	this.playerSprite = playerSprite;
			Matrix matrix = new Matrix();
		    matrix.postScale(0.70F, 0.70F);
			this.mgr = gameBoard.mgr;
			this.bitmap = BitmapFactory.decodeStream(mgr.open("sprites/Weapon1.png"));
			this.bitmap = Bitmap.createBitmap(this.bitmap , 0, 0, this.bitmap.getWidth(), this.bitmap.getHeight(), matrix, true);
			this.Crated = true;
			this.img = new Rect(0,0,this.bitmap.getWidth(), this.bitmap.getHeight());
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void draw(Canvas canvas) {
		Matrix mtx = new Matrix();
		//mtx.postRotate(90);
		mtx.postRotate(this.angle, 50, 15);
		// Rotating Bitmap
		Bitmap rotatedBMP = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mtx, true);

		updatepos();

		canvas.drawBitmap(rotatedBMP,img, Pos, null);

	}

	private void updatepos() {
		int Left = playerSprite.getX() - (playerSprite.getWidth()/3);
		int Right = playerSprite.getX()+ bitmap.getWidth() - (playerSprite.getWidth()/3);

		int Top = playerSprite.getY()+(playerSprite.getHeight()/3);
		int Bottom = playerSprite.getY()+bitmap.getHeight()+(playerSprite.getHeight()/3);

		Pos = new Rect(Left,Top,Right,Bottom);
	}

}
