package com.ycuwq.datepicker.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

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
			DatePickerDialogFragment datePickerDialogFragment = DatePickerDialogFragment.getInstance(new DatePickerDialogFragment.OnDateChooseListener() {
				@Override
				public void onDateChoose(int year, int month, int day) {
					Toast.makeText(getApplicationContext(), year + "-" + month + "-" + day, Toast.LENGTH_SHORT).show();
				}
			});
			datePickerDialogFragment.setSelectedDate(2016, 1, 1);
			datePickerDialogFragment.show(getFragmentManager(), "DatePickerDialogFragment");
		});
	}
}
