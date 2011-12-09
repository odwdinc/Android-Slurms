package com.isitbroken.Slurms;

import java.util.HashMap;

import com.stackoverflow.arcone.CollisionUtil;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class PlayerSprite  extends Sprite {
	HashMap<String, PlayerSprite> SpritesMap;
	private int Border = 0;
	private Rect DestRect;

	public PlayerSprite(Bitmap bitmap2, int Scale, GameBoard gameBoard) {

		super(bitmap2, 50+(int) (Math.random()*gameBoard.Width-100), 30 , Scale-10, Scale, gameBoard);
		// TODO Auto-generated constructor stub
		while(Fall() == false){}
	}

	@Override
	public void calBound() {
		DestRect = new Rect(getX() , getY(), getX() + getWidth() , getY() + getHeight());
		setBoundRect(new Rect(getX()+Border*2 , getY()+getHeight()/2, getX() + getWidth()-Border*2 , getY() + getHeight()-Border));
	}

	@Override
	public void draw(Canvas canvas) {
		calBound();
		canvas.drawBitmap(getBitmap(), getSourceRect(), DestRect, null);
	}

	public void AddToMap(HashMap<String, PlayerSprite> List) {
		SetBorder(10);
		List.put(ID, this);
		//SpritesMap = List;

	}
	public void SetBorder(int i) {
		Border = i;

	}

	public boolean Fall() {
		int steps = 5;
		FallChecker:

		for(int i = 0; i < getHeight()/3; i=i+steps){
			if(isCollisionDetected(this, Gameactivity.levePlatform) == false){
				int Tempost = getY()+steps;
				if(Tempost < Gameactivity.Height){
					SetY(Tempost);
				}
			}else{
				return true;
			}
		}

		return false;
	}


	public boolean isCollisionDetected(PlayerSprite Player, PlatformSprite Platform){
	    Rect Playerbounds = Player.getBounds();
	    Rect Platformbounds = Platform.getBounds();

	    if( Rect.intersects(Playerbounds, Platformbounds) ){

	        for (int i = Playerbounds.left; i < Playerbounds.right; i++) {
	            for (int j = Playerbounds.top; j < Playerbounds.bottom; j++) {
	                int PlayerPixel = CollisionUtil.getBitmapPixel(Player, i, j);
	                int PlatformPixel = CollisionUtil.getBitmapPixel(Platform, i, j);
	                if( CollisionUtil.isFilled(PlayerPixel) && CollisionUtil.isFilled(PlatformPixel)) {
	                    return true;
	                }
	            }
	        }
	    }
	    return false;
	}


}
