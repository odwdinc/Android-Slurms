package com.isitbroken.Slurms;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Handler;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameBoard extends SurfaceView implements SurfaceHolder.Callback{

	boolean surfaceCreated;
	private GameThread thread;
	private SlurmsActivity activity;
	Handler handler;
	PlatformSprite levePlatform;
	int Height;
	private Bitmap Levelbackground;
	int Width;

	HashMap<String, PlayerSprite> Player1 = new HashMap<String, PlayerSprite>();
	HashMap<String, PlayerSprite> Player2 = new HashMap<String, PlayerSprite>();

	HashMap<String, Sprite> Items;
	 Bitmap Player1Bitmap;
	 Bitmap Player2Bitmap;
	 Bitmap SplatWepon;
	 TurnHandler Turns;
	 AssetManager mgr;

	public GameBoard(Context context, SlurmsActivity slurmsActivity) {
		super(context, null);
		surfaceCreated = false;
		activity = slurmsActivity;
		getHolder().addCallback(this);
		Display display = activity.getWindowManager().getDefaultDisplay();
		Width = display.getWidth();
		Height = display.getHeight();
		mgr = activity.getAssets();

		Turns = new TurnHandler(Player1,Player2,this);
		SetupBoard();

	}

	private void SetupBoard() {
		try {

			Levelbackground = Bitmap.createScaledBitmap(BitmapFactory.decodeStream(mgr.open("decor/background_faded.png")), Width, Height, false);
			levePlatform = new PlatformSprite(Bitmap.createScaledBitmap(BitmapFactory.decodeStream(mgr.open("foreground/platform.png")), Width, Height, false), 0, 0, this);

			SplatWepon = BitmapFactory.decodeStream(mgr.open("sprites/slurmsBlast.png"));
			 Matrix matrix = new Matrix();
		     matrix.postScale(0.20F, 0.20F);


		     //Player1Bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeStream(mgr.open("sprites/slurms_sprite.png")),Width/10,Height/10,false);
			 //Player2Bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeStream(mgr.open("sprites/glurmo_sprite.png")),Width/10,Height/10,false);

			 Player1Bitmap = BitmapFactory.decodeStream(mgr.open("sprites/slurms_sprite.png"));
			 Player2Bitmap = BitmapFactory.decodeStream(mgr.open("sprites/glurmo_sprite.png"));


			 Player1Bitmap = Bitmap.createBitmap(Player1Bitmap , 0, 0, Player1Bitmap.getWidth(), Player1Bitmap.getHeight(), matrix, true);
			 Player2Bitmap = Bitmap.createBitmap(Player2Bitmap , 0, 0, Player2Bitmap.getWidth(), Player2Bitmap.getHeight(), matrix, true);



			Player1.clear();
			Player2.clear();

			for(int i = 0; i<2; i++){
				new PlayerSprite("Slurms", this, Player1Bitmap, Player1);
				new PlayerSprite("Glurmo", this, Player2Bitmap, Player2);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (surfaceCreated == false) {
			createThread(holder);
			surfaceCreated = true;
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		surfaceCreated = false;
	}

	public void terminateThread() {
		if(thread != null){
			thread.run = false;
			try {
				thread.join();
			} catch (InterruptedException e) {
			}
		}
	}

	public void createThread(SurfaceHolder holder) {
		thread = new GameThread(this, holder);
		thread.run = true;
		thread.start();
	}

	@Override
	protected void onDraw(Canvas canvas) {

		if(Levelbackground != null ){
			canvas.drawBitmap(Levelbackground, new Rect(0, 0, Levelbackground.getWidth(), Levelbackground.getHeight()),new Rect(0, 0,Width,Height), null);
		}

		if(levePlatform != null){
			levePlatform.draw(canvas);
			HandlePlayers(Player1,canvas);
			HandlePlayers(Player2,canvas);
		}

		Turns.Handle(canvas);

	}

	private void HandlePlayers(HashMap<String, PlayerSprite> hlist, Canvas canvas) {
		Iterator<String> SpriteIterator = hlist.keySet().iterator();

		while (SpriteIterator.hasNext()) {
			String key = SpriteIterator.next();

			PlayerSprite curentSprite = hlist.get(key);


			if(curentSprite.remove == false){

				if(!curentSprite.isPlatformCollisionDetected()){
					curentSprite.Falling();
				}

				curentSprite.draw(canvas);
			}else{
				SpriteIterator.remove();
				//hlist.remove(curentSprite.ID);
			}


		}

	}

	private void TestCollisions(HashMap<String, PlayerSprite> hlist,Canvas canvas) {
		Iterator<String> SpriteIterator = hlist.keySet().iterator();
		while (SpriteIterator.hasNext()) {


		}

	}

	private void UpdateUI() {
		handler.post(new Runnable() {
			@Override
			public void run() {
				activity.UPdateUI();
			}
		});
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

}
