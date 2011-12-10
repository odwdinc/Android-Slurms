package com.isitbroken.Slurms;

import java.io.IOException;
import java.util.HashMap;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.view.MotionEvent;

public class TurnHandler {
	boolean Player = false;
	private HashMap<String, PlayerSprite> player1;
	private HashMap<String, PlayerSprite> player2;
	private GameBoard gameBoard;
	private PlayerSprite CurentPlayer;
	private AssetManager mgr;
	private boolean Crated = false;
	private Rect img;
	private Bitmap bitmap;
	private Rect pos;

	TurnHandler(HashMap<String, PlayerSprite> player1, HashMap<String, PlayerSprite> player2, GameBoard gameBoard){

		try {
			this.player1 = player1;
			this.player2 = player2;
			this.gameBoard = gameBoard;
			Matrix matrix = new Matrix();
		    //matrix.postScale(0.70F, 0.70F);
			this.mgr = gameBoard.mgr;
			this.bitmap = BitmapFactory.decodeStream(mgr.open("sprites/Marker.png"));
			this.bitmap = Bitmap.createBitmap(this.bitmap , 0, 0, this.bitmap.getWidth(), this.bitmap.getHeight(), matrix, true);
			this.Crated = true;
			this.img = new Rect(0,0,this.bitmap.getWidth(), this.bitmap.getHeight());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public void Handle(Canvas canvas) {
		if(CurentPlayer != null){
			Player = true;
			this.pos = new Rect(CurentPlayer.getX()+(this.bitmap.getWidth()/2),(CurentPlayer.getY()-this.bitmap.getHeight())-(this.bitmap.getHeight()/3),CurentPlayer.getX()+this.bitmap.getWidth()+(this.bitmap.getWidth()/2),CurentPlayer.getY()-(this.bitmap.getHeight()/3));
			canvas.drawBitmap(bitmap, img, pos, null);
		}else{
			CurentPlayer = player1.get("Slurms 1");
		}
	}

	public void UpdatePos(MotionEvent event) {
		if(Player){
			int xpos = CurentPlayer.getX();
			if(event.getX() > xpos){
				CurentPlayer.MoveY(-20);
				CurentPlayer.MoveX(+10);
			}else{
				CurentPlayer.MoveY(-20);
				CurentPlayer.MoveX(-10);
			}

			//float mousex = event.getX()-gameBoard.Width;
			//float mousey = (event.getY()-gameBoard.Height)*-1;
			//double angle = Math.atan(mousey/mousex)/(Math.PI/180);
			//CurentPlayer.setangle(angle);
		}

	}
}
