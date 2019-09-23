package com.ycuwq.datepicker.util;

import android.graphics.Color;

/**
 * 颜色线性渐变工具
 * Created by ycuwq on 2018/1/6.
 */
public class LinearGradient {

	private int mStartColor;
	private int mEndColor;
	private int mRedStart;
	private int mBlueStart;
    private int mGreenStart;
    private int mRedEnd;
    private int mBlueEnd;
    private int mGreenEnd;

	public LinearGradient(int startColor, int endColor) {
		mStartColor = startColor;
		mEndColor = endColor;
        updateColor();
	}


	public void setStartColor(int startColor) {
		mStartColor = startColor;
        updateColor();
	}

	public void setEndColor(int endColor) {
		mEndColor = endColor;
        updateColor();
	}

    private void updateColor() {
        mRedStart = Color.red(mStartColor);
        mBlueStart = Color.blue(mStartColor);
        mGreenStart = Color.green(mStartColor);
        mRedEnd = Color.red(mEndColor);
        mBlueEnd = Color.blue(mEndColor);
        mGreenEnd = Color.green(mEndColor);
    }

	public int getColor(float ratio) {
		int red = (int) (mRedStart + ((mRedEnd - mRedStart) * ratio + 0.5));
		int greed = (int) (mGreenStart + ((mGreenEnd - mGreenStart) * ratio + 0.5));
		int blue = (int) (mBlueStart + ((mBlueEnd - mBlueStart) * ratio + 0.5));
		return Color.rgb(red, greed, blue);
	}
}
