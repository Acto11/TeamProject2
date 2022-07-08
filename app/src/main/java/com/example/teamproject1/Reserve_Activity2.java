package com.example.teamproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;


import java.util.Calendar;

public class Reserve_Activity2 extends AppCompatActivity {
    private TextView text_date, text_time;
    private  DatePickerDialog datePickerDialog;
    private  TimePickerDialog timePickerDialog;

    Calendar calendar = Calendar.getInstance();
    int Year = calendar.get(Calendar.YEAR);
    int Month = calendar.get(Calendar.MONTH);
    int Day = calendar.get(Calendar.DAY_OF_MONTH);
    int Hour = calendar.get(Calendar.HOUR);
    int Minute = calendar.get(Calendar.MINUTE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve2);

        text_date = (TextView) findViewById(R.id.text_date);
        text_time = (TextView) findViewById(R.id.text_time);

        Button btn_date_start = (Button) findViewById(R.id.btn_date_start);
        Button btn_date_end = (Button) findViewById(R.id.btn_date_end);
        Button btn_time_start = (Button) findViewById(R.id.btn_time_start);
        Button btn_time_end = (Button) findViewById(R.id.btn_time_end);



        //방문시작 날짜선택
        btn_date_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                datePickerDialog  = new DatePickerDialog(Reserve_Activity2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month+1;
                        String date = year + "/" + month +"/" + day;
                        text_date.setText(date);
                        datePicker.setMinDate(calendar.getTime().getTime());
                    }
                },Year,Month,Day);
                datePickerDialog.show();
            }
        });
        //방문종료 날짜선택
        btn_date_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog  = new DatePickerDialog(Reserve_Activity2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month+1;
                        String date = "~" + year + "/" + month +"/" + day;
                        text_date.append(date);

                    }
                },Year,Month,Day);

                datePickerDialog.show();
            }
        });

        btn_time_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog = new TimePickerDialog(Reserve_Activity2.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {

                        String time = hour + ":" + minute;
                        text_time.setText(time);
                    }
                },Hour,Minute,false);
                timePickerDialog.show();
            }
        });

        btn_time_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog = new TimePickerDialog(Reserve_Activity2.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {

                        String time = "~ " + hour + ":" + minute;
                        text_time.append(time);
                    }
                },Hour,Minute,false);
                timePickerDialog.show();
            }
        });
    }
}