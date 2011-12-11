package com.isitbroken.Slurms;

import android.graphics.AvoidXfermode;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class PlatformSprite extends Sprite {

	public PlatformSprite(Bitmap bitmap2, int x, int y, GameBoard gameBoard) {
		super(bitmap2, x, y, gameBoard);
		// TODO Auto-generated constructor stub
	}

	public void Addhole(float f, float g, int k) {

		Paint RemovePaint = new Paint();
		RemovePaint.setARGB(255, 0, 10, 30);
		int removeColor = RemovePaint.getColor();

		Canvas TempCanvas = new Canvas();
		TempCanvas.setBitmap(getBitmap());

		TempCanvas.drawCircle( f, g, (float)k, RemovePaint);

		//TempCanvas.drawBitmap(Gameactivity.SplatWepon, new Rect(0,0,Gameactivity.SplatWepon.getWidth(),Gameactivity.SplatWepon.getHeight()), new Rect((int)(f-k/2) ,(int)(g-k/2) , (int)(f+k/2), (int)(g+k/2)), RemovePaint);


		RemovePaint.setAlpha(0);
		RemovePaint.setXfermode(new AvoidXfermode(removeColor, 0, AvoidXfermode.Mode.TARGET));
		TempCanvas.drawPaint(RemovePaint);

	}
}
