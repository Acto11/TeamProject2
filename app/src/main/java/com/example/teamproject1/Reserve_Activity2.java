package com.example.teamproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Reserve_Activity2 extends AppCompatActivity {
    private TextView text_date;
    private EditText edit_purpose;
    private  DatePickerDialog datePickerDialog;
    private DatabaseReference mReservationRef;

    //일치하는 장소에 대한 토큰값
    private ArrayList<String> locationToken = new ArrayList<String>();
    //예약건에 대한 Date
    private ArrayList<String> checkDate = new ArrayList<String>();

    private MaterialCalendarView calendarView;

    ArrayList<CalendarDay> dates = new ArrayList<>();

    Calendar calendar = Calendar.getInstance();


    int Year = calendar.get(Calendar.YEAR);
    int Month = calendar.get(Calendar.MONTH);
    int Day = calendar.get(Calendar.DAY_OF_MONTH);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve2);
        //firebase child(reservation) 읽어오기
        mReservationRef = FirebaseDatabase.getInstance().getReference("Visit").child("Reservation");

        text_date = (TextView) findViewById(R.id.text_date);
        edit_purpose = (EditText) findViewById(R.id.edit_purpose);


        Button btn_date_start = (Button) findViewById(R.id.btn_date_start);
        Button btn_date_end = (Button) findViewById(R.id.btn_date_end);
        Button btn_terms = (Button) findViewById(R.id.btn_terms);
        Button btn_reserve2 = (Button) findViewById(R.id.btn_reserve2);



        Intent intent = getIntent();
        ReserveDTO reserveDTO = (ReserveDTO) intent.getSerializableExtra("reserveDTO");
        //방문시작 날짜선택
        String now_location = reserveDTO.getLocation();

        Context mContext = getApplicationContext();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.activity_calendar, null,false);




        btn_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Reserve_Activity2.this);
                builder.setTitle("보얀서약서 동의");
                builder.setMessage(R.string.terms);
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mReservationRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (int i = 0; i < locationToken.size(); i++) {
                                    mReservationRef.child(locationToken.get(i)).child("start_date").addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            String value = snapshot.getValue(String.class);
                                            checkDate.add(value);
                                            String msg = "success";
                                            Log.d("삽입 성공1",value);

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                }
                                for (int i = 0; i < locationToken.size(); i++) {
                                    mReservationRef.child(locationToken.get(i)).child("end_date").addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            String value = snapshot.getValue(String.class);
                                            checkDate.add(value);
                                            String msg = "success";
                                            Log.d("삽입 성공2",value);

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });//positive dialog btn
                mReservationRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            //예약테이블 장소의 모든키를 받아오지.
                            Log.d("토큰값",snapshot.getKey());

                            Log.d("값",snapshot.child("location").getValue(String.class));
                            String value = snapshot.child("location").getValue(String.class);
                            if(now_location.equals(value)){
                                Log.d("일치합니다",value);
                                locationToken.add(snapshot.getKey());

                            }
                            btn_reserve2.setEnabled(true);
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                builder.show();
            }
        });





//날짜선택란
        btn_date_start.setOnClickListener(new View.OnClickListener() {
            String shot_Day = "";
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(Reserve_Activity2.this);
                builder.setView(dialogView);
                calendarView = (MaterialCalendarView) dialogView.findViewById(R.id.calendarView);
                builder.setPositiveButton("선택", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }


                });
                builder.show();
                calendarView.setOnDateChangedListener(new OnDateSelectedListener() {

                    @Override
                    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                        int Year = date.getYear();
                        int Month = date.getMonth() + 1;
                        int Day = date.getDay();

                        Log.i("Year test", Year + "");
                        Log.i("Month test", Month + "");
                        Log.i("Day test", Day + "");

                        String shot_Day = Year + "-" + Month + "-" + Day;

                        Log.i("shot_Day test", shot_Day + "");
                        String dbDate = shot_Day.replaceAll("[^0-9]", "");
                        text_date.append(shot_Day);
                        reserveDTO.setStart_date(dbDate);
                        calendarView.clearSelection();

                    }

                });
                calendarView.addDecorators(new DayDecorator(getApplicationContext()));
                for(int i =0;i<checkDate.size()/2;i++) {

                    if (checkDate.size() == 2) {
                        String startdate = checkDate.get(i);
                        String enddate = checkDate.get(i + 1);
                        int startyear = Integer.parseInt(startdate.substring(0, 4));
                        int startmonth = Integer.parseInt(startdate.substring(4, 5)) - 1;
                        int startday = Integer.parseInt(startdate.substring(5, 7));
                        int endyear = Integer.parseInt(enddate.substring(0, 4));
                        int endmonth = Integer.parseInt(enddate.substring(4, 5)) - 1;
                        int endday = Integer.parseInt(enddate.substring(5, 7)) ;

                    } else {
                        String startdate = checkDate.get(i);
                        String enddate = checkDate.get(i + 2);
                        int startyear = Integer.parseInt(startdate.substring(0, 4));
                        int startmonth = Integer.parseInt(startdate.substring(4, 5)) - 1;
                        int startday = Integer.parseInt(startdate.substring(5, 7));
                        int endyear = Integer.parseInt(enddate.substring(0, 4));
                        int endmonth = Integer.parseInt(enddate.substring(4, 5)) - 1;
                        int endday = Integer.parseInt(enddate.substring(5, 7));
                        int size = endday - startday;
                        if(size>1){
                            for(int a=0;a<size;a++){
                                dates.add(CalendarDay.from(startyear, startmonth, startday+a));
                            }
                        }else {
                            dates.add(CalendarDay.from(startyear, startmonth, startday));
                        }
                        dates.add(CalendarDay.from(endyear, endmonth, endday));
                        calendarView.addDecorator(new CalendarDecorator(dates));
                    }
                }

            }

        });
        //방문종료 날짜선택
        btn_date_end.setOnClickListener(new View.OnClickListener() {
            String shot_Day = "";
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(Reserve_Activity2.this);
                if (dialogView.getParent() != null)
                    ((ViewGroup) dialogView.getParent()).removeView(dialogView);
                builder.setView(dialogView);
                calendarView = (MaterialCalendarView) dialogView.findViewById(R.id.calendarView);
                builder.setPositiveButton("선택", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }


                });
                builder.show();
                calendarView.setOnDateChangedListener(new OnDateSelectedListener() {

                    @Override
                    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                        int Year = date.getYear();
                        int Month = date.getMonth() + 1;
                        int Day = date.getDay();

                        Log.i("Year test", Year + "");
                        Log.i("Month test", Month + "");
                        Log.i("Day test", Day + "");

                        String shot_Day = Year + "-" + Month + "-" + Day;

                        Log.i("shot_Day test", shot_Day + "");
                        String dbDate = shot_Day.replaceAll("[^0-9]", "");
                        text_date.append(shot_Day);
                        reserveDTO.setEnd_date(dbDate);
                        calendarView.clearSelection();

                    }
                });
                calendarView.addDecorators(new DayDecorator(getApplicationContext()));

                for(int i =0;i<checkDate.size()/2;i++) {

                    if (checkDate.size() == 2) {
                        String startdate = checkDate.get(i);
                        String enddate = checkDate.get(i + 1);
                        int startyear = Integer.parseInt(startdate.substring(0, 4));
                        int startmonth = Integer.parseInt(startdate.substring(4, 5)) - 1;
                        int startday = Integer.parseInt(startdate.substring(5, 7));
                        int endyear = Integer.parseInt(enddate.substring(0, 4));
                        int endmonth = Integer.parseInt(enddate.substring(4, 5)) - 1;
                        int endday = Integer.parseInt(enddate.substring(5, 7)) ;

                    } else {
                        String startdate = checkDate.get(i);
                        String enddate = checkDate.get(i + 2);
                        int startyear = Integer.parseInt(startdate.substring(0, 4));
                        int startmonth = Integer.parseInt(startdate.substring(4, 5)) - 1;
                        int startday = Integer.parseInt(startdate.substring(5, 7));
                        int endyear = Integer.parseInt(enddate.substring(0, 4));
                        int endmonth = Integer.parseInt(enddate.substring(4, 5)) - 1;
                        int endday = Integer.parseInt(enddate.substring(5, 7));
                        int size = endday - startday;
                        if(size>1){
                            for(int a=0;a<size;a++){
                                dates.add(CalendarDay.from(startyear, startmonth, startday+a));
                            }
                        }else {
                            dates.add(CalendarDay.from(startyear, startmonth, startday));
                        }
                        dates.add(CalendarDay.from(endyear, endmonth, endday));
                        calendarView.addDecorator(new CalendarDecorator(dates));
                    }
                }
            }
        });

        btn_reserve2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reserveDTO.setPurpose(edit_purpose.getText().toString());
                Intent intent = new Intent(getApplicationContext(),Reserve_Activity3.class);
                intent.putExtra("reserveDTO",reserveDTO);
                startActivity(intent);
            }
        });
    }

}