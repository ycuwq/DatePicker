package com.ycuwq.datepicker.util;

import android.graphics.Color;
import android.support.annotation.ColorInt;

/**
 * 颜色线性渐变工具
 * Created by ycuwq on 2018/1/6.
 */
public class LinearGradient {
	private int mStartColor;
	private int mEndColor;

	public LinearGradient(@ColorInt int startColor, @ColorInt int endColor) {
		mStartColor = startColor;
		mEndColor = endColor;
	}

	public void setStartColor(@ColorInt int startColor) {
		mStartColor = startColor;
	}

	public void setEndColor(@ColorInt int endColor) {
		mEndColor = endColor;
	}

	public int getColor(float radio) {
		int redStart = Color.red(mStartColor);
		int blueStart = Color.blue(mStartColor);
		int greenStart = Color.green(mStartColor);
		int redEnd = Color.red(mEndColor);
		int blueEnd = Color.blue(mEndColor);
		int greenEnd = Color.green(mEndColor);

		int red = (int) (redStart + ((redEnd - redStart) * radio + 0.5));
		int greed = (int) (greenStart + ((greenEnd - greenStart) * radio + 0.5));
		int blue = (int) (blueStart + ((blueEnd - blueStart) * radio + 0.5));
		return Color.rgb(red, greed, blue);
	}
}
