package com.ycuwq.datepicker.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.ycuwq.datepicker.date.DatePicker;
import com.ycuwq.datepicker.date.DatePickerDialogFragment;

public class MainActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		DatePicker datePicker = findViewById(R.id.datePicker);
		Button button = findViewById(R.id.button);
		button.setOnClickListener(v -> {
			DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
			datePickerDialogFragment.show(getFragmentManager(), "DatePickerDialogFragment");
		});
	}
}
