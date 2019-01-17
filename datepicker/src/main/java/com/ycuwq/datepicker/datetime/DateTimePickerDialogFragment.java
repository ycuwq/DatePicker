package com.ycuwq.datepicker.datetime;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
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
 * Created by flydream on 2019/1/17.
 */
public class DateTimePickerDialogFragment extends DialogFragment {

	protected DateTimePicker mDateTimePicker;
	private int mSelectedYear = -1;
	private int mSelectedMonth = -1;
	private int mSelectedDay = -1;
	private int mSelectedHour = -1;
	private int mSelectedMinute = -1;

	private OnDateTimeChooseListener mOnDateTimeChooseListener;
	private boolean mIsShowAnimation = true;
	protected Button mCancelButton, mDecideButton;

	public void setOnDateTimeChooseListener(OnDateTimeChooseListener onDateTimeChooseListener) {
		mOnDateTimeChooseListener = onDateTimeChooseListener;
	}

	public void showAnimation(boolean show) {
		mIsShowAnimation = show;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dialog_datetime, container);

		mDateTimePicker = view.findViewById(R.id.dateTimePicker_dialog);
		mCancelButton = view.findViewById(R.id.btn_dialog_date_cancel);
		mDecideButton = view.findViewById(R.id.btn_dialog_date_decide);
		mCancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

		mDecideButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mOnDateTimeChooseListener != null) {
					mOnDateTimeChooseListener.onDateTimeChoose(mDateTimePicker.getYear(),
							mDateTimePicker.getMonth(), mDateTimePicker.getDay(),mDateTimePicker.getHour(),mDateTimePicker.getMinute());
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

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = new Dialog(getActivity(), R.style.DatePickerBottomDialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定

		dialog.setContentView(R.layout.dialog_datetime);
		dialog.setCanceledOnTouchOutside(true); // 外部点击取消

		Window window = dialog.getWindow();
		if (window != null) {
			if (mIsShowAnimation) {
				window.getAttributes().windowAnimations = R.style.DatePickerDialogAnim;
			}
			WindowManager.LayoutParams lp = window.getAttributes();
			lp.gravity = Gravity.BOTTOM; // 紧贴底部
			lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
			lp.dimAmount = 0.35f;
			window.setAttributes(lp);
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
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
		if (mDateTimePicker != null) {
			mDateTimePicker.setDate(mSelectedYear, mSelectedMonth, mSelectedDay, false);
		}
	}

	public interface OnDateTimeChooseListener {
		void onDateTimeChoose(int year, int month, int day,int hour, int minute);
	}


}
