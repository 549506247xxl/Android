package com.bytedance.clockapplication.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Calendar;
import java.util.Date;

public class Clock extends View {

    private final static String TAG = Clock.class.getSimpleName();

    private static final int FULL_ANGLE = 360;

    private static final int CUSTOM_ALPHA = 140;
    private static final int FULL_ALPHA = 255;

    private static final int DEFAULT_PRIMARY_COLOR = Color.WHITE;
    private static final int DEFAULT_SECONDARY_COLOR = Color.LTGRAY;

    private static final float DEFAULT_DEGREE_STROKE_WIDTH = 0.010f;

    public final static int AM = 0;

    private static final int RIGHT_ANGLE = 90;

    private float PANEL_RADIUS = 200.0f;// 表盘半径

    private float HOUR_POINTER_LENGTH;// 指针长度
    private float MINUTE_POINTER_LENGTH;
    private float SECOND_POINTER_LENGTH;
    private float UNIT_DEGREE = (float) (6 * Math.PI / 180);// 一个小格的度数

    private int mWidth, mCenterX, mCenterY, mRadius;

    private int degreesColor;

    private Paint mNeedlePaint;

    public Clock(Context context) {
        super(context);
        init(context, null);
    }

    public Clock(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Clock(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    // 画布的大小
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int size;
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int widthWithoutPadding = width - getPaddingLeft() - getPaddingRight();
        int heightWithoutPadding = height - getPaddingTop() - getPaddingBottom();

        if (widthWithoutPadding > heightWithoutPadding) {
            size = heightWithoutPadding;
        } else {
            size = widthWithoutPadding;
        }

        setMeasuredDimension(size + getPaddingLeft() + getPaddingRight(), size + getPaddingTop() + getPaddingBottom());
    }

    private void init(Context context, AttributeSet attrs) {

        this.degreesColor = DEFAULT_PRIMARY_COLOR;

        mNeedlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mNeedlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mNeedlePaint.setStrokeCap(Paint.Cap.ROUND);

    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        // 小的作为宽
        mWidth = getHeight() > getWidth() ? getWidth() : getHeight();

        int halfWidth = mWidth / 2;
        mCenterX = halfWidth;
        mCenterY = halfWidth;
        mRadius = halfWidth;
        PANEL_RADIUS = mRadius;
        HOUR_POINTER_LENGTH = PANEL_RADIUS - 400;
        MINUTE_POINTER_LENGTH = PANEL_RADIUS - 250;
        SECOND_POINTER_LENGTH = PANEL_RADIUS - 150;

        drawDegrees(canvas);
        drawHoursValues(canvas);
        drawNeedles(canvas);

        // todo 每一秒刷新一次，让指针动起来
        postInvalidateDelayed(1000);
    }

    private void drawDegrees(Canvas canvas) {

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);   // 抗锯齿标志
        paint.setStyle(Paint.Style.FILL_AND_STROKE);     // 填充风格：填充并且修边
        paint.setStrokeCap(Paint.Cap.ROUND);            // 圆头
        paint.setStrokeWidth(mWidth * DEFAULT_DEGREE_STROKE_WIDTH);
        paint.setColor(degreesColor);

        int rPadded = mCenterX - (int) (mWidth * 0.01f);    // 内径
        int rEnd = mCenterX - (int) (mWidth * 0.05f);       // 外径

        for (int i = 0; i < FULL_ANGLE; i += 6 /* Step */) {

            if ((i % RIGHT_ANGLE) != 0 && (i % 15) != 0)    // 每一刻显示的颜色重一些
                paint.setAlpha(CUSTOM_ALPHA);
            else {
                paint.setAlpha(FULL_ALPHA);
            }

            // Math.toRadians(i) 将 角度转弧度
//            int startX = (int) (mCenterX + rPadded * Math.cos(Math.toRadians(i)));
//            int startY = (int) (mCenterX - rPadded * Math.sin(Math.toRadians(i)));
//
//            int stopX = (int) (mCenterX + rEnd * Math.cos(Math.toRadians(i)));
//            int stopY = (int) (mCenterX - rEnd * Math.sin(Math.toRadians(i)));

            // 👆老师写的sin和cos好像反了
            int startX = (int) (mCenterX + rPadded * Math.sin(Math.toRadians(i)));
            int startY = (int) (mCenterX - rPadded * Math.cos(Math.toRadians(i)));

            int stopX = (int) (mCenterX + rEnd * Math.sin(Math.toRadians(i)));
            int stopY = (int) (mCenterX - rEnd * Math.cos(Math.toRadians(i)));

            canvas.drawLine(startX, startY, stopX, stopY, paint);

        }
    }

    /**
     * Draw Hour Text Values, such as 1 2 3 ...
     *
     * @param canvas
     */
    private void drawHoursValues(Canvas canvas) {
        // Default Color:
        // - hoursValuesColor

        int hoursNums[] = {12,1,2,3,4,5,6,7,8,9,10,11};
        Paint paint = new Paint(Paint.EMBEDDED_BITMAP_TEXT_FLAG);
        paint.setTextSize(50f);     // 设置字体大小
        paint.setColor(Color.CYAN); // 设置字体颜色
        paint.setTextAlign(Paint.Align.CENTER);
        int valuePadding = mCenterX - (int)(mWidth * 0.09f);
        for(int i=0; i< FULL_ANGLE; i+=30){

            int x = (int)(mCenterX + valuePadding * Math.sin(Math.toRadians(i)));
            int y = (int)(mCenterX - valuePadding * Math.cos(Math.toRadians(i)));
            y += paint.getTextSize()/2;  // 使定点位于文字中央
            canvas.drawText(String.valueOf(hoursNums[i/30]), x, y, paint);
        }

    }

    /**
     * Draw hours, minutes needles
     * Draw progress that indicates hours needle disposition.
     *
     * @param canvas
     */
    private void drawNeedles(final Canvas canvas) {
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        int nowHours = now.getHours() + 8; // 获得的时间事GTM时间，与北京时间差8个小时
        int nowMinutes = now.getMinutes();
        int nowSeconds = now.getSeconds();
        // 画秒针
        drawPointer(canvas, 2, nowSeconds);
        // 画分针

        int secPart = nowSeconds / 60;
        drawPointer(canvas, 1, nowMinutes + secPart);
        // todo 画分针
        // 画时针
        int part = nowMinutes / 12;
        drawPointer(canvas, 0, 5 * nowHours + part);
        Log.d("Hour", now.toString());

    }


    private void drawPointer(Canvas canvas, int pointerType, int value) {

        float degree;
        float[] pointerHeadXY = new float[2];

        mNeedlePaint.setStrokeWidth(mWidth * DEFAULT_DEGREE_STROKE_WIDTH);
        switch (pointerType) {
            case 0:
                degree = value * UNIT_DEGREE;
                mNeedlePaint.setColor(Color.WHITE);
                pointerHeadXY = getPointerHeadXY(HOUR_POINTER_LENGTH, degree);
                break;
            case 1:
                // todo 画分针，设置分针的颜色
                degree = value * UNIT_DEGREE;
                mNeedlePaint.setColor(Color.YELLOW);
                pointerHeadXY = getPointerHeadXY(MINUTE_POINTER_LENGTH, degree);
                break;
            case 2:
                degree = value * UNIT_DEGREE;
                mNeedlePaint.setColor(Color.GREEN);
                pointerHeadXY = getPointerHeadXY(SECOND_POINTER_LENGTH, degree);
                break;
        }


        canvas.drawLine(mCenterX, mCenterY, pointerHeadXY[0], pointerHeadXY[1], mNeedlePaint);
    }

    private float[] getPointerHeadXY(float pointerLength, float degree) {
        float[] xy = new float[2];
        xy[0] = (float) (mCenterX + pointerLength * Math.sin(degree));
        xy[1] = (float) (mCenterY - pointerLength * Math.cos(degree));
        return xy;
    }


}