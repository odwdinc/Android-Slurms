package com.isitbroken.Slurms;

import com.stackoverflow.arcone.CollisionUtil;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class Bullet extends Sprite{

	boolean Ingame;
	private double dirx;
	private double diry;
	private double gravity = 2;
	private int ExploidLevel = 40;


	public Bullet(Bitmap bitmap2, GameBoard gameBoard, int x, int y, float angle, float firepower) {
		super(bitmap2, x, y, gameBoard);
		Ingame = true;
		dirx = Math.cos(angle*Math.PI/180)*firepower;
		diry = Math.sin(angle*Math.PI/180)*firepower;
	}



	@Override
	public void draw(Canvas canvas) {
		diry -= gravity ;
		SetX((int) (getX()-(dirx/10)));
		SetY((int) (getY()-(diry/10)));

		calBound();
		TestBullet();

		canvas.drawBitmap(getBitmap(), getSourceRect(), getBoundRect(), null);
	}



	private void TestBullet() {
		if(getBoundRect().right >= Gameactivity.Width || getBoundRect().left <= 0 || getBoundRect().bottom >= Gameactivity.Height ||getBoundRect().top <= 0){
			Ingame = false;
		}else{
			if(isCollisionDetected(Gameactivity.levePlatform)){
				Gameactivity.levePlatform.Addhole(getX(), getY(), ExploidLevel );
				Ingame = false;
			}
		}

	}


	public boolean isCollisionDetected(PlatformSprite platform){
		Rect Bulletbounds = this.getBounds();

	    if( Rect.intersects(Bulletbounds, platform.getBounds()) ){

	        for (int i = Bulletbounds.left; i < Bulletbounds.right; i++) {
	            for (int j = Bulletbounds.top; j < Bulletbounds.bottom; j++) {
	                int PlayerPixel = CollisionUtil.getBitmapPixel(this, i, j);
	                int PlatformPixel = CollisionUtil.getBitmapPixel(platform, i, j);
	                if( CollisionUtil.isFilled(PlayerPixel) && CollisionUtil.isFilled(PlatformPixel)) {
	                	return true;
	                }
	            }
	        }
	    }
	    return false;
	}

}
