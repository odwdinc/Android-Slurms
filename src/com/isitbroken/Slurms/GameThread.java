package com.isitbroken.Slurms;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread{

	boolean run = true;
	private SurfaceHolder myThreadSurfaceHolder;
	private GameBoard myThreadSurfaceView;

	GameThread(GameBoard roSurfaceView, SurfaceHolder surfaceHolder){
		myThreadSurfaceHolder = surfaceHolder;
		myThreadSurfaceView = roSurfaceView;
	}

	public void run() {
		while(run){

				Canvas c = null;
				c = myThreadSurfaceHolder.lockCanvas(null);
				synchronized (myThreadSurfaceHolder) {
					myThreadSurfaceView.onDraw(c);
				}
				if (c != null) {
					myThreadSurfaceHolder.unlockCanvasAndPost(c);
				}
				//}

		}
	}
}

