package com.example.firsttopic.Fourtopic;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
public class MyView extends View {
    private String TAG = "TAG";
    private Bitmap bitmap;
    private int startX, startY, endX, endY;
    private Matrix matrix = new Matrix();
    private float scale = 1.0f;
    private float translationX = 0f;
    private float translationY = 0f;
    private float coreX = 0f;
    private float coreY = 0f;
    private float s, s1;
    private boolean bool;
    private boolean cent;
    public MyView(Context context) {
        super(context);
    }
    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        cent = true;
        Log.d(TAG, "MyView: ");
    }
    public int seturl(int id) {
        Log.d(TAG, "seturl: " + id);
        bitmap = BitmapFactory.decodeResource(getResources(), id);
        invalidate();
        return id;
    }
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        matrix.reset();
        matrix.setScale(scale, scale,coreX,coreY);
        if (cent) {
            translationX = (canvas.getWidth() - bitmap.getWidth())/2;
            translationY = (canvas.getHeight() - bitmap.getHeight())/2;
            Log.e(TAG, "onDraw: "+translationX );
            matrix.postTranslate(translationX, translationY);
            cent = false;
       } else {
            matrix.postTranslate(translationX, translationY);
        }
        Bitmap bitmap1 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        canvas.drawBitmap(bitmap1, matrix, null);
    }
    private float scale(MotionEvent e) {
        return (float) Math.sqrt(Math.pow(e.getX(0) - e.getX(1), 2) + Math.pow(e.getY(0) - e.getY(1), 2));
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getPointerCount() >= 2) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_POINTER_DOWN:
                    startX = (int) event.getX();
                    endX = startX;
                    Log.d(TAG, "onTouchEvent: cenX" + "\t" + startX);
                    startY = (int) event.getY();
                    endY = startY;
                    s = scale(event);
                    Log.d(TAG, "onTouchEvent: ACTION_POINTER_DOWN");
                    bool = false;
                    break;
                case MotionEvent.ACTION_MOVE:
                    coreX = (event.getX(0) + event.getX(1)) / 2;
                    Log.d(TAG, "onTouchEvent: 中心" + coreX);
                    coreY = (event.getY(0) + event.getY(1)) / 2;
                    s1 = scale(event);
                    scale = scale * (s1 / s);
                    s1 = scale(event);
                    if (scale > 3.5)
                        scale = 3.5f;
                    else if (scale < 0.3)
                        scale = 0.3f;
                    invalidate();
                    s = s1;
                    Log.d(TAG, "onTouchEvent: ACTION_MOVE");
                    break;
            }
        } else {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    Log.e(TAG, "onTouchEvent: "+translationX );
                    startX = (int) event.getX();
                    Log.d(TAG, "onTouchEvent: cenX" + "\t" + startX);
                    startY = (int) event.getY();
                    Log.d(TAG, "onTouchEvent: ACTION_DOWN");
                    bool = true;
                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.d(TAG, "onTouchEvent: MOVE");
                    endX = (int) event.getX();
                    endY = (int) event.getY();
                    if (bool) {
                        translationX += endX -startX;
                        Log.e(TAG, "onTouchEvent: "+translationX );
                        translationY += endY -startY;
                        invalidate();
                    }
                    bool = true;
                    startX = endX;
                    startY = endY;
                    break;
                case MotionEvent.ACTION_UP:
                    Log.d(TAG, "onTouchEvent: ACTION_UP");
                    break;
            }
        }
        return true;
    }
}
