package com.song.fast.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.song.fast.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

/**
 * description: 轮播图
 * author: ss
 * date: 2017/10/28 13:43
 * update: 轮播图
 * version: 1.0
*/

public class BannerLayout extends LinearLayout {
    private ViewPager bannerViewPager;
    private LinearLayout bannerPointLayout;
    private ScheduledExecutorService scheduler;
    private int mPosition = 0;
    private int mBannerCount = 1;
    private Context context;
    private int bannerPointSize;
    private int bannerPointGravity;
    private int bannerPointDrawableSelected, bannerPointDrawableUnselected;
    private int bannerDelaySecond;
    private OnBannerClickListener onBannerClickListener;
    private OnImageLoad onImageLoad;
    private static Handler handler;
    private Runnable runnable;


    public BannerLayout(Context context) {
        this(context, null);
    }

    public BannerLayout(Context context,  AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerLayout(Context context,AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BannerLayout);
        bannerPointSize = typedArray.getDimensionPixelSize(R.styleable.BannerLayout_bannerPointSize, 10);
        bannerPointGravity = typedArray.getInt(R.styleable.BannerLayout_bannerPointGravity, Gravity.CENTER);
        bannerDelaySecond = typedArray.getInt(R.styleable.BannerLayout_bannerDelaySecond, 5);
        bannerPointDrawableSelected = typedArray.getResourceId(R.styleable.BannerLayout_bannerPointDrawableSelected, R.drawable.point01);
        bannerPointDrawableUnselected = typedArray.getResourceId(R.styleable.BannerLayout_bannerPointDrawableUnselected, R.drawable.point02);
        typedArray.recycle();
        View view = View.inflate(context, R.layout.custom_banner_layout, null);
        addView(view);
        bannerViewPager = (ViewPager) view.findViewById(R.id.bannerViewPager);
        bannerPointLayout = (LinearLayout) view.findViewById(R.id.bannerPointLayout);
        bannerPointLayout.setGravity(bannerPointGravity);
        bannerViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                addPointLayout(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void start(List<Object> bannerList) {
        handler = new Handler();
        bannerShutdown();
        mBannerCount = bannerList.size();
        BannerPagerAdapter bannerPagerAdapter = new BannerPagerAdapter(context, bannerList);
//        bannerViewPager.setPageTransformer(false,new CascadeZoomPageTransformer());
        bannerViewPager.setAdapter(bannerPagerAdapter);
        addPointLayout(0);
        startScheduler();
    }

    private void addPointLayout(int position) {
        bannerPointLayout.removeAllViews();
        for (int i = 0; i < mBannerCount; i++) {
            ImageView imageView = new ImageView(context);
            LayoutParams layoutParams = new LayoutParams(bannerPointSize, bannerPointSize);
            layoutParams.setMargins(10, 0, 0, 0);
            imageView.setLayoutParams(layoutParams);
            if (position == i) {
                imageView.setImageResource(bannerPointDrawableSelected);
            } else {
                imageView.setImageResource(bannerPointDrawableUnselected);
            }
            bannerPointLayout.addView(imageView);
        }
    }

    private void startScheduler() {
        runnable = new Runnable() {
            @Override
            public void run() {
                mPosition = bannerViewPager.getCurrentItem();
                if (mPosition < mBannerCount - 1) {
                    mPosition++;
                } else {
                    mPosition = 0;
                }
                bannerViewPager.setCurrentItem(mPosition,true);
                handler.postDelayed(this, bannerDelaySecond * 1000);
            }
        };
        handler.postDelayed(runnable, bannerDelaySecond * 1000);
    }

    public void bannerShutdown() {
        if (handler != null && runnable != null)
            handler.removeCallbacks(runnable);
    }

    private class BannerPagerAdapter extends PagerAdapter {
        private List<Object> bannerList = new ArrayList<>();
        private Context context;

        BannerPagerAdapter(Context context, List<Object> bannerList) {
            this.context = context;
            this.bannerList.clear();
            this.bannerList.addAll(bannerList);
        }

        @Override
        public int getCount() {
            return bannerList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Object object = bannerList.get(position);
            if (onImageLoad!=null) {
            onImageLoad.onLoad(imageView,object);
            }

            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onBannerClickListener != null) {
                        onBannerClickListener.onBannerClick(position);
                    }
                }
            });
            container.addView(imageView, layoutParams);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    public interface OnBannerClickListener {
        void onBannerClick(int position);
    }

    public interface OnImageLoad {
        void onLoad(ImageView imageView, Object object);
    }

    public int dp2px(float var0) {
        float var1 = context.getResources().getDisplayMetrics().density;
        return (int) (var0 * var1 + 0.5F);
    }

    public BannerLayout setBannerPointSize(int bannerPointSize) {
        this.bannerPointSize = dp2px(bannerPointSize);
        return this;
    }

    public BannerLayout setBannerPointGravity(int bannerPointGravity) {
        this.bannerPointGravity = bannerPointGravity;
        bannerPointLayout.setGravity(bannerPointGravity);
        return this;
    }

    public BannerLayout setBannerPointDrawableSelected(int bannerPointDrawableSelected) {
        this.bannerPointDrawableSelected = bannerPointDrawableSelected;
        return this;
    }

    public BannerLayout setBannerPointDrawableUnselected(int bannerPointDrawableUnselected) {
        this.bannerPointDrawableUnselected = bannerPointDrawableUnselected;
        return this;
    }

    public BannerLayout setBannerDelaySecond(int bannerDelaySecond) {
        this.bannerDelaySecond = bannerDelaySecond;
        return this;
    }

    public BannerLayout setOnBannerClickListener(OnBannerClickListener onBannerClickListener) {
        this.onBannerClickListener = onBannerClickListener;
        return this;
    }

    public BannerLayout setOnImageLoad(OnImageLoad onImageLoad) {
        this.onImageLoad = onImageLoad;
        return this;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        bannerShutdown();
        if (handler!=null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    /*static class WeakHandler extends Handler { handler泄漏的问题
        WeakReference<Activity> mWeakReference;

        public WeakHandler(WeakReference<Activity> weakReference) {
            mWeakReference = weakReference;
        }

        @Override
        public void handleMessage(Message msg) {
            Activity activity = mWeakReference.get();
            super.handleMessage(msg);
            if (activity != null) {

            }
        }
    }*/

}
