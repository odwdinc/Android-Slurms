package com.isitbroken.Slurms;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
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
	private Bitmap Curesbitmap;
	private Rect Curesimg;
	private Rect Curespos;
	private int CrosHarX = 0;
	private int CrosHarY;
	private double angle;
	int StepCounter;
	private int player1pos;
	private int player2pos;

	TurnHandler(HashMap<String, PlayerSprite> player1, HashMap<String, PlayerSprite> player2, GameBoard gameBoard){

		try {
			this.player1 = player1;
			this.player2 = player2;
			this.gameBoard = gameBoard;
			//Matrix matrix = new Matrix();
		    //matrix.postScale(0.70F, 0.70F);
			this.mgr = gameBoard.mgr;
			this.bitmap = BitmapFactory.decodeStream(mgr.open("sprites/Marker.png"));
			this.Curesbitmap = BitmapFactory.decodeStream(mgr.open("sprites/crosshars.png"));
			//this.bitmap = Bitmap.createBitmap(this.bitmap , 0, 0, this.bitmap.getWidth(), this.bitmap.getHeight(), matrix, true);
			this.Crated = true;
			this.img = new Rect(0,0,this.bitmap.getWidth(), this.bitmap.getHeight());
			this.Curesimg = new Rect(0,0,this.Curesbitmap.getWidth(), this.Curesbitmap.getHeight());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}


	}

	public void Handle(Canvas canvas) {
		if(CurentPlayer != null){
			Player = true;
			//canvas.drawBitmap(bitmap, img, pos, null);

			this.pos = new Rect(CurentPlayer.getX()+(bitmap.getWidth()/2),(CurentPlayer.getY()-bitmap.getHeight())-(this.bitmap.getHeight()/3),CurentPlayer.getX()+bitmap.getWidth()+(bitmap.getWidth()/2),CurentPlayer.getY()-(bitmap.getHeight()/3));
			canvas.drawBitmap(bitmap, img, pos, null);

			if(angle != 0){
				int gap = CurentPlayer.getHeight()+10;

				CrosHarX = (int)(CurentPlayer.getX()-gap*Math.cos(angle*(Math.PI/180)));
				CrosHarY = (int)(CurentPlayer.getY()-gap*Math.sin(angle*(Math.PI/180)));

				Curespos = new Rect(CrosHarX,CrosHarY,CrosHarX+Curesbitmap.getWidth(),CrosHarY+Curesbitmap.getWidth());
				canvas.drawBitmap(Curesbitmap, Curesimg, Curespos, null);
			}


		}else{

			GetNextPlayer();
		}
	}

	private void GetNextPlayer() {
		HashMap<String, PlayerSprite> player = null;
		int plypos = 0;
		if(CurentPlayer != null){
			if(CurentPlayer.SpritesMap.equals(player1)){
				player = player2;
				plypos = player2pos;
				if(player1pos++ >= player1.size()-1){
					player1pos=0;
				}
			}else if(CurentPlayer.SpritesMap.equals(player2)){
				player = player1;
				plypos = player1pos;
				if(player2pos++ >= player2.size()-1){
					player2pos=0;
				}
			}
		}else{
			player = player1;
			plypos = 0;
			player1pos = 0;
			player2pos = 0;
		}
		if(player != null){
		Object[] thisset = player.values().toArray();
		PlayerSprite thisstr = (PlayerSprite) thisset[plypos];

		CurentPlayer = thisstr;
		StepCounter = 0;
		}

	}

	public  void UpdatePos(MotionEvent event, boolean moving) {
		if(Player){
			if(event.getAction() == MotionEvent.ACTION_UP && moving){
				if(StepCounter++ < 4){
					int xpos = CurentPlayer.getX();
					if(event.getX() > xpos){
						CurentPlayer.MoveY(-20);
						CurentPlayer.MoveX(+10);
					}else{
						CurentPlayer.MoveY(-20);
						CurentPlayer.MoveX(-10);
					}
				}else{
					gameBoard.UpdateUI();
				}

			}
			if(event.getAction() == MotionEvent.ACTION_MOVE){


				float HarX = event.getX();
				float HarY = event.getY()-40;

				float mousex = (HarX-CurentPlayer.getX())*-1;
				float mousey = (HarY-CurentPlayer.getY());
				boolean flip = false;
				if(event.getX() > CurentPlayer.getX()){
					flip = true;
				}


				angle = Math.atan(mousey/mousex)/(Math.PI/180);
				if (mousex<0) {
					angle += 180;
				}
				if (mousex>=0 && mousey<0) {
					angle += 360;
				}
				angle = (angle)*-1;

				Log.d("Slurms", ">:"+angle);
				CurentPlayer.setangle(angle,flip);
			}
		}

	}

	public void Fire() {
		CurentPlayer.FrireWepon();
		GetNextPlayer();
	}
}
