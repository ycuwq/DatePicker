package com.ycuwq.datepicker.sample;

import android.content.Context;
import android.util.AttributeSet;

import com.ycuwq.datepicker.date.DatePicker;

public class CustomDatePicker extends DatePicker {
    public CustomDatePicker(Context context) {
        super(context);
    }

    public CustomDatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomDatePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_custom_date;
    }
}
