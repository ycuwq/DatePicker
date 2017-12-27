package com.ycuwq.datepicker.date;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.ycuwq.datepicker.R;
import com.ycuwq.datepicker.WheelPicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 年份选择器
 * Created by yangchen on 17-12-27.
 */
@SuppressWarnings("unused")
public class YearPicker extends WheelPicker<Integer> {

    private int mStartYear, mEndYear;
    private int mSelectedYear;
    public YearPicker(Context context) {
        this(context, null);
    }

    public YearPicker(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YearPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mSelectedYear = Calendar.getInstance().get(Calendar.YEAR);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.YearPicker);
        mStartYear = a.getInteger(R.styleable.YearPicker_startYear, 1900);
        mEndYear = a.getInteger(R.styleable.YearPicker_endYear, 2100);
        a.recycle();
        setItemMaximumWidthText("0000");
        updateYear();
        setSelectedYear(mSelectedYear, false);
    }

    private void updateYear() {
        List<Integer> list = new ArrayList<>();
        for (int i = mStartYear; i <= mEndYear; i++) {
            list.add(i);
        }
        setDataList(list);
    }

    public void setYear(int startYear, int endYear) {
        mStartYear = startYear;
        mEndYear = endYear;
        updateYear();
    }

    public void setSelectedYear(int selectedYear) {
        setSelectedYear(selectedYear, true);
    }

    public void setSelectedYear(int selectedYear, boolean smoothScroll) {
        mSelectedYear = selectedYear;
        if (mSelectedYear < mStartYear || mSelectedYear > mEndYear) {
            throw new IllegalArgumentException("selectedYear:"
                    + selectedYear + "more than range" + mStartYear + " ` " + mEndYear);
        }
        setCurrentPosition(mSelectedYear - mStartYear, smoothScroll);
    }

    public interface OnYearSelectedListener {
        void onYearSelected();
    }

}
