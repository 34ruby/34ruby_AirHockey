package com.wdj.pingponggame3;


import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.core.view.MotionEventCompat;

import java.io.FileOutputStream;

import static android.app.AlertDialog.*;

public class AirHockey extends View {
    static Context context;
    private GameThread mThread;
    static int Width, Height;

    public Racket1 racket1;
    public Racket2 racket2;

    Button btnLeft1P;
    Button btnRight1P;
    int puck_direction = 1;

    Button btnLeft2P;
    Button btnRight2P;

    Bitmap puck;
    int puck_x;
    int puck_y;
    int mWidth;

    int puck_x_speed ;
    int puck_y_speed ;


    int player1Score = 0;
    int player2Score = 0;

    Paint paint = new Paint();

    Bitmap backImage;

    View dialogView;
    EditText dlgEdt1;
    Button finishButton;


    public AirHockey(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Width = display.getWidth();
        Height = display.getHeight();

        Bitmap imgLeft = BitmapFactory.decodeResource(getResources(), R.drawable.btnleft);
        Bitmap imgRight = BitmapFactory.decodeResource(getResources(), R.drawable.btnright);


        int basicUnit = imgLeft.getWidth();

        puck_x_speed = Width/150;
        puck_y_speed = Width/105;


        btnLeft1P = new Button(imgLeft, 0, Height - basicUnit - basicUnit / 4);
        btnRight1P = new Button(imgRight, Width - btnLeft1P.w, Height - basicUnit - basicUnit / 4);



        btnLeft2P = new Button(imgLeft, 0, basicUnit / 4);
        btnRight2P = new Button(imgRight, Width - btnLeft1P.w, basicUnit / 4);


        puck = BitmapFactory.decodeResource(context.getResources(), R.drawable.puck);
        puck = Bitmap.createScaledBitmap(puck, Width / 16, Width / 16, true);
        mWidth = puck.getWidth() / 2;

        backImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.playground);
        backImage = Bitmap.createScaledBitmap(backImage, Width, Height, true);

        racket1 = new Racket1(Width / 2, Height - basicUnit * 2 + 120);
        racket2 = new Racket2(Width / 2, basicUnit);
        puck_x = racket1.x + racket1.w;
        puck_y = racket1.y;

        if (mThread == null) {
            mThread = new GameThread();
            mThread.start();
        }

    }

    @Override
    protected void onDetachedFromWindow() {
        mThread.operation = false;
        super.onDetachedFromWindow();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawBitmap(backImage, 0, 0, null);

        if (isCollision(racket1.x + racket1.w, racket1.y + racket1.h, racket1.w, racket1.h, puck_x + mWidth, puck_y + mWidth, mWidth, mWidth)) {

            puck_y_speed = -puck_y_speed;
            puck_y -= 10;

            if(Racket1.btn_direction==0){
                puck_direction = 0;
            } else puck_direction = 1;
        }


        if (isCollision(racket2.x + racket2.w, racket2.y + racket2.h, racket2.w, racket2.h, puck_x + mWidth, puck_y + mWidth, mWidth, mWidth)) {

            puck_y_speed = -puck_y_speed;
            puck_y += 10;

            if(Racket2.btn_direction2==0){
                puck_direction = 0;
            } else puck_direction = 1;

        }

        canvas.drawBitmap(racket1.image, racket1.x, racket1.y, null);
        canvas.drawBitmap(racket2.image, racket2.x, racket2.y, null);


        if (puck_x < 0 || puck_x > Width) {

            if (puck_direction == 1) {
                puck_direction = 0;
            }
            else if (puck_direction == 0) {
                puck_direction = 1;
            }
        }

        if(puck_direction==0) {
            puck_x -= puck_x_speed;
            puck_y -= puck_y_speed;

        } else {
            puck_x += puck_x_speed;
            puck_y -= puck_y_speed;
        }

        canvas.drawBitmap(puck, puck_x, puck_y, null);

        if (puck_y < 0) {
            puck_y = Height / 2;
            puck_x = 0;
            player1Score += 1;
        }
        if (puck_y > Height) {
            puck_y = Height / 2;
            puck_x = Width;
            player2Score += 1;
        }



        canvas.drawBitmap(btnLeft1P.img, btnLeft1P.x, btnLeft1P.y, null);
        canvas.drawBitmap(btnRight1P.img, btnRight1P.x, btnRight1P.y, null);


        canvas.drawBitmap(btnLeft2P.img, btnLeft2P.x, btnLeft2P.y, null);
        canvas.drawBitmap(btnRight2P.img, btnRight2P.x, btnRight2P.y, null);



        canvas.drawText("Score: " + player1Score + "", mWidth*2, Height / 2 + btnLeft1P.h, paint);
        canvas.rotate(180, Width / 2, Height / 2);
        canvas.drawText("Score: " + player2Score + "", mWidth*2, Height / 2 + btnLeft1P.h, paint);
        canvas.rotate(180, Width / 2, Height / 2);

        if (player1Score == 5) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AirHockey.context);
            builder.setTitle("1플레이어 승리").setMessage("1플레이어가 5점을 먼저 획득하였습니다.");
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        if (player2Score == 5) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AirHockey.context);
            builder.setTitle("2플레이어 승리").setMessage("2플레이어가 5점을 먼저 획득하였습니다.");
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    boolean isCollision(int x, int y, int width, int height, int mx, int my, int mWidth, int mHeight) {

        if ((width + mWidth) > Math.abs(x - mx) && (height + mHeight) > Math.abs(y - my)) {
            return true;

        } else return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean isTouch = false;

        int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                isTouch = true;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                isTouch = false;
                break;
            default:
                return true;
        }


        int pIndex = MotionEventCompat.getActionIndex(event);
        int id = MotionEventCompat.getPointerId(event, pIndex);


        float x = MotionEventCompat.getX(event, pIndex);
        float y = MotionEventCompat.getY(event, pIndex);

        btnLeft1P.processButton(x, y, id, isTouch);
        btnRight1P.processButton(x, y, id, isTouch);


        btnLeft2P.processButton(x, y, id, isTouch);
        btnRight2P.processButton(x, y, id, isTouch);


        return true;
    }

    class GameThread extends Thread {
        public boolean operation = true;


        GameThread() {

            paint.setColor(Color.WHITE);
            paint.setAntiAlias(true);
            paint.setTypeface(Typeface.create("", Typeface.BOLD));
            paint.setTextSize(62);

        }

        @Override
        public void run() {
            while (operation) {
                try {
                    racket1.movePuck(btnLeft1P.isTouch, btnRight1P.isTouch);
                    racket2.movePuck(btnLeft2P.isTouch, btnRight2P.isTouch);
                    postInvalidate();
                    sleep(10);
                    if (player1Score == 5 || player2Score == 5) {
                        operation = false;

                    }
                } catch (Exception e) {

                }
            }
        }



    }

}
