package com.ycuwq.datepicker.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ycuwq.datepicker.WheelPicker;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		WheelPicker<String> wheelPicker = findViewById(R.id.wheel_picker);
		List<String> list = new ArrayList<>();
		for (int i = 0; i < 12000; i ++) {
			list.add("第" + i);
		}
		wheelPicker.setDataList(list);
	}
}
