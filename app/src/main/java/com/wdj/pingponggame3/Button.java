package com.wdj.pingponggame3;

import android.graphics.Bitmap;
import android.graphics.RectF;

public class Button {

    public Bitmap img;
    public int x, y;
    int w,h;

    private int myId = -1;
    private RectF rect;

    public boolean isTouch;

    public Button(Bitmap bitmap, int x, int y) {
        this.x=x;
        this.y=y;

        bitmap = Bitmap.createScaledBitmap(bitmap, AirHockey.Width/6, AirHockey.Width/6, true);

        w = bitmap.getWidth();
        h = bitmap.getHeight();
        img = bitmap;

        rect = new RectF(x, y,  x + w, y + h);

        this.x = x;
        this.y = y;
    }

    public void processButton(float x, float y,int id, boolean isDown) {

        if ( isDown && rect.contains(x, y) ) {
            isTouch = true;
            myId = id;
        }

        if ( !isDown && id == myId) {
            isTouch = false;
        }
    }

}
