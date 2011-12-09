package com.isitbroken.Slurms;

import java.io.IOException;
import java.io.InputStream;

import com.stackoverflow.arcone.CollisionUtil;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameBoard extends SurfaceView implements SurfaceHolder.Callback{

	boolean surfaceCreated;
	private GameThread thread;
	private SlurmsActivity activity;
	Handler handler;
	private Sprite testcan;
	Sprite testLeve;
	private int Height;
	private Bitmap Levelbackground;
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
		AssetManager mgr = activity.getAssets();
		InputStream temp;
		try {
			Levelbackground = BitmapFactory.decodeStream(mgr.open("decor/background.png"));
			testcan = new Sprite(BitmapFactory.decodeStream(mgr.open("sprites/slurms_sprite.png")), 200, 30, 20, 30);
			testLeve = new Sprite(BitmapFactory.decodeStream(mgr.open("Sprites/testlevel.png")), 0, 0, width, height);

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
		//canvas.drawColor(0xFFFFFFFF);
		canvas.drawRGB(0,0,0);
		if(Levelbackground != null ){
			canvas.drawBitmap(Levelbackground, 0, 0, null);
		}
		if(testcan != null && testLeve != null){
			if(CollisionUtil.isCollisionDetected(testcan, testLeve) == false){
					testcan.Fall(Height-10);
			}
			testcan.draw(canvas);
			testLeve.draw(canvas);
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

}
