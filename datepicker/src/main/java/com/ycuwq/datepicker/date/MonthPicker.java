package com.ycuwq.datepicker.date;

import android.content.Context;
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

    private static int MAX_MONTH = 12;
    private static int MIN_MONTH = 1;

    private int mSelectedMonth;

    private OnMonthSelectedListener mOnMonthSelectedListener;

    private int mYear;
    private long mMaxDate, mMinDate;
    private int mMaxYear, mMinYear;
    private int mMinMonth = MIN_MONTH;
    private int mMaxMonth = MAX_MONTH;
    public MonthPicker(Context context) {
        this(context, null);
    }

    public MonthPicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MonthPicker(Context context, AttributeSet attrs, int defStyleAttr) {
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
        for (int i = mMinMonth; i <= mMaxMonth; i++) {
            list.add(i);
        }
        setDataList(list);
    }

    public void setMaxDate(long date) {
        mMaxDate = date;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        mMaxYear = calendar.get(Calendar.YEAR);
    }

    public void setMinDate(long date) {
        mMinDate = date;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        mMinYear = calendar.get(Calendar.YEAR);
    }


    public void setYear(int year) {
        mYear = year;
        mMinMonth = MIN_MONTH;
        mMaxMonth = MAX_MONTH;
        if (mMaxDate != 0 && mMaxYear == year) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mMaxDate);
            mMaxMonth = calendar.get(Calendar.MONTH) + 1;

        }
        if (mMinDate != 0 && mMinYear == year) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mMinDate);
            mMinMonth = calendar.get(Calendar.MONTH) + 1;

        }
        updateMonth();
        if (mSelectedMonth > mMaxMonth) {
            setSelectedMonth(mMaxMonth, false);
        } else if (mSelectedMonth < mMinMonth) {
            setSelectedMonth(mMinMonth, false);
        } else {
            setSelectedMonth(mSelectedMonth, false);
        }
    }

    public int getSelectedMonth() {
        return mSelectedMonth;
    }

    public void setSelectedMonth(int selectedMonth) {
        setSelectedMonth(selectedMonth, true);
    }

    public void setSelectedMonth(int selectedMonth, boolean smoothScroll) {
        setCurrentPosition(selectedMonth - mMinMonth, smoothScroll);
        mSelectedMonth = selectedMonth;
    }

	public void setOnMonthSelectedListener(OnMonthSelectedListener onMonthSelectedListener) {
		mOnMonthSelectedListener = onMonthSelectedListener;
	}

	public interface OnMonthSelectedListener {
    	void onMonthSelected(int month);
    }

}
