package com.ycuwq.datepicker.date;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.ycuwq.datepicker.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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

	private OnDateSelectedListener mOnDateSelectedListener;

	public DatePicker(Context context) {
		this(context, null);
	}

	public DatePicker(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public DatePicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		LayoutInflater.from(context).inflate(R.layout.layout_date, this);
		initChild();
		initAttrs(context, attrs);
	}

	private void initAttrs(Context context, @Nullable AttributeSet attrs) {
		if (attrs == null) {
			return;
		}
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DatePicker);
		int textSize = a.getDimensionPixelSize(R.styleable.DatePicker_dateTextSize,
				getResources().getDimensionPixelSize(R.dimen.WheelItemTextSize));
		int textColor = a.getColor(R.styleable.DatePicker_dateTextColor,
				Color.BLACK);
		boolean isTextGradual = a.getBoolean(R.styleable.DatePicker_dateTextGradual, true);
		boolean isCyclic = a.getBoolean(R.styleable.DatePicker_cyclic, false);
		int halfVisibleItemCount = a.getInteger(R.styleable.DatePicker_dateHalfVisibleItemCount, 2);
		int selectedItemTextColor = a.getColor(R.styleable.DatePicker_dateSelectedTextColor,
				getResources().getColor(R.color.selectedTextColor));
		int selectedItemTextSize = a.getDimensionPixelSize(R.styleable.DatePicker_dateSelectedTextSize,
				getResources().getDimensionPixelSize(R.dimen.WheelSelectedItemTextSize));
		int itemWidthSpace = a.getDimensionPixelSize(R.styleable.DatePicker_dateItemWidthSpace,
				getResources().getDimensionPixelOffset(R.dimen.WheelItemWidthSpace));
		int itemHeightSpace = a.getDimensionPixelSize(R.styleable.DatePicker_dateItemHeightSpace,
				getResources().getDimensionPixelOffset(R.dimen.WheelItemHeightSpace));
		boolean isZoomInCenterItem = a.getBoolean(R.styleable.DatePicker_dateZoomInCenterItem, true);
		boolean isShowCurtain = a.getBoolean(R.styleable.DatePicker_curtain, true);
		int curtainColor = a.getColor(R.styleable.DatePicker_curtainColor, Color.WHITE);
		boolean isShowCurtainBorder = a.getBoolean(R.styleable.DatePicker_curtainBorder, true);
		int curtainBorderColor = a.getColor(R.styleable.DatePicker_curtainBorderColor,
				getResources().getColor(R.color.divider));
		a.recycle();

		setTextSize(textSize);
		setTextColor(textColor);
		setTextGradual(isTextGradual);
		setCyclic(isCyclic);
		setHalfVisibleItemCount(halfVisibleItemCount);
		setSelectedItemTextColor(selectedItemTextColor);
		setSelectedItemTextSize(selectedItemTextSize);
		setItemWidthSpace(itemWidthSpace);
		setItemHeightSpace(itemHeightSpace);
		setZoomInCenterItem(isZoomInCenterItem);
		setShowCurtain(isShowCurtain);
		setCurtainColor(curtainColor);
		setShowCurtainBorder(isShowCurtainBorder);
		setCurtainBorderColor(curtainBorderColor);
	}
	private void initChild() {
		mYearPicker = findViewById(R.id.yearPicker);
		mYearPicker.setOnYearSelectedListener(this);
		mMonthPicker = findViewById(R.id.monthPicker);
		mMonthPicker.setOnMonthSelectedListener(this);
		mDayPicker = findViewById(R.id.dayPicker);
		mDayPicker.setOnDaySelectedListener(this);
	}

	private void onDateSelected() {
		if (mOnDateSelectedListener != null) {
			mOnDateSelectedListener.onDateSelected(mYearPicker.getSelectedYear(),
					mMonthPicker.getSelectedMonth(), mDayPicker.getSelectedDay());
		}
	}


	@Override
	public void onMonthSelected(int month) {
		mDayPicker.setMonth(getYear(), month);
		onDateSelected();
	}

	@Override
	public void onDaySelected(int day) {
		onDateSelected();
	}

	@Override
	public void onYearSelected(int year) {
		int month = getMonth();
		mDayPicker.setMonth(year, month);
		onDateSelected();
	}

	public void setDate(int year, int month, int day) {
		setDate(year, month, day, true);
	}

	public void setDate(int year, int month, int day, boolean smoothScroll) {
		mYearPicker.setSelectedYear(year, smoothScroll);
		mMonthPicker.setSelectedMonth(month, smoothScroll);
		mDayPicker.setSelectedDay(day, smoothScroll);
	}

	public String getDate() {
		DateFormat format = SimpleDateFormat.getDateInstance();
		return getDate(format);
	}

	public String getDate(@NonNull DateFormat dateFormat) {
		int year, month, day;
		year = getYear();
		month = getMonth();
		day = getDay();
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day);

		return dateFormat.format(calendar.getTime());
	}

	public int getYear() {
		return mYearPicker.getSelectedYear();
	}

	public int getMonth() {
		return mMonthPicker.getSelectedMonth();
	}

	public int getDay() {
		return mDayPicker.getSelectedDay();
	}

	public YearPicker getYearPicker() {
		return mYearPicker;
	}

	public MonthPicker getMonthPicker() {
		return mMonthPicker;
	}

	public DayPicker getDayPicker() {
		return mDayPicker;
	}

	/**
	 * 一般列表的文本颜色
	 * @param textColor 文本颜色
	 */
	public void setTextColor(@ColorInt int textColor) {
		mDayPicker.setTextColor(textColor);
		mMonthPicker.setTextColor(textColor);
		mYearPicker.setTextColor(textColor);
	}

	/**
	 * 一般列表的文本大小
	 * @param textSize 文字大小
	 */
	public void setTextSize(int textSize) {
		mDayPicker.setTextSize(textSize);
		mMonthPicker.setTextSize(textSize);
		mYearPicker.setTextSize(textSize);
	}

	/**
	 * 设置被选中时候的文本颜色
	 * @param selectedItemTextColor 文本颜色
	 */
	public void setSelectedItemTextColor(@ColorInt int selectedItemTextColor) {
		mDayPicker.setSelectedItemTextColor(selectedItemTextColor);
		mMonthPicker.setSelectedItemTextColor(selectedItemTextColor);
		mYearPicker.setSelectedItemTextColor(selectedItemTextColor);
	}

	/**
	 * 设置被选中时候的文本大小
	 * @param selectedItemTextSize 文字大小
	 */
	public void setSelectedItemTextSize(int selectedItemTextSize) {
		mDayPicker.setSelectedItemTextSize(selectedItemTextSize);
		mMonthPicker.setSelectedItemTextSize(selectedItemTextSize);
		mYearPicker.setSelectedItemTextSize(selectedItemTextSize);
	}


	/**
	 * 设置显示数据量的个数的一半。
	 * 为保证总显示个数为奇数,这里将总数拆分，总数为 mHalfVisibleItemCount * 2 + 1
	 * @param halfVisibleItemCount 总数量的一半
	 */
	public void setHalfVisibleItemCount(int halfVisibleItemCount) {
		mDayPicker.setHalfVisibleItemCount(halfVisibleItemCount);
		mMonthPicker.setHalfVisibleItemCount(halfVisibleItemCount);
		mYearPicker.setHalfVisibleItemCount(halfVisibleItemCount);
	}

	public void setItemWidthSpace(int itemWidthSpace) {
		mDayPicker.setItemWidthSpace(itemWidthSpace);
		mMonthPicker.setItemWidthSpace(itemWidthSpace);
		mYearPicker.setItemWidthSpace(itemWidthSpace);
	}

	/**
	 * 设置两个Item之间的间隔
	 * @param itemHeightSpace 间隔值
	 */
	public void setItemHeightSpace(int itemHeightSpace) {
		mDayPicker.setItemHeightSpace(itemHeightSpace);
		mMonthPicker.setItemHeightSpace(itemHeightSpace);
		mYearPicker.setItemHeightSpace(itemHeightSpace);
	}


	public void setZoomInCenterItem(boolean zoomInCenterItem) {
		mDayPicker.setZoomInCenterItem(zoomInCenterItem);
		mMonthPicker.setZoomInCenterItem(zoomInCenterItem);
		mYearPicker.setZoomInCenterItem(zoomInCenterItem);
	}

	/**
	 * 设置是否循环滚动。
	 * @param cyclic 上下边界是否相邻
	 */
	public void setCyclic(boolean cyclic) {
		mDayPicker.setCyclic(cyclic);
		mMonthPicker.setCyclic(cyclic);
		mYearPicker.setCyclic(cyclic);
	}

	/**
	 * 设置文字渐变，离中心越远越淡。
	 * @param textGradual 是否渐变
	 */
	public void setTextGradual(boolean textGradual) {
		mDayPicker.setTextGradual(textGradual);
		mMonthPicker.setTextGradual(textGradual);
		mYearPicker.setTextGradual(textGradual);
	}


	/**
	 * 设置中心Item是否有幕布遮盖
	 * @param showCurtain 是否有幕布
	 */
	public void setShowCurtain(boolean showCurtain) {
		mDayPicker.setShowCurtain(showCurtain);
		mMonthPicker.setShowCurtain(showCurtain);
		mYearPicker.setShowCurtain(showCurtain);
	}

	/**
	 * 设置幕布颜色
	 * @param curtainColor 幕布颜色
	 */
	public void setCurtainColor(@ColorInt int curtainColor) {
		mDayPicker.setCurtainColor(curtainColor);
		mMonthPicker.setCurtainColor(curtainColor);
		mYearPicker.setCurtainColor(curtainColor);
	}

	/**
	 * 设置幕布是否显示边框
	 * @param showCurtainBorder 是否有幕布边框
	 */
	public void setShowCurtainBorder(boolean showCurtainBorder) {
		mDayPicker.setShowCurtainBorder(showCurtainBorder);
		mMonthPicker.setShowCurtainBorder(showCurtainBorder);
		mYearPicker.setShowCurtainBorder(showCurtainBorder);
	}

	/**
	 * 幕布边框的颜色
	 * @param curtainBorderColor 幕布边框颜色
	 */
	public void setCurtainBorderColor(@ColorInt int curtainBorderColor) {
		mDayPicker.setCurtainBorderColor(curtainBorderColor);
		mMonthPicker.setCurtainBorderColor(curtainBorderColor);
		mYearPicker.setCurtainBorderColor(curtainBorderColor);
	}

	/**
	 * 设置选择器的指示器文本
	 * @param yearText 年指示器文本
	 * @param monthText 月指示器文本
	 * @param dayText 日指示器文本
	 */
	public void setIndicatorText(String yearText, String monthText, String dayText) {
		mYearPicker.setIndicatorText(yearText);
		mMonthPicker.setIndicatorText(monthText);
		mDayPicker.setIndicatorText(dayText);
	}

	/**
	 * 设置指示器文字的颜色
	 * @param textColor 文本颜色
	 */
	public void setIndicatorTextColor(@ColorInt int textColor) {
		mYearPicker.setIndicatorTextColor(textColor);
		mMonthPicker.setIndicatorTextColor(textColor);
		mDayPicker.setIndicatorTextColor(textColor);
	}

	/**
	 * 设置指示器文字的大小
	 * @param textSize 文本大小
	 */
	public void setIndicatorTextSize(int textSize) {
		mYearPicker.setTextSize(textSize);
		mMonthPicker.setTextSize(textSize);
		mDayPicker.setTextSize(textSize);
	}

	public void setOnDateSelectedListener(OnDateSelectedListener onDateSelectedListener) {
		mOnDateSelectedListener = onDateSelectedListener;
	}

	public interface OnDateSelectedListener {
		void onDateSelected(int year, int month, int day);
	}
}
