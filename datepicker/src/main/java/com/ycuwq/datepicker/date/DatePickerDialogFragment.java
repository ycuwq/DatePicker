package com.ycuwq.datepicker.date;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.ycuwq.datepicker.R;

/**
 * 时间选择器，弹出框
 * Created by 杨晨 on 2018/1/6.
 */
public class DatePickerDialogFragment extends DialogFragment {

	private DatePicker mDatePicker;
	private int mSelectedYear = -1, mSelectedMonth = -1, mSelectedDay = -1;
	private OnDateChooseListener mOnDateChooseListener;
	private boolean mIsShowAnimation = true;
	protected Button mCancelButton, mConfirmButton;
	public static DatePickerDialogFragment getInstance(OnDateChooseListener onDateChooseListener) {
		DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
		datePickerDialogFragment.setOnDateChooseListener(onDateChooseListener);
		return datePickerDialogFragment;
	}

	public void setOnDateChooseListener(OnDateChooseListener onDateChooseListener) {
		mOnDateChooseListener = onDateChooseListener;
	}

	public void showAnimation(boolean show) {
		mIsShowAnimation = show;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dialog_date, container);

		mDatePicker = view.findViewById(R.id.dayPicker_dialog);
		mCancelButton = view.findViewById(R.id.btn_cancel);
		mConfirmButton = view.findViewById(R.id.btn_confirm);
		mCancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		mConfirmButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mOnDateChooseListener != null) {
					mOnDateChooseListener.onDateChoose(mDatePicker.getYear(),
							mDatePicker.getMonth(), mDatePicker.getDay());
				}
				dismiss();
			}
		});

		if (mSelectedYear > 0) {
			setSelectedDate();
		}

		initChild();
		return view;
	}

	protected void initChild() {

	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定

		dialog.setContentView(R.layout.dialog_date);
		dialog.setCanceledOnTouchOutside(true); // 外部点击取消

		Window window = dialog.getWindow();
		if (window != null) {
			if (mIsShowAnimation) {
				window.getAttributes().windowAnimations = R.style.dialogAnim;
			}
			WindowManager.LayoutParams lp = window.getAttributes();
			lp.gravity = Gravity.BOTTOM; // 紧贴底部
			lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
			window.setAttributes(lp);
		}

		return dialog;
	}

	public void setSelectedDate(int year, int month, int day) {
		mSelectedYear = year;
		mSelectedMonth = month;
		mSelectedDay = day;
		setSelectedDate();
	}

	private void setSelectedDate() {
		if (mDatePicker != null) {
			mDatePicker.setDate(mSelectedYear, mSelectedMonth, mSelectedDay, false);
		}
	}

	public interface OnDateChooseListener {
		void onDateChoose(int year, int month, int day);
	}


}
