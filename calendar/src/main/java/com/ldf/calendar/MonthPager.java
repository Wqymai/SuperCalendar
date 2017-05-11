package com.ldf.calendar;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;

public class MonthPager extends ViewPager {
    public static int CURRENT_DAY_INDEX = 600;
    private int mCellSpace;
    private ViewPager.OnPageChangeListener viewPageChangeListener;
    private OnPageChangeListener monthPageChangeListener;
    private boolean pageChangeByGesture = false;
    private boolean hasPageChangeListener = false;


    public MonthPager(Context context) {
        this(context, null);
        init();
    }

    public MonthPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        viewPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(monthPageChangeListener != null) {
                    monthPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (pageChangeByGesture) {
                    if(monthPageChangeListener != null) {
                        monthPageChangeListener.onPageSelected(position);
                    }
                    pageChangeByGesture = false;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(monthPageChangeListener != null) {
                    monthPageChangeListener.onPageScrollStateChanged(state);
                }
                pageChangeByGesture = true;
            }
        };
        addOnPageChangeListener(viewPageChangeListener);
        hasPageChangeListener = true;
    }

    @Override
    public void addOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        if(hasPageChangeListener) {
            Log.e("ldf","MonthPager Just Can Use Own OnPageChangeListener");
        } else {
            super.addOnPageChangeListener(listener);
        }
    }

    public void addOnPageChangeListener(OnPageChangeListener listener) {
        this.monthPageChangeListener = listener;
        Log.e("ldf","MonthPager Just Can Use Own OnPageChangeListener");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        mCellSpace = Math.min(h / 6, w / 7);
        super.onSizeChanged(w, h, oldW, oldH);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(mCellSpace > 0){
            super.onMeasure(widthMeasureSpec,MeasureSpec.makeMeasureSpec(mCellSpace * 6,
                    MeasureSpec.EXACTLY));
        } else {
            super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        }
    }

    public interface OnPageChangeListener {
        void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);
        void onPageSelected(int position);
        void onPageScrollStateChanged(int state);
    }
}