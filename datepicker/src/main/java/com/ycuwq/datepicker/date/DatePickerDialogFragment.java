package com.ycuwq.datepicker.date;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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
	private final String TAG = getClass().getSimpleName();
	private DatePicker mDatePicker;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView: ");
		View view = inflater.inflate(R.layout.dialog_date, container);

		mDatePicker = view.findViewById(R.id.dayPicker_dialog);
		Button cancelButton = view.findViewById(R.id.btn_cancel);
		Button confirmButton = view.findViewById(R.id.btn_confirm);
		// TODO: 2018/1/6 传输信息
		cancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		confirmButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mDatePicker.getDate();
				dismiss();
			}
		});
		return view;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Log.d(TAG, "onCreateDialog: ");
		Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
		dialog.setContentView(R.layout.dialog_date);
		dialog.setCanceledOnTouchOutside(true); // 外部点击取消

		Window window = dialog.getWindow();
		if (window != null) {
			WindowManager.LayoutParams lp = window.getAttributes();
			lp.gravity = Gravity.BOTTOM; // 紧贴底部
			lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
			window.setAttributes(lp);
		}

		return dialog;
	}
}
