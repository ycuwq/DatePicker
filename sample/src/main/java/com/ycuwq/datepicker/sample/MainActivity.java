package com.ycuwq.datepicker.sample;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ycuwq.datepicker.date.DatePicker;
import com.ycuwq.datepicker.date.DatePickerDialogFragment;

import java.text.ChoiceFormat;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView dateTv = findViewById(R.id.tv_date);
        DatePicker datePicker = findViewById(R.id.datePicker);
        double[] limits = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        String[] formats = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec"};
        ChoiceFormat format = new ChoiceFormat(limits, formats);
        datePicker.getMonthPicker().setDataFormat(format);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
            datePickerDialogFragment.setOnDateChooseListener(new DatePickerDialogFragment.OnDateChooseListener() {
                @Override
                public void onDateChoose(int year, int month, int day) {
                    Toast.makeText(getApplicationContext(), year + "-" + month + "-" + day, Toast.LENGTH_SHORT).show();
                }
            });
            datePickerDialogFragment.show(getFragmentManager(), "DatePickerDialogFragment");
        });
        datePicker.setOnDateSelectedListener(new DatePicker.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day) {
                dateTv.setText(year + "-" + month + "-" + day);
            }
        });
    }

}
