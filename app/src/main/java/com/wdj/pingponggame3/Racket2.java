package com.wdj.pingponggame3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


public class Racket2 {

    Bitmap image;
    int x, y;
    int w, h;
    static int btn_direction2 = 0;

    public Racket2(int x, int y) {

        this.x = x;
        this.y = y;

        image = BitmapFactory.decodeResource(AirHockey.context.getResources(), R.drawable.racket2);
        int xWidth = AirHockey.Width / 6;
        int yWidth = xWidth / 4;

        image = Bitmap.createScaledBitmap(image, xWidth, yWidth, true);

        w = image.getWidth() / 2;
        h = image.getHeight() / 2;

    }

    public void movePuck(boolean btnLeft, boolean btnRight) {


        if (btnLeft) {
            btn_direction2 = 0;
            x -= AirHockey.Width / 80;
        }

        if (btnRight) {
            btn_direction2 = 1;
            x += AirHockey.Width / 80;
        }

    }

}
