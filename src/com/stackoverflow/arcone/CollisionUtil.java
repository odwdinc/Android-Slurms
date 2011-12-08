package com.stackoverflow.arcone;

import com.isitbroken.Slurms.Sprite;

import android.graphics.Color;
import android.graphics.Rect;

public class CollisionUtil {
public static boolean isCollisionDetected(Sprite sprite1, Sprite sprite2){
    Rect bounds1 = sprite1.getBounds();
    Rect bounds2 = sprite2.getBounds();

    if( Rect.intersects(bounds1, bounds2) ){
        Rect collisionBounds = getCollisionBounds(bounds1, bounds2);
        for (int i = collisionBounds.left; i < collisionBounds.right; i++) {
            for (int j = collisionBounds.top; j < collisionBounds.bottom; j++) {
                int sprite1Pixel = getBitmapPixel(sprite1, i, j);
                int sprite2Pixel = getBitmapPixel(sprite2, i, j);
                if( isFilled(sprite1Pixel) && isFilled(sprite2Pixel)) {
                    return true;
                }
            }
        }
    }
    return false;
}

private static int getBitmapPixel(Sprite sprite, int i, int j) {
	int x = i-(int)sprite.getX();
	int y = j-(int)sprite.getY();
    return sprite.getBitmap().getPixel(x, y);
}

private static Rect getCollisionBounds(Rect rect1, Rect rect2) {
    int left = (int) Math.max(rect1.left, rect2.left);
    int top = (int) Math.max(rect1.top, rect2.top);
    int right = (int) Math.min(rect1.right, rect2.right);
    int bottom = (int) Math.min(rect1.bottom, rect2.bottom);
    return new Rect(left, top, right, bottom);
}

private static boolean isFilled(int pixel) {
    return pixel != Color.TRANSPARENT;
}
}
