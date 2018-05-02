package com.sunyy.card;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by sunyangyang on 2018/5/2.
 */

public class Const {

    public Const() {
    }

    /*
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
    public static int dip2px(Context context, float dpValue) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        float scale = dm.density;
        return (int) (dpValue * scale + 0.5f);
    }
}
