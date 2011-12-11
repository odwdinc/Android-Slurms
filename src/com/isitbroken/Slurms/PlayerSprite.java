package com.isitbroken.Slurms;

import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.stackoverflow.arcone.CollisionUtil;

public class PlayerSprite  extends Sprite {
	HashMap<String, PlayerSprite> SpritesMap;
	private Rect DestRect;
	boolean remove = false;
	private WeaponSpirit Weapon;
	private GameBoard gameBoard;
	int Xvelocity;
	int Yvelocity;

	public PlayerSprite(String string, GameBoard gameBoard, Bitmap bitmap2, HashMap<String, PlayerSprite> player) {
		super(bitmap2, 50+(int) (Math.random()*(gameBoard.Width-100)), 30 , gameBoard);
		this.ID = string+" "+(player.size()+1);
		this.remove = false;
		this.Weapon = new WeaponSpirit(this,gameBoard, "Weapon1", "bullit");
		AddToMap(player);
		this.gameBoard = gameBoard;
		while(Falling() && remove == false){}
	}

	@Override
	public void calBound() {
		DestRect = new Rect(getX() , getY(), getX() + getWidth() , getY() + getHeight());
		setBoundRect(new Rect(getX()+(getWidth()/3) , getY()+getHeight()/2, getX() + (getWidth()-(getWidth()/3)) , getY() + (getHeight()-(getHeight()/6))));
	}

	@Override
	public void draw(Canvas canvas) {
		calBound();
		Paint TextPant = new Paint();
		TextPant.setARGB(255, 0, 200, 75);
		canvas.drawText(ID, getX(), getY(), TextPant);
		canvas.drawBitmap(getBitmap(), getSourceRect(), DestRect, null);

		if(Weapon.Crated){
			Weapon.draw(canvas);
		}
	}

	public void AddToMap(HashMap<String, PlayerSprite> List) {
		List.put(ID, this);
		SpritesMap = List;

	}
	public void SetBorder(int i) {

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
	public void MoveY(int i) {

		int curentY = getY();
			SetY(curentY+i);

		if(isPlatformCollisionDetected()){
			SetY(curentY-10);
		}
	}
	public void MoveX(int i) {

		int curentX = getX();
			SetX(curentX+i);

		if(isPlatformCollisionDetected()){
			SetX(curentX-i);
		}
	}

	public void setangle(double angle, boolean flip) {
		Weapon.angle = (float) angle;
		Weapon.flip = flip;

	}

	public void FrireWepon() {
		Weapon.Fire();

	}
}
