package com.isitbroken.Slurms;

import java.util.HashMap;
import java.util.Iterator;

import com.stackoverflow.arcone.CollisionUtil;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class PlayerSprite  extends Sprite {
	HashMap<String, PlayerSprite> SpritesMap;
	private int Border = 0;
	private Rect DestRect;
	boolean remove = false;

	public PlayerSprite(Bitmap bitmap2, int Scale, GameBoard gameBoard, int i) {
		super(bitmap2, 50+(int) (Math.random()*(gameBoard.Width-100)), 30 , Scale-10, Scale, gameBoard);
		ID = "ID"+i;
		remove = false;
		// TODO Auto-generated constructor stub
		//while(Falling()){}
	}

	@Override
	public void calBound() {
		DestRect = new Rect(getX() , getY(), getX() + getWidth() , getY() + getHeight());
		setBoundRect(new Rect(getX()+Border*2 , getY()+getHeight()/2, getX() + getWidth()-Border*2 , getY() + getHeight()-Border/2));
	}

	@Override
	public void draw(Canvas canvas) {
		calBound();
		Paint TextPant = new Paint();
		TextPant.setARGB(255, 0, 200, 75);
		canvas.drawText(ID, getX(), getY(), TextPant);

		canvas.drawBitmap(getBitmap(), getSourceRect(), DestRect, null);
	}

	public void AddToMap(HashMap<String, PlayerSprite> List) {
		SetBorder(10);
		List.put(ID, this);
		SpritesMap = List;

	}
	public void SetBorder(int i) {
		Border = i;

	}

	public boolean Falling() {
		int steps = 2;

		for(int i = 0; i < getHeight()/3; i=i+steps){
			if(isPlatformCollisionDetected() == false){
				int Tempost = getY()+steps;
				if(Tempost < Gameactivity.Height-(getHeight()+5)){
					SetY(Tempost);
				}else{
					remove = true;
				}
			}else{
				return false;
			}
		}

		return true;
	}

	public boolean isPlatformCollisionDetected(){
	    Rect Playerbounds = this.getBounds();
	    Rect Platformbounds = Gameactivity.levePlatform.getBounds();

	    if( Rect.intersects(Playerbounds, Platformbounds) ){

	        for (int i = Playerbounds.left; i < Playerbounds.right; i++) {
	            for (int j = Playerbounds.top; j < Playerbounds.bottom; j++) {
	                int PlayerPixel = CollisionUtil.getBitmapPixel(this, i, j);
	                int PlatformPixel = CollisionUtil.getBitmapPixel(Gameactivity.levePlatform, i, j);
	                if( CollisionUtil.isFilled(PlayerPixel) && CollisionUtil.isFilled(PlatformPixel)) {
	                    return true;
	                }
	            }
	        }
	    }
	    return false;
	}
}
