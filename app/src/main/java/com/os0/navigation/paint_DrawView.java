package com.os0.navigation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class paint_DrawView extends View {

    private static final float TOUCH_TOLERANCE = 4;
    private float mX, mY;
    private Path mPath;

    private Paint mPaint;

    private ArrayList<paint_Stroke> redoPaths = new ArrayList<>();
    int tempIndex = -1;

    private ArrayList<paint_Stroke> paths = new ArrayList<>();
    private int currentColor;
    private int strokeWidth;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);

    public paint_DrawView(Context context) {
        this(context, null);
    }

    //smooth
    public paint_DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();

        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mPaint.setAlpha(0xff);

    }

    public void init(int height, int width) {

        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        currentColor = Color.BLACK;

        strokeWidth = 20;
    }

    public void setColor(int color) {
        currentColor = color;
    }

    public int getColor(){
        return currentColor;
    }


    public void setStrokeWidth(int width) {
        strokeWidth = width;
    }

    public void undo() {

        if (paths.size() != 0) {
            redoPaths.add ( paths.get(paths.size() - 1));
            tempIndex ++;
            paths.remove(paths.size() - 1);
            invalidate();
        }
    }
    public void eraseAll(){

       // if(!paths.isEmpty()) {
         //   Stroke all = paths.get(paths.size() - 1);

         //   for (Stroke s : paths
          //  ) {
          //      all.path.addPath(s.path);
          //  }
          //  tempIndex++;
      //      redoPaths.add(all);

            paths.clear();
            invalidate();
      //  }
    }

    public void redo(){
        if(tempIndex>=0){
            paths.add(redoPaths.get(tempIndex));
            redoPaths.remove(tempIndex);
            tempIndex--;
            invalidate();
        }

    }

    public Bitmap save() {
        return mBitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.save();

        int backgroundColor = Color.WHITE;
        mCanvas.drawColor(backgroundColor);

        for (paint_Stroke fp : paths) {
            mPaint.setColor(fp.color);
            mPaint.setStrokeWidth(fp.strokeWidth);
            mCanvas.drawPath(fp.path, mPaint);
        }
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.restore();
    }


    private void touchStart(float x, float y) {
        mPath = new Path();
        paint_Stroke fp = new paint_Stroke(currentColor, strokeWidth, mPath);
        paths.add(fp);

        mPath.reset();

        mPath.moveTo(x, y);

        mX = x;
        mY = y;
    }


    private void touchMove(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);

        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    private void touchUp() {
        mPath.lineTo(mX, mY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchStart(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                invalidate();
                break;
        }
        return true;
    }
}
