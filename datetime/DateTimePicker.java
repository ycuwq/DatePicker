package com.ycuwq.datepicker.datetime;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.ycuwq.datepicker.R;
import com.ycuwq.datepicker.date.DayPicker;
import com.ycuwq.datepicker.date.MonthPicker;
import com.ycuwq.datepicker.date.YearPicker;
import com.ycuwq.datepicker.time.HourPicker;
import com.ycuwq.datepicker.time.MinutePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateTimePicker extends FrameLayout implements YearPicker.OnYearSelectedListener,
        MonthPicker.OnMonthSelectedListener, DayPicker.OnDaySelectedListener,HourPicker.OnHourSelectedListener,
        MinutePicker.OnMinuteSelectedListener  {

    private YearPicker mYearPicker;
    private MonthPicker mMonthPicker;
    private DayPicker mDayPicker;
    private HourPicker mHourPicker;
    private MinutePicker mMinutePicker;

    private Long mMaxDate;
    private Long mMinDate;

    OnDateTimeSelectedListener mOnDateTimeSelectedListener;

    public DateTimePicker(Context context) {
        this(context, null);
    }

    public DateTimePicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DateTimePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.layout_datetime, this);
        initChild();
        initAttrs(context, attrs);
        mYearPicker.setBackgroundDrawable(getBackground());
        mMonthPicker.setBackgroundDrawable(getBackground());
        mDayPicker.setBackgroundDrawable(getBackground());
        mHourPicker.setBackgroundDrawable(getBackground());
        mMinutePicker.setBackgroundDrawable(getBackground());


    }

    private void initAttrs(Context context, @Nullable AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DateTimePicker);
        int textSize = a.getDimensionPixelSize(R.styleable.DateTimePicker_itemTextSize,
                getResources().getDimensionPixelSize(R.dimen.WheelItemTextSize));
        int textColor = a.getColor(R.styleable.DateTimePicker_itemTextColor,
                Color.BLACK);
        boolean isTextGradual = a.getBoolean(R.styleable.DateTimePicker_textGradual, true);
        boolean isCyclic = a.getBoolean(R.styleable.DateTimePicker_wheelCyclic, false);
        int halfVisibleItemCount = a.getInteger(R.styleable.DateTimePicker_halfVisibleItemCount, 2);
        int selectedItemTextColor = a.getColor(R.styleable.DateTimePicker_selectedTextColor,
                getResources().getColor(R.color.com_ycuwq_datepicker_selectedTextColor));
        int selectedItemTextSize = a.getDimensionPixelSize(R.styleable.DateTimePicker_selectedTextSize,
                getResources().getDimensionPixelSize(R.dimen.WheelSelectedItemTextSize));
        int itemWidthSpace = a.getDimensionPixelSize(R.styleable.DateTimePicker_itemWidthSpace,
                getResources().getDimensionPixelOffset(R.dimen.WheelItemWidthSpace));
        int itemHeightSpace = a.getDimensionPixelSize(R.styleable.DateTimePicker_itemHeightSpace,
                getResources().getDimensionPixelOffset(R.dimen.WheelItemHeightSpace));
        boolean isZoomInSelectedItem = a.getBoolean(R.styleable.DateTimePicker_zoomInSelectedItem, true);
        boolean isShowCurtain = a.getBoolean(R.styleable.DateTimePicker_wheelCurtain, true);
        int curtainColor = a.getColor(R.styleable.DateTimePicker_wheelCurtainColor, Color.WHITE);
        boolean isShowCurtainBorder = a.getBoolean(R.styleable.DateTimePicker_wheelCurtainBorder, true);
        int curtainBorderColor = a.getColor(R.styleable.DateTimePicker_wheelCurtainBorderColor,
                getResources().getColor(R.color.com_ycuwq_datepicker_divider));
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
        setZoomInSelectedItem(isZoomInSelectedItem);
        setShowCurtain(isShowCurtain);
        setCurtainColor(curtainColor);
        setShowCurtainBorder(isShowCurtainBorder);
        setCurtainBorderColor(curtainBorderColor);
    }



    private void initChild() {
        mYearPicker = findViewById(R.id.yearPicker_layout_date);
        mYearPicker.setOnYearSelectedListener(this);
        mMonthPicker = findViewById(R.id.monthPicker_layout_date);
        mMonthPicker.setOnMonthSelectedListener(this);
        mDayPicker = findViewById(R.id.dayPicker_layout_date);
        mDayPicker.setOnDaySelectedListener(this);
        mHourPicker = findViewById(R.id.hourPicker_layout_time);
        mHourPicker.setOnHourSelectedListener(this);
        mMinutePicker = findViewById(R.id.minutePicker_layout_time);
        mMinutePicker.setOnMinuteSelectedListener(this);
    }



    @Override
    public void onDaySelected(int day) {
        onDateSelected();
    }

    @Override
    public void onMonthSelected(int month) {
        mDayPicker.setMonth(getYear(), month);
        onDateSelected();
    }

    @Override
    public void onYearSelected(int year) {
        int month = getMonth();
        mMonthPicker.setYear(year);
        mDayPicker.setMonth(year, month);
        onDateSelected();
    }

    @Override
    public void onHourSelected(int hour) {
        onDateSelected();
    }

    @Override
    public void onMinuteSelected(int hour) {
        onDateSelected();
    }

    private void onDateSelected() {
        if (mOnDateTimeSelectedListener != null) {
            mOnDateTimeSelectedListener.onDateTimeSelected(getYear(),
                    getMonth(), getDay(),getHour(),getMinute());
        }
    }

    /**
     * Sets date.
     *
     * @param year  the year
     * @param month the month
     * @param day   the day
     */
    public void setDate(int year, int month, int day) {
        setDate(year, month, day, true);
    }

    /**
     * Sets date.
     *
     * @param year         the year
     * @param month        the month
     * @param day          the day
     * @param smoothScroll the smooth scroll
     */
    public void setDate(int year, int month, int day, boolean smoothScroll) {
        mYearPicker.setSelectedYear(year, smoothScroll);
        mMonthPicker.setSelectedMonth(month, smoothScroll);
        mDayPicker.setSelectedDay(day, smoothScroll);
    }

    /**
     * Sets date.
     *
     * @param year         the year
     * @param month        the month
     * @param day          the day
     * @param smoothScroll the smooth scroll
     */
    public void setDateTime(int year, int month, int day,int hour,int minute, boolean smoothScroll) {
        mYearPicker.setSelectedYear(year, smoothScroll);
        mMonthPicker.setSelectedMonth(month, smoothScroll);
        mDayPicker.setSelectedDay(day, smoothScroll);
        mHourPicker.setSelectedHour(hour);
        mMinutePicker.setSelectedMinute(minute);
    }


    public void setMaxDate(long date) {
        setCyclic(false);
        mMaxDate = date;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        mYearPicker.setEndYear(calendar.get(Calendar.YEAR));
        mMonthPicker.setMaxDate(date);
        mDayPicker.setMaxDate(date);
        mMonthPicker.setYear(mYearPicker.getSelectedYear());
        mDayPicker.setMonth(mYearPicker.getSelectedYear(), mMonthPicker.getSelectedMonth());
    }

    public void setMinDate(long date) {
        setCyclic(false);
        mMinDate = date;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        mYearPicker.setStartYear(calendar.get(Calendar.YEAR));
        mMonthPicker.setMinDate(date);
        mDayPicker.setMinDate(date);
        mMonthPicker.setYear(mYearPicker.getSelectedYear());
        mDayPicker.setMonth(mYearPicker.getSelectedYear(), mMonthPicker.getSelectedMonth());
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public String getDate() {
        DateFormat format = SimpleDateFormat.getDateInstance();
        return getDate(format);
    }

    /**
     * Gets date.
     *
     * @param dateFormat the date format
     * @return the date
     */
    public String getDate(@NonNull DateFormat dateFormat) {
        int year, month, day;
        year = getYear();
        month = getMonth();
        day = getDay();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);

        return dateFormat.format(calendar.getTime());
    }

    /**
     * Gets year.
     *
     * @return the year
     */
    public int getYear() {
        return mYearPicker.getSelectedYear();
    }

    /**
     * Gets month.
     *
     * @return the month
     */
    public int getMonth() {
        return mMonthPicker.getSelectedMonth();
    }

    /**
     * Gets day.
     *
     * @return the day
     */
    public int getDay() {
        return mDayPicker.getSelectedDay();
    }

    /**
     * Gets year picker.
     *
     * @return the year picker
     */
    public YearPicker getYearPicker() {
        return mYearPicker;
    }

    /**
     * Gets month picker.
     *
     * @return the month picker
     */
    public MonthPicker getMonthPicker() {
        return mMonthPicker;
    }

    /**
     * Gets day picker.
     *
     * @return the day picker
     */
    public DayPicker getDayPicker() {
        return mDayPicker;
    }

    /**
     * Sets time.
     *
     * @param hour         the year
     * @param minute        the month
     * @param smoothScroll the smooth scroll
     */
    public void setTime(int hour, int minute, boolean smoothScroll) {
        mHourPicker.setSelectedHour(hour, smoothScroll);
        mMinutePicker.setSelectedMinute(minute, smoothScroll);
    }

    /**
     * Gets hour.
     *
     * @return the hour
     */
    public int getHour() {
        return mHourPicker.getCurrentPosition();
    }


    /**
     * Gets minuute.
     *
     * @return the minute
     */
    public int getMinute() {
        return mMinutePicker.getCurrentPosition();
    }

    /**
     * Sets on date selected listener.
     *
     * @param onDateTimeSelectedListener the on time selected listener
     */
    public void setOnDateTimeSelectedListener(OnDateTimeSelectedListener onDateTimeSelectedListener) {
        mOnDateTimeSelectedListener = onDateTimeSelectedListener;
    }


    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
        if (mHourPicker != null) {
            mHourPicker.setBackgroundColor(color);
        }
        if (mMinutePicker != null) {
            mMinutePicker.setBackgroundColor(color);
        }
    }

    @Override
    public void setBackgroundResource(int resid) {
        super.setBackgroundResource(resid);
        if (mHourPicker != null) {
            mHourPicker.setBackgroundResource(resid);
        }
        if (mMinutePicker != null) {
            mMinutePicker.setBackgroundResource(resid);
        }
    }

    @Override
    public void setBackgroundDrawable(Drawable background) {
        super.setBackgroundDrawable(background);
        if (mHourPicker != null) {
            mHourPicker.setBackgroundDrawable(background);
        }
        if (mMinutePicker != null) {
            mMinutePicker.setBackgroundDrawable(background);
        }
    }


    public HourPicker getHourPicker() {
        return mHourPicker;
    }

    public MinutePicker getMinutePicker() {
        return mMinutePicker;
    }

    /**
     * 一般列表的文本颜色
     *
     * @param textColor 文本颜色
     */
    public void setTextColor(@ColorInt int textColor) {
        mDayPicker.setTextColor(textColor);
        mMonthPicker.setTextColor(textColor);
        mYearPicker.setTextColor(textColor);
        mHourPicker.setTextColor(textColor);
        mMinutePicker.setTextColor(textColor);
    }

    /**
     * 一般列表的文本大小
     *
     * @param textSize 文字大小
     */
    public void setTextSize(int textSize) {
        mDayPicker.setTextSize(textSize);
        mMonthPicker.setTextSize(textSize);
        mYearPicker.setTextSize(textSize);
        mHourPicker.setTextSize(textSize);
        mMinutePicker.setTextSize(textSize);
    }

    /**
     * 设置被选中时候的文本颜色
     *
     * @param selectedItemTextColor 文本颜色
     */
    public void setSelectedItemTextColor(@ColorInt int selectedItemTextColor) {
        mDayPicker.setSelectedItemTextColor(selectedItemTextColor);
        mMonthPicker.setSelectedItemTextColor(selectedItemTextColor);
        mYearPicker.setSelectedItemTextColor(selectedItemTextColor);
        mHourPicker.setSelectedItemTextColor(selectedItemTextColor);
        mMinutePicker.setSelectedItemTextColor(selectedItemTextColor);
    }

    /**
     * 设置被选中时候的文本大小
     *
     * @param selectedItemTextSize 文字大小
     */
    public void setSelectedItemTextSize(int selectedItemTextSize) {
        mDayPicker.setSelectedItemTextSize(selectedItemTextSize);
        mMonthPicker.setSelectedItemTextSize(selectedItemTextSize);
        mYearPicker.setSelectedItemTextSize(selectedItemTextSize);
        mHourPicker.setSelectedItemTextSize(selectedItemTextSize);
        mMinutePicker.setSelectedItemTextSize(selectedItemTextSize);
    }


    /**
     * 设置显示数据量的个数的一半。
     * 为保证总显示个数为奇数,这里将总数拆分，itemCount = mHalfVisibleItemCount * 2 + 1
     *
     * @param halfVisibleItemCount 总数量的一半
     */
    public void setHalfVisibleItemCount(int halfVisibleItemCount) {
        mDayPicker.setHalfVisibleItemCount(halfVisibleItemCount);
        mMonthPicker.setHalfVisibleItemCount(halfVisibleItemCount);
        mYearPicker.setHalfVisibleItemCount(halfVisibleItemCount);
        mHourPicker.setHalfVisibleItemCount(halfVisibleItemCount);
        mMinutePicker.setHalfVisibleItemCount(halfVisibleItemCount);
    }

    /**
     * Sets item width space.
     *
     * @param itemWidthSpace the item width space
     */
    public void setItemWidthSpace(int itemWidthSpace) {
        mDayPicker.setItemWidthSpace(itemWidthSpace);
        mMonthPicker.setItemWidthSpace(itemWidthSpace);
        mYearPicker.setItemWidthSpace(itemWidthSpace);
        mHourPicker.setItemWidthSpace(itemWidthSpace);
        mMinutePicker.setItemWidthSpace(itemWidthSpace);
    }

    /**
     * 设置两个Item之间的间隔
     *
     * @param itemHeightSpace 间隔值
     */
    public void setItemHeightSpace(int itemHeightSpace) {
        mDayPicker.setItemHeightSpace(itemHeightSpace);
        mMonthPicker.setItemHeightSpace(itemHeightSpace);
        mYearPicker.setItemHeightSpace(itemHeightSpace);
        mHourPicker.setItemHeightSpace(itemHeightSpace);
        mMinutePicker.setItemHeightSpace(itemHeightSpace);
    }


    /**
     * Set zoom in center item.
     *
     * @param zoomInSelectedItem the zoom in center item
     */
    public void setZoomInSelectedItem(boolean zoomInSelectedItem) {
        mDayPicker.setZoomInSelectedItem(zoomInSelectedItem);
        mMonthPicker.setZoomInSelectedItem(zoomInSelectedItem);
        mYearPicker.setZoomInSelectedItem(zoomInSelectedItem);
        mHourPicker.setZoomInSelectedItem(zoomInSelectedItem);
        mMinutePicker.setZoomInSelectedItem(zoomInSelectedItem);
    }

    /**
     * 设置是否循环滚动。
     * set wheel cyclic
     * @param cyclic 上下边界是否相邻
     */
    public void setCyclic(boolean cyclic) {
        mDayPicker.setCyclic(cyclic);
        mMonthPicker.setCyclic(cyclic);
        mYearPicker.setCyclic(cyclic);
        mHourPicker.setCyclic(cyclic);
        mMinutePicker.setCyclic(cyclic);
    }

    /**
     * 设置文字渐变，离中心越远越淡。
     * Set the text color gradient
     * @param textGradual 是否渐变
     */
    public void setTextGradual(boolean textGradual) {
        mDayPicker.setTextGradual(textGradual);
        mMonthPicker.setTextGradual(textGradual);
        mYearPicker.setTextGradual(textGradual);
        mHourPicker.setTextGradual(textGradual);
        mMinutePicker.setTextGradual(textGradual);
    }


    /**
     * 设置中心Item是否有幕布遮盖
     * set the center item curtain cover
     * @param showCurtain 是否有幕布
     */
    public void setShowCurtain(boolean showCurtain) {
        mDayPicker.setShowCurtain(showCurtain);
        mMonthPicker.setShowCurtain(showCurtain);
        mYearPicker.setShowCurtain(showCurtain);
        mHourPicker.setShowCurtain(showCurtain);
        mMinutePicker.setShowCurtain(showCurtain);
    }

    /**
     * 设置幕布颜色
     * set curtain color
     * @param curtainColor 幕布颜色
     */
    public void setCurtainColor(@ColorInt int curtainColor) {
        mDayPicker.setCurtainColor(curtainColor);
        mMonthPicker.setCurtainColor(curtainColor);
        mYearPicker.setCurtainColor(curtainColor);
        mHourPicker.setCurtainColor(curtainColor);
        mMinutePicker.setCurtainColor(curtainColor);
    }

    /**
     * 设置幕布是否显示边框
     * set curtain border
     * @param showCurtainBorder 是否有幕布边框
     */
    public void setShowCurtainBorder(boolean showCurtainBorder) {
        mDayPicker.setShowCurtainBorder(showCurtainBorder);
        mMonthPicker.setShowCurtainBorder(showCurtainBorder);
        mYearPicker.setShowCurtainBorder(showCurtainBorder);
        mHourPicker.setShowCurtainBorder(showCurtainBorder);
        mMinutePicker.setShowCurtainBorder(showCurtainBorder);
    }

    /**
     * 幕布边框的颜色
     * curtain border color
     * @param curtainBorderColor 幕布边框颜色
     */
    public void setCurtainBorderColor(@ColorInt int curtainBorderColor) {
        mDayPicker.setCurtainBorderColor(curtainBorderColor);
        mMonthPicker.setCurtainBorderColor(curtainBorderColor);
        mYearPicker.setCurtainBorderColor(curtainBorderColor);
        mHourPicker.setCurtainBorderColor(curtainBorderColor);
        mMinutePicker.setCurtainBorderColor(curtainBorderColor);
    }

    /**
     * 设置选择器的指示器文本
     * set indicator text
     * @param hourText  小时指示器文本
     * @param minuteText 分钟指示器文本

     */
    public void setIndicatorText(String yearText, String monthText, String dayText,String hourText, String minuteText) {
        mYearPicker.setIndicatorText(yearText);
        mMonthPicker.setIndicatorText(monthText);
        mDayPicker.setIndicatorText(dayText);
        mHourPicker.setIndicatorText(hourText);
        mMinutePicker.setIndicatorText(minuteText);
    }

    /**
     * 设置指示器文字的颜色
     * set indicator text color
     * @param textColor 文本颜色
     */
    public void setIndicatorTextColor(@ColorInt int textColor) {
        mYearPicker.setIndicatorTextColor(textColor);
        mMonthPicker.setIndicatorTextColor(textColor);
        mDayPicker.setIndicatorTextColor(textColor);
        mHourPicker.setIndicatorTextColor(textColor);
        mMinutePicker.setIndicatorTextColor(textColor);
    }

    /**
     * 设置指示器文字的大小
     *  indicator text size
     * @param textSize 文本大小
     */
    public void setIndicatorTextSize(int textSize) {
        mYearPicker.setTextSize(textSize);
        mMonthPicker.setTextSize(textSize);
        mDayPicker.setTextSize(textSize);
        mHourPicker.setTextSize(textSize);
        mMinutePicker.setTextSize(textSize);
    }
    /**
     * The interface On date selected listener.
     */
    public interface OnDateTimeSelectedListener {
        /**
         * On time selected.
         *
         * @param hour  the hour
         * @param minute the minute
         */
        void onDateTimeSelected(int year, int month, int day, int hour, int minute);
    }


}
