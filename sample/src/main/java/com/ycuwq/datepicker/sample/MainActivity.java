package com.ycuwq.datepicker.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.ycuwq.datepicker.WheelPicker;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final WheelPicker<String> wheelPicker = findViewById(R.id.wheel_picker);
		List<String> list = new ArrayList<>();
		for (int i = 0; i < 200; i ++) {
			list.add("第" + i);
		}
		wheelPicker.setDataList(list);
		wheelPicker.setOnWheelChangeListener(new WheelPicker.OnWheelChangeListener<String>() {
            @Override
            public void onWheelSelected(String item, int position) {
                Log.d(TAG, "onWheelSelected: " + item);
            }
        });
		wheelPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wheelPicker.setCurtainColor(Color.parseColor("#30ffffff"));
                Log.d(TAG, "onClick: ");
            }
        });
	}
}
