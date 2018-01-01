package com.ycuwq.datepicker.date;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.ycuwq.datepicker.R;

/**
 * 日期选择器
 * Created by 杨晨 on 2018/1/1.
 */
@SuppressWarnings("unused")
public class DatePicker extends LinearLayout {

	private YearPicker mYearPicker;
	private MonthPicker mMonthPicker;
	private DayPicker mDayPicker;
	private Context mContext;

	public DatePicker(Context context) {
		this(context, null);
	}

	public DatePicker(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public DatePicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mContext = context;
		initAttrs(context, attrs);
		setOrientation(LinearLayout.HORIZONTAL);
		initChild();
	}

	private void initAttrs(Context context, @Nullable AttributeSet attrs) {
		if (attrs == null) {
			return;
		}
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DatePicker);
		a.recycle();
	}
	private void initChild() {
		mYearPicker = new YearPicker(mContext);
		mMonthPicker = new MonthPicker(mContext);
		mDayPicker = new DayPicker(mContext);
	}

}
