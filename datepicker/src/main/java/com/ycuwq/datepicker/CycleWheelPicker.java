package com.ycuwq.datepicker;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

/**
 * 循环滚动选择器
 * Created by yangchen on 2017/12/12.
 */
public class CycleWheelPicker extends View {

	private List<String> mDataList;
	private int mTextColor;
	private Paint mPaint;

	public CycleWheelPicker(Context context) {
		this(context, null);
	}

	public CycleWheelPicker(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs,0);
	}

	public CycleWheelPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}


	private void init() {
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setTextAlign(Paint.Align.CENTER);
		mPaint.setColor(mTextColor);

	}
}
