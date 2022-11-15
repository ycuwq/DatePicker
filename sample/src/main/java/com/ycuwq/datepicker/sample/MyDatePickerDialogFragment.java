package com.ycuwq.datepicker.sample;

import com.ycuwq.datepicker.date.DatePickerDialogFragment;

/**
 * custom Dialog
 * Created by ycuwq on 2018/1/12.
 */
public class MyDatePickerDialogFragment extends DatePickerDialogFragment {

    @Override
    protected void initChild() {
        super.initChild();
        mCancelButton.setTextSize(mCancelButton.getTextSize() + 5);
        mDecideButton.setTextSize(mDecideButton.getTextSize() + 5);
        mDatePicker.setShowCurtain(false);
    }
}
