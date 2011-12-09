package com.isitbroken.Slurms;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

import com.stackoverflow.arcone.CollisionUtil;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
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
	private Bitmap Player1Bitmap;
	private Bitmap Player2Bitmap;

	public GameBoard(Context context, SlurmsActivity slurmsActivity) {
		super(context, null);
		surfaceCreated = false;
		activity = slurmsActivity;
		getHolder().addCallback(this);

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		Height = height;
		Width = width;
		AssetManager mgr = activity.getAssets();
		try {
			Levelbackground = BitmapFactory.decodeStream(mgr.open("decor/background_faded.png"));
			levePlatform = new PlatformSprite(BitmapFactory.decodeStream(mgr.open("foreground/platform.png")), 0, 0, width, height,this);

			Player1Bitmap = BitmapFactory.decodeStream(mgr.open("sprites/slurms_sprite.png"));
			Player2Bitmap = BitmapFactory.decodeStream(mgr.open("sprites/glurmo_sprite.png"));

			;

			new PlayerSprite(Player1Bitmap, 60, this).AddToMap(Player1);
			new PlayerSprite(Player1Bitmap, 60, this).AddToMap(Player1);
			new PlayerSprite(Player2Bitmap, 60, this).AddToMap(Player2);
			new PlayerSprite(Player2Bitmap, 60, this).AddToMap(Player2);

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
		thread.run = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
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
		}

		HandlePlayers(Player1,canvas);
		HandlePlayers(Player2,canvas);

	}

	private void HandlePlayers(HashMap<String, PlayerSprite> list, Canvas canvas) {
		Iterator<String> SpriteIterator = list.keySet().iterator();

		while (SpriteIterator.hasNext()) {
			String key = SpriteIterator.next();
			PlayerSprite curentSprite = list.get(key);

			if(levePlatform != null){
				if(CollisionUtil.isCollisionDetected(curentSprite, levePlatform) == false){
					curentSprite.Fall();
				}
			}

			curentSprite.draw(canvas);
		}

	}

	private void TestCollisions(Canvas canvas) {


	}

	private void UpdateUI() {
		handler.post(new Runnable() {
			@Override
			public void run() {
				activity.UPdateUI();
			}
		});
	}

}
