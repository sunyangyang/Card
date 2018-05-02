package com.sunyy.card;

import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by sunyangyang on 2018/5/2.
 */

public class CardCell {
    private Paint mPaint;
    private Matrix mMatrix;
    private Bitmap mContentBitmap;
    private Bitmap mVarietyBitmap;
    private Bitmap mTargetVarietyBitmap;
    private Bitmap mTargetContentBitmap;
    private float mRy;
    private Camera mCamera;
    public CardCell(Bitmap contentBitmap, Bitmap varietyBitmap) {
        mCamera = new Camera();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);
        mMatrix = new Matrix();
        mContentBitmap = contentBitmap;
        mVarietyBitmap = varietyBitmap;

    }

    public void draw(Canvas canvas, Rect rect, Bitmap cardBitmap) {
        canvas.save();
        canvas.translate(rect.left + rect.width(), rect.top + rect.height() / 2);
        mCamera.save();
        mCamera.translate(0, rect.height() / 2, 0);
        mCamera.rotateY(mRy);

        mCamera.getMatrix(mMatrix);
        mCamera.restore();

        mMatrix.preTranslate(-rect.width() / 2, 0);
        mMatrix.postTranslate(-rect.width() / 2, 0);

        if (mTargetVarietyBitmap == null && mVarietyBitmap != null) {
            mTargetVarietyBitmap = Bitmap.createScaledBitmap(mVarietyBitmap, rect.width(), rect.height(), false);
        }

        if (mTargetContentBitmap == null && mContentBitmap != null && mVarietyBitmap != null) {
            float sx = rect.width() * 1.0f / mVarietyBitmap.getWidth();
            float sy = rect.height() * 1.0f / mVarietyBitmap.getHeight();
            mTargetContentBitmap = Bitmap.createScaledBitmap(mContentBitmap, (int) (mContentBitmap.getWidth() * sx), (int) (mContentBitmap.getHeight() * sy), false);
        }

        float tx = (rect.width() - mTargetContentBitmap.getWidth()) / 2;
        float ty = (rect.height() - mTargetContentBitmap.getHeight()) / 2;
        if (mRy > 90) {
            if (cardBitmap != null) {
                canvas.drawBitmap(cardBitmap, mMatrix, mPaint);
            }
        } else {
            if (mTargetVarietyBitmap != null) {
                canvas.drawBitmap(mTargetVarietyBitmap, mMatrix, mPaint);
            }
            if (mTargetContentBitmap != null) {
                mMatrix.preTranslate(tx, ty);
                canvas.drawBitmap(mTargetContentBitmap, mMatrix, mPaint);
                mMatrix.postTranslate(tx, ty);
            }
        }
        canvas.restore();
    }

    public void setR(float ry) {
        mRy = ry;
    }
}
