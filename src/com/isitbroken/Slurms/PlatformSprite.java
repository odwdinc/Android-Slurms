package com.isitbroken.Slurms;

import android.graphics.AvoidXfermode;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class PlatformSprite extends Sprite {

	public PlatformSprite(Bitmap bitmap2, int x, int y, int width, int height,
			GameBoard gameBoard) {
		super(bitmap2, x, y, width, height, gameBoard);
		// TODO Auto-generated constructor stub
	}

	public void Addhole(float f, float g, int k) {

		Paint RemovePaint = new Paint();
		RemovePaint.setARGB(255, 0, 10, 30);
		int removeColor = RemovePaint.getColor();

		Canvas TempCanvas = new Canvas();
		TempCanvas.setBitmap(getBitmap());

		TempCanvas.drawCircle( f, g, (float)k, RemovePaint);


		RemovePaint.setAlpha(0);
		RemovePaint.setXfermode(new AvoidXfermode(removeColor, 0, AvoidXfermode.Mode.TARGET));
		TempCanvas.drawPaint(RemovePaint);

	}
}
