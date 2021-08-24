package com.yhzc.schooldormitorymobile.view.calendarView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.blankj.utilcode.util.ColorUtils;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.MonthView;

/**
 * 演示一个变态需求的月视图
 * Created by huanghaibin on 2018/2/9.
 */

public class CustomMonthView extends MonthView {

    private int mRadius;

    /**
     * 背景圆点
     */
    private final Paint mPointPaint = new Paint();

    /**
     * 今天的背景色
     */
    private final Paint mCurrentDayPaint = new Paint();

    /**
     * 圆点半径
     */
    private final float mPointRadius;

    private final int mPadding;

    public CustomMonthView(Context context) {
        super(context);


        Paint schemeBasicPaint = new Paint();
        schemeBasicPaint.setAntiAlias(true);
        schemeBasicPaint.setStyle(Paint.Style.FILL);
        schemeBasicPaint.setTextAlign(Paint.Align.CENTER);
        schemeBasicPaint.setFakeBoldText(true);
        schemeBasicPaint.setColor(Color.WHITE);


        mCurrentDayPaint.setAntiAlias(true);
        mCurrentDayPaint.setStyle(Paint.Style.FILL);
        mCurrentDayPaint.setColor(0xFFeaeaea);

        mPointPaint.setAntiAlias(true);
        mPointPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setTextAlign(Paint.Align.CENTER);
        mPointPaint.setColor(Color.RED);

        float circleRadius = dipToPx(getContext(), 7);

        mPadding = dipToPx(getContext(), 8);

        mPointRadius = dipToPx(context, 2);

        Paint.FontMetrics metrics = schemeBasicPaint.getFontMetrics();
        float schemeBaseLine = circleRadius - metrics.descent + (metrics.bottom - metrics.top) / 2 + dipToPx(getContext(), 1);
    }


    @Override
    protected void onPreviewHook() {
        mRadius = Math.min(mItemWidth, mItemHeight) / 11 * 4;
    }


    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2 - 15;
        canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);
        return true;
    }

    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight - mPadding;

        mPointPaint.setColor(calendar.getSchemeColor());
        canvas.drawCircle(cx, cy, mPointRadius, mPointPaint);


    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        int top = y - mItemHeight / 6 + 10;


        if (!calendar.isCurrentDay() && !isSelected && hasScheme) {
            int alphaComponent = ColorUtils.setAlphaComponent(calendar.getSchemeColor(), 50);
            mCurrentDayPaint.setColor(alphaComponent);
            canvas.drawCircle(cx, cy - 15, mRadius, mCurrentDayPaint);
        }



        if (calendar.isCurrentDay() && !isSelected) {
            mCurrentDayPaint.setColor(Color.TRANSPARENT);
            canvas.drawCircle(cx, cy - 15, mRadius, mCurrentDayPaint);
        }

        mCurMonthTextPaint.setColor(0xff333333);
        mCurMonthLunarTextPaint.setColor(0xffCFCFCF);
        mSchemeTextPaint.setColor(0xff333333);
        mSchemeLunarTextPaint.setColor(0xffCFCFCF);

        mOtherMonthTextPaint.setColor(0xFFe1e1e1);
        mOtherMonthLunarTextPaint.setColor(0xFFe1e1e1);

        if (isSelected) {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
                    mSelectTextPaint);
        } else if (hasScheme) {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
                    calendar.isCurrentMonth() ? mSchemeTextPaint : mOtherMonthTextPaint);

        } else {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mCurMonthTextPaint : mOtherMonthTextPaint);
        }
    }

    /**
     * dp转px
     *
     * @param context context
     * @param dpValue dp
     * @return px
     */
    private static int dipToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
