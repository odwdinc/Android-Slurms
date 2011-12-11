package com.isitbroken.Slurms;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

public class WeaponSpirit {

	float angle = 20;
	private AssetManager mgr;
	private Bitmap bitmap;
	boolean Crated = false;
	private Rect Pos;
	private Rect img;
	private PlayerSprite playerSprite;
	public boolean flip;
	private Bitmap bullitbitmap;
	private GameBoard gameBoard;
	private HashMap<String, Bullet> BullitMap = new HashMap<String, Bullet>();

	public WeaponSpirit(PlayerSprite playerSprite, GameBoard gameBoard,String Weapon, String Bullit) {
	    try {
	    	this.playerSprite = playerSprite;
			Matrix matrix = new Matrix();
		    matrix.postScale(0.70F, 0.70F);
			this.mgr = gameBoard.mgr;
			this.gameBoard = gameBoard;
			this.bitmap = BitmapFactory.decodeStream(mgr.open("sprites/"+Weapon+".png"));
			this.bullitbitmap = BitmapFactory.decodeStream(mgr.open("sprites/"+Bullit+".png"));
			this.bitmap = Bitmap.createBitmap(this.bitmap , 0, 0, this.bitmap.getWidth(), this.bitmap.getHeight(), matrix, true);
			this.bullitbitmap = Bitmap.createBitmap(this.bullitbitmap , 0, 0, this.bullitbitmap.getWidth(), this.bullitbitmap.getHeight(), matrix, true);
			this.Crated = true;
			this.img = new Rect(0,0,this.bitmap.getWidth(), this.bitmap.getHeight());
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void draw(Canvas canvas) {

		updatepos();

		Matrix mMatrix = new Matrix();

		mMatrix.setTranslate(Pos.left,Pos.top);
		mMatrix.postRotate(angle, 2*(bitmap.getWidth()/3), bitmap.getHeight()/2);
		// Rotating Bitmap

		if(flip){
			mMatrix.preScale(1, -1);
		}
		Bitmap rotatedBMP = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mMatrix, true);
		img = new Rect(0,0,rotatedBMP.getWidth(), rotatedBMP.getHeight());
		canvas.drawBitmap(rotatedBMP,img, Pos, null);

		Iterator<String> SpriteIterator = BullitMap.keySet().iterator();

		while (SpriteIterator.hasNext()) {
			String key = SpriteIterator.next();
			Bullet Thisbullit = BullitMap.get(key);
			if(Thisbullit.Ingame){
				Thisbullit.draw(canvas);
			}else{
				SpriteIterator.remove();
			}
		}

	}

	private void updatepos() {
		int Xloc;
		if(flip){
			Xloc = -2;
		}else{
			Xloc = 2;
		}

		int Left = playerSprite.getX() - (Xloc*(playerSprite.getWidth()/4));
		int Right = playerSprite.getX()+ bitmap.getWidth() - ((Xloc*playerSprite.getWidth()/4));

		int Top = playerSprite.getY();
		int Bottom = playerSprite.getY()+bitmap.getHeight();

		Pos = new Rect(Left,Top,Right,Bottom);
	}

	public void Fire() {
		if(BullitMap.size() < 2){
			int BulletX = (int)(Pos.centerX()-10*Math.cos(angle*(Math.PI/180)));
			int BulletY = (int)(Pos.centerY()-10*Math.sin(angle*(Math.PI/180)));

			Bullet thisbullit = new Bullet(bullitbitmap, gameBoard, BulletX, BulletY, angle, 100);
			BullitMap.put(thisbullit.ID,thisbullit);
		}
	}

}
