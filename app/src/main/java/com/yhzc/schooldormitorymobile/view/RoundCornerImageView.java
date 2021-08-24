package com.yhzc.schooldormitorymobile.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import com.yhzc.schooldormitorymobile.R;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/4/23 11:47
 * @描述:
 */
public class RoundCornerImageView extends AppCompatImageView {
    private float width, height;
    private int leftTopRadius;
    private int rightTopRadius;
    private int rightBottomRadius;
    private int leftBottomRadius;

    public RoundCornerImageView(Context context) {
        this(context, null);
        init(context, null);
    }

    public RoundCornerImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context, attrs);
    }

    public RoundCornerImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        // 读取配置
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoundCornerImageView);
        int defaultRadius = 0;
        int radius = array.getDimensionPixelOffset(R.styleable.RoundCornerImageView_radius, defaultRadius);
        leftTopRadius = array.getDimensionPixelOffset(R.styleable.RoundCornerImageView_left_top_radius, defaultRadius);
        rightTopRadius = array.getDimensionPixelOffset(R.styleable.RoundCornerImageView_right_top_radius, defaultRadius);
        rightBottomRadius = array.getDimensionPixelOffset(R.styleable.RoundCornerImageView_right_bottom_radius, defaultRadius);
        leftBottomRadius = array.getDimensionPixelOffset(R.styleable.RoundCornerImageView_left_bottom_radius, defaultRadius);
        //如果四个角的值没有设置，那么就使用通用的radius的值。
        if (defaultRadius == leftTopRadius) {
            leftTopRadius = radius;
        }
        if (defaultRadius == rightTopRadius) {
            rightTopRadius = radius;
        }
        if (defaultRadius == rightBottomRadius) {
            rightBottomRadius = radius;
        }
        if (defaultRadius == leftBottomRadius) {
            leftBottomRadius = radius;
        }
        array.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //这里做下判断，只有图片的宽高大于设置的圆角距离的时候才进行裁剪
        int maxLeft = Math.max(leftTopRadius, leftBottomRadius);
        int maxRight = Math.max(rightTopRadius, rightBottomRadius);
        int minWidth = maxLeft + maxRight;
        int maxTop = Math.max(leftTopRadius, rightTopRadius);
        int maxBottom = Math.max(leftBottomRadius, rightBottomRadius);
        int minHeight = maxTop + maxBottom;
        if (width >= minWidth && height >= minHeight) {
            Path path = new Path();
            //四个角：右上，右下，左下，左上
            path.moveTo(leftTopRadius, 0);
            path.lineTo(width - rightTopRadius, 0);
            path.quadTo(width, 0, width, rightTopRadius);
            path.lineTo(width, height - rightBottomRadius);
            path.quadTo(width, height, width - rightBottomRadius, height);
            path.lineTo(leftBottomRadius, height);
            path.quadTo(0, height, 0, height - leftBottomRadius);
            path.lineTo(0, leftTopRadius);
            path.quadTo(0, 0, leftTopRadius, 0);
            canvas.clipPath(path);
        }
        super.onDraw(canvas);
    }
}
