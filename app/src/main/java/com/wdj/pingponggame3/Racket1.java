package com.wdj.pingponggame3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


public class Racket1 {

    Bitmap image;
    int x, y;

    static int btn_direction = 0;

    public int w, h;

    public Racket1(int x, int y) {

        this.x = x;
        this.y = y;

        image = BitmapFactory.decodeResource(AirHockey.context.getResources(), R.drawable.racket1);
        int xWidth = AirHockey.Width / 6;
        int yWidth = xWidth / 4;

        image = Bitmap.createScaledBitmap(image, xWidth, yWidth, true);


        w = image.getWidth() / 2;
        h = image.getHeight() / 2;
    }

    public void movePuck(boolean btnLeft, boolean btnRight) {

        if (btnLeft) {
            btn_direction = 0;
            x -= AirHockey.Width / 80;
        }

        if (btnRight) {
            btn_direction = 1;
            x += AirHockey.Width / 80;
        }
    }
}
