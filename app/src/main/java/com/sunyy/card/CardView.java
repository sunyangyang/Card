package com.sunyy.card;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by sunyangyang on 2018/5/2.
 */

public class CardView extends View {

    private ValueAnimator mAnimator;
    private MyAnimatorListener mListener;

    private int[] mVarietyIds = new int[]{R.mipmap.heitao, R.mipmap.meihua, R.mipmap.hongtao, R.mipmap.fangkuai};//前两个黑色，后两个红色，不可更改，init里面有用到
    private String[] mContents = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13"};
    private int[] mBlackIds = new int[]{R.mipmap.black_1, R.mipmap.black_2, R.mipmap.black_3, R.mipmap.black_4, R.mipmap.black_5, R.mipmap.black_6,
            R.mipmap.black_7, R.mipmap.black_8, R.mipmap.black_9, R.mipmap.black_10, R.mipmap.black_11, R.mipmap.black_12, R.mipmap.black_13};
    private int[] mRedIds = new int[]{R.mipmap.red_1, R.mipmap.red_2, R.mipmap.red_3, R.mipmap.red_4, R.mipmap.red_5, R.mipmap.red_6,
            R.mipmap.red_7, R.mipmap.red_8, R.mipmap.red_9, R.mipmap.red_10, R.mipmap.red_11, R.mipmap.red_12, R.mipmap.red_13};
    private int mMaxCount = mVarietyIds.length;

    private List<String> mNumList = new ArrayList<String>();
    private List<MyInfo> mInfoList = new ArrayList<MyInfo>();
    private List<CardCell> mCellList = new ArrayList<CardCell>();
    private int mPaddingTop;
    private int mPaddingBottom;
    private int mCardWidth;
    private int mCardHeight;
    private int mVerticalSpace;
    private int mHorizontalSpace;
    private int mCardLayoutHeight;
    private Paint mPaint;
    private int mPageBlockPaddingLeft;
    private int mPageBlockPaddingRight;
    private Bitmap mCardBitmap;
    private Bitmap mTargetCardBitmap;
    private Resources mRes;
    private String mContent;
    private Context mContext;

    public CardView(Context context, String content) {
        super(context);
        mContext = context;
        mRes = context.getResources();
        init();
        setDefault();
        List<MyInfo> list = getInfoList();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                MyInfo info = list.get(i);
                CardCell cell = new CardCell(info.mContentBitmap, info.mVarietyBitmap);
                mCellList.add(cell);
            }
        }

        if (mAnimator == null) {
            mAnimator = ValueAnimator.ofFloat(180, 0);
            mAnimator.setDuration(1000);
        }
        if (mListener == null) {
            mListener = new MyAnimatorListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    postInvalidateSelf((Float) valueAnimator.getAnimatedValue());
                }

                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {

                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            };
        }

        mAnimator.addListener(mListener);
        mAnimator.addUpdateListener(mListener);
        mAnimator.start();
    }

    private void postInvalidateSelf(Float ry) {
        for (int i = 0; i < mCellList.size(); i++) {
            mCellList.get(i).setR(ry);
        }
        postInvalidate();
    }

    protected void setAnimatorListener(MyAnimatorListener listener) {
        mListener = listener;
    }

    public MyAnimatorListener getAnimatorListener() {
        return mListener;
    }

    protected void setAnimator(ValueAnimator animator) {
        mAnimator = animator;
    }

    protected ValueAnimator getAnimator() {
        return mAnimator;
    }

    protected void setDefault() {
        mPaddingTop = Const.dip2px(mContext, 30);//牌转向时，会有一边比较大
        mPaddingBottom = Const.dip2px(mContext, 30);
        mCardWidth = Const.dip2px(mContext, 125);
        mCardHeight = Const.dip2px(mContext, 154);
        mVerticalSpace = Const.dip2px(mContext, 60);
        mHorizontalSpace = Const.dip2px(mContext, 20);
        mCardLayoutHeight = mPaddingBottom + mPaddingTop + mCardHeight * 2 + mVerticalSpace;
        mPageBlockPaddingLeft = Const.dip2px(mContext, 10);
        mPageBlockPaddingRight = Const.dip2px(mContext, 10);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCardBitmap = BitmapFactory.decodeResource(mRes, R.mipmap.tw_card);
    }

    protected void init() {
        //TODO 可以自己做数据，我这边先用random
        int max = 13;
        int min = 1;
        Random random = new Random();
        for (int i = 0; i < mMaxCount; i++) {
            int s = random.nextInt(max) % (max - min + 1) + min;
            mNumList.add(String.valueOf(s));
        }
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < mMaxCount; i++) {
            list.add(mVarietyIds[i]);
        }
        boolean isBlack = true;
        if (mNumList.size() > 0) {
            for (int i = 0; i < mNumList.size(); i++) {
                MyInfo info = new MyInfo();
                info.mContent = mNumList.get(i);
                int index = random.nextInt(list.size());
                int id = list.get(index);
                list.remove(Integer.valueOf(id));
                if (id == mVarietyIds[0] || id == mVarietyIds[1]) {
                    isBlack = true;
                } else {
                    isBlack = false;
                }
                info.mVarietyBitmap = BitmapFactory.decodeResource(mRes, id);
                for (int j = 0; j < mContents.length; j++) {
                    if (TextUtils.equals(info.mContent, mContents[j])) {
                        if (isBlack) {
                            info.mContentBitmap = BitmapFactory.decodeResource(mRes, mBlackIds[j]);
                        } else {
                            info.mContentBitmap = BitmapFactory.decodeResource(mRes, mRedIds[j]);
                        }
                    }
                }
                mInfoList.add(info);
            }
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect rect = new Rect();
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        rect.set(0, 0, width, height);
        canvas.translate((float)(width - (mCardWidth * 2 + mHorizontalSpace)) / 2, mPaddingTop);
        for (int i = 0; i < mCellList.size(); i++) {
            CardCell cell = mCellList.get(i);
            int line = i / 2;
            int column = i % 2;
            int top = rect.top + (line * (mCardHeight + mVerticalSpace));
            int left = rect.left + (column * (mCardWidth + mHorizontalSpace));
            Rect cellRect = new Rect(left, top, left + mCardWidth, top + mCardHeight);
            if (getCardBitmap() != null && mTargetCardBitmap == null) {
                mTargetCardBitmap = Bitmap.createScaledBitmap(getCardBitmap(), cellRect.width(), cellRect.height(), false);
            }
            cell.draw(canvas, cellRect, mTargetCardBitmap);
        }
    }

    protected Bitmap getCardBitmap() {
        return mCardBitmap;
    }

    public interface MyAnimatorListener extends ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {
    }

    public class MyInfo {
        String mContent;
        Bitmap mVarietyBitmap;
        Bitmap mContentBitmap;
    }

    protected List<MyInfo> getInfoList() {
        return mInfoList;
    }
}
