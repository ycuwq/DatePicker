package com.ycuwq.datepicker.date;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

import com.ycuwq.datepicker.WheelPicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 日期选择
 * Created by yangchen on 17-12-28.
 */
public class DayPicker extends WheelPicker<Integer>{

    private int mEndDay;

    private int mSelectedDay;

    private int mYear, mMonth;

    public DayPicker(Context context) {
        this(context, null);
    }

    public DayPicker(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DayPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mEndDay = Calendar.getInstance().getActualMaximum(Calendar.DATE);
        updateDay();
        mSelectedDay = Calendar.getInstance().get(Calendar.DATE);
        setSelectedDay(mSelectedDay, false);
    }


    public void setMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        mEndDay = calendar.getActualMaximum(Calendar.DATE);
        updateDay();
    }

    public int getSelectedDay() {
        return mSelectedDay;
    }

    public void setSelectedDay(int selectedMonth) {
        setSelectedDay(selectedMonth, true);
    }

    public void setSelectedDay(int selectedDay, boolean smoothScroll) {
        if (selectedDay < 1 || selectedDay > mEndDay) {
            throw new IllegalArgumentException("selectedMonth:"
                    + selectedDay + "more than range" + "1~12");
        }
        setCurrentPosition(selectedDay - 1, smoothScroll);
    }

    private void updateDay() {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= mEndDay; i++) {
            list.add(i);
        }
        setDataList(list);
    }
}
