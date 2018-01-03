package com.ycuwq.datepicker.date;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.ycuwq.datepicker.R;

/**
 * 日期选择器
 * Created by 杨晨 on 2018/1/1.
 */
@SuppressWarnings("unused")
public class DatePicker extends LinearLayout implements YearPicker.OnYearSelectedListener,
		MonthPicker.OnMonthSelectedListener, DayPicker.OnDaySelectedListener {

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

		LayoutInflater.from(context).inflate(R.layout.layout_date, this);
		mContext = context;
		initAttrs(context, attrs);
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
		mYearPicker = findViewById(R.id.yearPicker);
		mYearPicker.setOnYearSelectedListener(this);
		mMonthPicker = findViewById(R.id.monthPicker);
		mMonthPicker.setOnMonthSelectedListener(this);
		mDayPicker = findViewById(R.id.dayPicker);
		mDayPicker.setOnDaySelectedListener(this);
	}

	@Override
	public void onMonthSelected(int month) {

	}

	@Override
	public void onDaySelected(int day) {

	}

	@Override
	public void onYearSelected(int year) {

	}
}
