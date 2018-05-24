package com.jocoo.chartdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class HistogramChartView extends View {
    private Paint mPaint;
    final static int RADIUS = 20;

    public HistogramChartView(Context context) {
        this(context, null);
    }

    public HistogramChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HistogramChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawHistogramTopArc(canvas, getWidth() / 2, getHeight(), 0.50f, new int[]{0xffA8DA50, 0xff70C128});

        drawHistogramSegment(canvas, getWidth() / 3, getHeight(), 0.50f, new int[]{0xffcccccc, 0xffA8DA50, 0xff70C128});
    }

    private void drawHistogramSegment(final Canvas canvas, float centerX, float centerY, float percent, int[] color) {
        int histogram_height = (int) (getHeight() * percent);
        RectF top_rect = new RectF();
        top_rect.set(centerX - RADIUS, 0,
                centerX + RADIUS, centerY - histogram_height);
        RectF bottom_rect = new RectF();
        bottom_rect.set(centerX - RADIUS, centerY - histogram_height,
                centerX + RADIUS, centerY);

        // top
        mPaint.setColor(color[0]);
        mPaint.setShader(null);
        canvas.drawRect(top_rect, mPaint);

        // bottom
        mPaint.setShader(
                new LinearGradient(
                        centerX, centerY, centerX, centerY - histogram_height,
                        color[1], color[2], Shader.TileMode.CLAMP));
        canvas.drawRect(bottom_rect, mPaint);
    }

    private void drawHistogramTopArc(final Canvas canvas, float centerX, float centerY, float percent, int[] color) {
        Path path = new Path();
        path.moveTo(centerX, centerY);
        path.lineTo(centerX - RADIUS, centerY);
        int histogram_height = (int) (getHeight() * percent);
        int rect_height = (histogram_height - RADIUS);
        path.lineTo(centerX - RADIUS, centerY - rect_height);
        RectF top_arc_rect = new RectF();
        top_arc_rect.set(centerX - RADIUS, centerY - histogram_height,
                centerX + RADIUS, centerY - histogram_height + 2 * RADIUS);
        path.addArc(top_arc_rect, -180, 180);
        path.lineTo(centerX + RADIUS, centerY);
        path.lineTo(centerX, centerY);
        path.close();
        mPaint.setShader(new LinearGradient(centerX, centerY, centerX, centerY - histogram_height, color[0], color[1], Shader.TileMode.CLAMP));
        canvas.drawPath(path, mPaint);
    }
}
