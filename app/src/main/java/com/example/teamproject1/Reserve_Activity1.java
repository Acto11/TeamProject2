package com.example.teamproject1;

import androidx.annotation.NonNull;
import android.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.StandardCharsets;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reserve_Activity1 extends AppCompatActivity {
    //파이어베이스 인증
    private FirebaseAuth mFirebaseAuth;
    //실시간 데이터베이스
    private DatabaseReference mDatabaseRef,mLocationRef;
    //UserAccount id 토큰값
    private ArrayList<String> checkToken = new ArrayList<String>();
    //이메일값
    private ArrayList<String> checkId = new ArrayList<String>();
    //이름값
    private ArrayList<String> checkName = new ArrayList<String>();

//    //장소값
//    private ArrayList<String> locationName = new ArrayList<String>();
//    //VisitLocation 토큰값
//    private ArrayList<String> locationToken = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve1);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Visit").child("UserAccount");
        mLocationRef = FirebaseDatabase.getInstance().getReference("Visit").child("visitlocation");
        //다음페이지 버튼
        Button register = (Button) findViewById(R.id.btn_reserve1);
        //방문 담당자 확인
        Button confirm = (Button) findViewById(R.id.btn_Confirm);
        //장소선택
        Button location = (Button) findViewById(R.id.btn_location);
        //스피너 레이아웃
        Intent intent = getIntent();
        //String array[] = (String[]) intent.getSerializableExtra("array_spinner");
        ArrayList<String> data= (ArrayList<String>) intent.getSerializableExtra("locationName");
        ReserveDTO reserveDTO = new ReserveDTO();
        reserveDTO.setIdToken(data.get(0));
        String email = data.get(0);
        if(isEmail(email)){
            data.remove(0);
        }
        String[] array = data.toArray(new String[data.size()]);

        Context mContext = getApplicationContext();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.activity_spinner,null,false);
        Spinner s = (Spinner) layout.findViewById(R.id.spinner_loc);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, array);
        s.setAdapter(adapter);


        //선택 목록이 나타날때 사용할 레이아웃을 지정


        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup);

        EditText edt_Email = (EditText)findViewById(R.id.edt_Email);



        // ReserveDTO reserveDTO = (ReserveDTO) intent.getSerializableExtra("reserveDTO");

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            checkToken.add(snapshot.getKey());
                            Log.d("타입", snapshot.getKey());
                            Log.d("결과값", snapshot.getValue().toString());
                            //checkId.add(snapshot.getValue().toString());
                        }
                        for (int i = 0; i < checkToken.size(); i++) {
                            mDatabaseRef.child(checkToken.get(i)).child("emailId").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String value = snapshot.getValue(String.class);
                                    checkId.add(value);
                                    confirm.setEnabled(true);

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                        //이름값 알아오기
                        for (int i = 0; i < checkToken.size(); i++) {
                            mDatabaseRef.child(checkToken.get(i)).child("user_name").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String value = snapshot.getValue(String.class);
                                    checkName.add(value);

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
        });
        //담당자 확인버튼
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edt_Email.getText().toString();

                for(int i =0;i<checkId.size();i++){
                    if(checkId.get(i).equals(email)){
                        reserveDTO.setIdToken2(checkName.get(i));
                        Toast.makeText(getApplicationContext(), checkName.get(i)+"사원 확인되었습니다.", Toast.LENGTH_SHORT).show();
                        location.setEnabled(true);
                        break;
                    }
                }
                if(!checkId.contains(email)){
                    Toast.makeText(getApplicationContext(), "담당자 이메일을 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        }); //confirm button
        //장소선택
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Reserve_Activity1.this);
                builder.setTitle("AlertDialog Title");
                builder.setMessage("AlertDialog Content");
                builder.setView(layout);
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        register.setEnabled(true);

                    }
                });
                builder.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String value =parent.getItemAtPosition(position).toString();
                        reserveDTO.setLocation(value);
                        location.setText(value);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                builder.show();
            }
        });




        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Reserve_Activity2.class);
                intent.putExtra("reserveDTO",reserveDTO);
                startActivity(intent);
            }
        });

    }

    public static boolean isEmail(String email){
        boolean validation = false;


        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if(m.matches()) {
            validation = true;
        }

        return validation;
    }


}