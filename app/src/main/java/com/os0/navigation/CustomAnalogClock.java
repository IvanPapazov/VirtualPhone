package com.os0.navigation;

import android.app.Notification;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Picture;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.Calendar;

public class CustomAnalogClock extends View {
        private int minuteDiff=0, hourDiff=0;
        private boolean dialOn = true;

        public void setDiff(int hDiff, int mDiff, boolean setDial){
            hourDiff = hDiff;
            minuteDiff = mDiff;
            dialOn = setDial;
        }

        /** height, width of the clock's view */
        private int mHeight, mWidth = 0;

        /** numeric numbers to denote the hours */
        private int[] mClockHours = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

        /** spacing and padding of the clock-hands around the clock round */
        private int mPadding = 0;
        private int mNumeralSpacing = 0;

        /** truncation of the heights of the clock-hands,
         hour clock-hand will be smaller comparetively to others */
        private int mHandTruncation, mHourHandTruncation = 0;

        /** others attributes to calculate the locations of hour-points */
        private int mRadius = 0;
        private Paint mPaint;
        private Rect mRect = new Rect();
        private boolean isInit;  // it will be true once the clock will be initialized.

        public CustomAnalogClock(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public CustomAnalogClock(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        protected void onDraw(Canvas canvas) {

            /** initialize necessary values */
            if (!isInit) {
                mPaint = new Paint();
                mHeight = getHeight();
                mWidth = getWidth();
                mPadding = mNumeralSpacing + 50;  // spacing from the circle border
                int minAttr = Math.min(mHeight, mWidth);
                mRadius = minAttr / 2 - mPadding;

                // for maintaining different heights among the clock-hands
                mHandTruncation = minAttr / 20;
                mHourHandTruncation = minAttr / 10;

                isInit = true;  // set true once initialized
            }

            /** draw the canvas-color */
            //canvas.drawColor(Color.DKGRAY);

            /** circle border */
            mPaint.reset();
            mPaint.setColor(Color.WHITE);
            if (!dialOn) mPaint.setColor(Color.BLACK);
            mPaint.setStyle(Style.STROKE);
            mPaint.setStrokeWidth(4);
            mPaint.setAntiAlias(true);
            canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius + mPadding - 10, mPaint);

            /** clock-center */
            mPaint.setColor(Color.rgb(255, 215, 0));
            mPaint.setStyle(Style.FILL);
            canvas.drawCircle(mWidth / 2, mHeight / 2, 35, mPaint);  // the 03 clock hands will be rotated from this center point.

            /** border of hours */

            int fontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics());
            mPaint.setTextSize(fontSize);  // set font size (optional)

            if (!dialOn){
                for (int hour : mClockHours) {
                    String tmp = String.valueOf(hour);
                    mPaint.setColor(Color.BLACK);
                    mPaint.getTextBounds(tmp, 0, tmp.length(), mRect);  // for circle-wise bounding

                    // find the circle-wise (x, y) position as mathematical rule
                    double angle = Math.PI / 6 * (hour - 3);
                    int x = (int) (mWidth / 2 + Math.cos(angle) * mRadius - mRect.width() / 2);
                    int y = (int) (mHeight / 2 + Math.sin(angle) * mRadius + mRect.height() / 2);

                    canvas.drawText(String.valueOf(hour), x, y, mPaint);  // you can draw dots to denote hours as alternative
                }
            }
            /** draw clock hands to represent the every single time */

            Calendar calendar = Calendar.getInstance();
            float hour = calendar.get(Calendar.HOUR_OF_DAY) + hourDiff;
            hour = hour > 12 ? hour - 12 : hour;

            drawHandLine(canvas, (hour + ((double)calendar.get(Calendar.MINUTE) + minuteDiff) / 60) * 5f, true, false); // draw hours
            drawHandLine(canvas, calendar.get(Calendar.MINUTE) + minuteDiff, false, false); // draw minutes
            drawHandLine(canvas, calendar.get(Calendar.SECOND), false, true); // draw seconds

            /** invalidate the appearance for next representation of time  */
            postInvalidateDelayed(500);
            invalidate();
        }

        private void drawHandLine(Canvas canvas, double moment, boolean isHour, boolean isSecond) {
            if (!dialOn) mPaint.setStrokeWidth(5f);
            else mPaint.setStrokeWidth(30f);
            double angle = Math.PI * moment / 30 - Math.PI / 2;
            int handRadius = isHour ? mRadius - mHandTruncation - mHourHandTruncation : mRadius - mHandTruncation;
            if (isSecond) mPaint.setColor(Color.rgb(255, 69, 0));
            else mPaint.setColor(Color.rgb(255, 215, 0));
            if (!dialOn) handRadius /= 2;
            canvas.drawLine(mWidth / 2, mHeight / 2, (float) (mWidth / 2 + Math.cos(angle) * handRadius), (float) (mHeight / 2 + Math.sin(angle) * handRadius), mPaint);
            moment -= 30;
            angle = Math.PI * moment / 30 - Math.PI / 2;
            handRadius -= isHour ? 245 : 350;
            if (!dialOn) handRadius /= 1.5f;
            canvas.drawLine(mWidth / 2, mHeight / 2, (float) (mWidth / 2 + Math.cos(angle) * handRadius), (float) (mHeight / 2 + Math.sin(angle) * handRadius), mPaint);
            handRadius += isHour ? 235 : 340;
            mPaint.setStrokeWidth(10f);
            mPaint.setColor(Color.WHITE);
            if (!dialOn) {
                mPaint.setColor(Color.BLACK);
                mPaint.setStrokeWidth(5f);
            }
            moment += 30;
            angle = Math.PI * moment / 30 - Math.PI / 2;
            if (!dialOn) handRadius /= 2;
            canvas.drawLine(mWidth / 2, mHeight / 2, (float) (mWidth / 2 + Math.cos(angle) * handRadius), (float) (mHeight / 2 + Math.sin(angle) * handRadius), mPaint);
        }
    }
