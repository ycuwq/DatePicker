package com.ycuwq.datepicker.time;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.ycuwq.datepicker.R;


/**
 * HourAndMinutePicker
 * Created by ycuwq on 2018/1/22.
 */
public class HourAndMinutePicker extends LinearLayout implements
        HourPicker.OnHourSelectedListener, MinutePicker.OnMinuteSelectedListener {

    private HourPicker mHourPicker;
    private MinutePicker mMinutePicker;
    private OnTimeSelectedListener mOnTimeSelectedListener;

    public HourAndMinutePicker(Context context) {
        this(context, null);
    }

    public HourAndMinutePicker(Context context,  AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HourAndMinutePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.layout_time, this);
        initChild();
        initAttrs(context, attrs);
        mHourPicker.setBackgroundDrawable(getBackground());
        mMinutePicker.setBackgroundDrawable(getBackground());
    }

    @Override
    public void onHourSelected(int hour) {
        onTimeSelected();
    }

    @Override
    public void onMinuteSelected(int hour) {
        onTimeSelected();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HourAndMinutePicker);
        int textSize = a.getDimensionPixelSize(R.styleable.HourAndMinutePicker_itemTextSize,
                getResources().getDimensionPixelSize(R.dimen.WheelItemTextSize));
        int textColor = a.getColor(R.styleable.HourAndMinutePicker_itemTextColor,
                Color.BLACK);
        boolean isTextGradual = a.getBoolean(R.styleable.HourAndMinutePicker_textGradual, true);
        boolean isCyclic = a.getBoolean(R.styleable.HourAndMinutePicker_wheelCyclic, true);
        int halfVisibleItemCount = a.getInteger(R.styleable.HourAndMinutePicker_halfVisibleItemCount, 2);
        int selectedItemTextColor = a.getColor(R.styleable.HourAndMinutePicker_selectedTextColor,
                getResources().getColor(R.color.com_ycuwq_datepicker_selectedTextColor));
        int selectedItemTextSize = a.getDimensionPixelSize(R.styleable.HourAndMinutePicker_selectedTextSize,
                getResources().getDimensionPixelSize(R.dimen.WheelSelectedItemTextSize));
        int itemWidthSpace = a.getDimensionPixelSize(R.styleable.HourAndMinutePicker_itemWidthSpace,
                getResources().getDimensionPixelOffset(R.dimen.WheelItemWidthSpace));
        int itemHeightSpace = a.getDimensionPixelSize(R.styleable.HourAndMinutePicker_itemHeightSpace,
                getResources().getDimensionPixelOffset(R.dimen.WheelItemHeightSpace));
        boolean isZoomInSelectedItem = a.getBoolean(R.styleable.HourAndMinutePicker_zoomInSelectedItem, true);
        boolean isShowCurtain = a.getBoolean(R.styleable.HourAndMinutePicker_wheelCurtain, true);
        int curtainColor = a.getColor(R.styleable.HourAndMinutePicker_wheelCurtainColor, Color.WHITE);
        boolean isShowCurtainBorder = a.getBoolean(R.styleable.HourAndMinutePicker_wheelCurtainBorder, true);
        int curtainBorderColor = a.getColor(R.styleable.HourAndMinutePicker_wheelCurtainBorderColor,
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
        mHourPicker = findViewById(R.id.hourPicker_layout_time);
        mHourPicker.setOnHourSelectedListener(this);
        mMinutePicker = findViewById(R.id.minutePicker_layout_time);
        mMinutePicker.setOnMinuteSelectedListener(this);

    }

    private void onTimeSelected() {
        if (mOnTimeSelectedListener != null) {
            mOnTimeSelectedListener.onTimeSelected(getHour(), getMinute());
        }
    }


    /**
     * Sets time.
     *
     * @param hour         the year
     * @param minute        the month
     */
    public void setTime(int hour, int minute) {
        setTime(hour, minute, true);
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
    public void setTextColor(int textColor) {
        mHourPicker.setTextColor(textColor);
        mMinutePicker.setTextColor(textColor);
    }

    /**
     * 一般列表的文本大小
     *
     * @param textSize 文字大小
     */
    public void setTextSize(int textSize) {
        mHourPicker.setTextSize(textSize);
        mMinutePicker.setTextSize(textSize);
    }

    /**
     * 设置被选中时候的文本颜色
     *
     * @param selectedItemTextColor 文本颜色
     */
    public void setSelectedItemTextColor(int selectedItemTextColor) {
        mHourPicker.setSelectedItemTextColor(selectedItemTextColor);
        mMinutePicker.setSelectedItemTextColor(selectedItemTextColor);
    }

    /**
     * 设置被选中时候的文本大小
     *
     * @param selectedItemTextSize 文字大小
     */
    public void setSelectedItemTextSize(int selectedItemTextSize) {
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
        mHourPicker.setHalfVisibleItemCount(halfVisibleItemCount);
        mMinutePicker.setHalfVisibleItemCount(halfVisibleItemCount);
    }

    /**
     * Sets item width space.
     *
     * @param itemWidthSpace the item width space
     */
    public void setItemWidthSpace(int itemWidthSpace) {
        mHourPicker.setItemWidthSpace(itemWidthSpace);
        mMinutePicker.setItemWidthSpace(itemWidthSpace);
    }

    /**
     * 设置两个Item之间的间隔
     *
     * @param itemHeightSpace 间隔值
     */
    public void setItemHeightSpace(int itemHeightSpace) {
        mHourPicker.setItemHeightSpace(itemHeightSpace);
        mMinutePicker.setItemHeightSpace(itemHeightSpace);
    }


    /**
     * Set zoom in center item.
     *
     * @param zoomInSelectedItem the zoom in center item
     */
    public void setZoomInSelectedItem(boolean zoomInSelectedItem) {
        mHourPicker.setZoomInSelectedItem(zoomInSelectedItem);
        mMinutePicker.setZoomInSelectedItem(zoomInSelectedItem);
    }

    /**
     * 设置是否循环滚动。
     * set wheel cyclic
     * @param cyclic 上下边界是否相邻
     */
    public void setCyclic(boolean cyclic) {
        mHourPicker.setCyclic(cyclic);
        mMinutePicker.setCyclic(cyclic);
    }

    /**
     * 设置文字渐变，离中心越远越淡。
     * Set the text color gradient
     * @param textGradual 是否渐变
     */
    public void setTextGradual(boolean textGradual) {
        mHourPicker.setTextGradual(textGradual);
        mMinutePicker.setTextGradual(textGradual);
    }


    /**
     * 设置中心Item是否有幕布遮盖
     * set the center item curtain cover
     * @param showCurtain 是否有幕布
     */
    public void setShowCurtain(boolean showCurtain) {
        mHourPicker.setShowCurtain(showCurtain);
        mMinutePicker.setShowCurtain(showCurtain);
    }

    /**
     * 设置幕布颜色
     * set curtain color
     * @param curtainColor 幕布颜色
     */
    public void setCurtainColor(int curtainColor) {
        mHourPicker.setCurtainColor(curtainColor);
        mMinutePicker.setCurtainColor(curtainColor);
    }

    /**
     * 设置幕布是否显示边框
     * set curtain border
     * @param showCurtainBorder 是否有幕布边框
     */
    public void setShowCurtainBorder(boolean showCurtainBorder) {
        mHourPicker.setShowCurtainBorder(showCurtainBorder);
        mMinutePicker.setShowCurtainBorder(showCurtainBorder);
    }

    /**
     * 幕布边框的颜色
     * curtain border color
     * @param curtainBorderColor 幕布边框颜色
     */
    public void setCurtainBorderColor(int curtainBorderColor) {
        mHourPicker.setCurtainBorderColor(curtainBorderColor);
        mMinutePicker.setCurtainBorderColor(curtainBorderColor);
    }

    /**
     * 设置选择器的指示器文本
     * set indicator text
     * @param hourText  小时指示器文本
     * @param minuteText 分钟指示器文本

     */
    public void setIndicatorText(String hourText, String minuteText) {
        mHourPicker.setIndicatorText(hourText);
        mMinutePicker.setIndicatorText(minuteText);
    }

    /**
     * 设置指示器文字的颜色
     * set indicator text color
     * @param textColor 文本颜色
     */
    public void setIndicatorTextColor(int textColor) {
        mHourPicker.setIndicatorTextColor(textColor);
        mMinutePicker.setIndicatorTextColor(textColor);
    }

    /**
     * 设置指示器文字的大小
     *  indicator text size
     * @param textSize 文本大小
     */
    public void setIndicatorTextSize(int textSize) {
        mHourPicker.setTextSize(textSize);
        mMinutePicker.setTextSize(textSize);
    }

    /**
     * Sets on date selected listener.
     *
     * @param onTimeSelectedListener the on time selected listener
     */
    public void setOnTimeSelectedListener(OnTimeSelectedListener onTimeSelectedListener) {
        mOnTimeSelectedListener = onTimeSelectedListener;
    }

    /**
     * The interface On date selected listener.
     */
    public interface OnTimeSelectedListener {
        /**
         * On time selected.
         *
         * @param hour  the hour
         * @param minute the minute
         */
        void onTimeSelected(int hour, int minute);
    }
}
