package com.ycuwq.datepicker.date;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.ycuwq.datepicker.WheelPicker;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 月份选择器
 * Created by ycuwq on 17-12-28.
 */
public class MonthPicker extends WheelPicker<Integer> {

    private int mSelectedMonth;

    private OnMonthSelectedListener mOnMonthSelectedListener;

    public MonthPicker(Context context) {
        this(context, null);
    }

    public MonthPicker(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MonthPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
	    setItemMaximumWidthText("00");
	    NumberFormat numberFormat = NumberFormat.getNumberInstance();
	    numberFormat.setMinimumIntegerDigits(2);
	    setDataFormat(numberFormat);

		Calendar.getInstance().clear();
        mSelectedMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        updateMonth();
        setSelectedMonth(mSelectedMonth, false);
        setOnWheelChangeListener(new OnWheelChangeListener<Integer>() {
	        @Override
	        public void onWheelSelected(Integer item, int position) {
	        	mSelectedMonth = item;
		        if (mOnMonthSelectedListener != null) {
		        	mOnMonthSelectedListener.onMonthSelected(item);
		        }
	        }
        });
    }

    public void updateMonth() {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            list.add(i);
        }
        setDataList(list);
    }

    public int getSelectedMonth() {
        return mSelectedMonth;
    }

    public void setSelectedMonth(int selectedMonth) {
        setSelectedMonth(selectedMonth, true);
    }

    public void setSelectedMonth(int selectedMonth, boolean smoothScroll) {
        if (selectedMonth < 1 || selectedMonth > 12) {
            throw new IllegalArgumentException("selectedMonth:"
                    + selectedMonth + "more than range" + "1~12");
        }
        setCurrentPosition(selectedMonth - 1, smoothScroll);
    }

	public void setOnMonthSelectedListener(OnMonthSelectedListener onMonthSelectedListener) {
		mOnMonthSelectedListener = onMonthSelectedListener;
	}

	public interface OnMonthSelectedListener {
    	void onMonthSelected(int month);
    }

}
