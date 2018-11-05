package com.bawei.suquanwei_week1.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

/**
 * Created by 小哥 on 2018/11/5.
 */

public class ChoujiangView extends View implements View.OnClickListener {

    private Paint mPaint;
    private Paint strPaint;
    private int mWidth;
    private int mPadding;
    private RectF rectF;
    private String str = "开始";
    private boolean isStart = false;
    public RotateAnimation rotateAnimation;
    private String[] contents = new String[]{"开心","无心","有心","放心","担心","惊心"};
    public ChoujiangView(Context context) {
        this(context,null);
    }

    public ChoujiangView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public ChoujiangView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initData();
        setOnClickListener(this);
    }

    private void initData() {
        //以view的中心圆点旋转为参考
        rotateAnimation = new RotateAnimation(0f,360f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setRepeatCount(-1);
        rotateAnimation.setFillAfter(true);
    }
    private void initView() {
        strPaint = new Paint();
        strPaint.setStyle(Paint.Style.STROKE);
        strPaint.setAntiAlias(true);
        strPaint.setColor(Color.WHITE);
        strPaint.setStrokeWidth(5);

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(2);
        mPaint.setStrokeWidth(3);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(500,500);
        mWidth = getMeasuredWidth();
        mPadding = 5;
        initRect();
    }

    private void initRect() {
        rectF = new RectF(0,0,mWidth,mWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制圆
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(mWidth / 2,mWidth / 2,mWidth / 2 - mPadding,mPaint);
        //绘制六个椭圆
        mPaint.setStyle(Paint.Style.FILL);

        initArc(canvas);

        //绘制里面的小圆
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mWidth / 2,mWidth / 2,50,mPaint);
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(24);
        Rect rect = new Rect();
        mPaint.getTextBounds(str,0,str.length(),rect);
        int strWidth = rect.width();
        int textHeight = rect.height();
        canvas.drawText(str,mWidth / 2 - 25 + 25-strWidth / 2,mWidth / 2+textHeight / 2,mPaint);
    }

    /**
     * 设置六个弧线
     * 对应文字
     */
    private void initArc(Canvas canvas) {
        for (int i = 0; i < 6; i++) {
            mPaint.setColor(colors[i]);
            canvas.drawArc(rectF,(i-1) * 60 + 60,60,true,mPaint);
        }
        for (int i = 0; i < 6; i++) {
            mPaint.setColor(Color.BLACK);
            mPaint.setTextSize(40);
            Path path = new Path();
            path.addArc(rectF,(i-1) * 60 + 60,60);
            canvas.drawTextOnPath(contents[i],path,60,60,mPaint);
        }
    }
    public int[] colors = new int[]{Color.parseColor("#FFBB00"),Color.parseColor("#CCFF33"),Color.parseColor("#BA55D3"),Color.parseColor("#FF4500"),Color.parseColor("#87CECB"),Color.parseColor("#007799")};
   //设置动画
    @Override
    public void onClick(View view) {

        if (!isStart) {
            isStart = true;
            rotateAnimation.setDuration(1000);
            rotateAnimation.setInterpolator(new LinearInterpolator());//不停顿
            startAnimation(rotateAnimation);
        } else {
            isStart = false;
            stopAnim();
        }
    }

    /**
     * 停止动画
     */
    public void stopAnim() {
        clearAnimation();
    }
}
