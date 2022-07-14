package com.example.teamproject1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

//import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class LocationActivity extends AppCompatActivity {
    //firebase 인증?
    //private FirebaseAuth LocFirebaseAuth;
    //RealtimeDatabase 연동 & key(테이블?) 정의
    private final FirebaseDatabase conDatabaseRef = FirebaseDatabase.getInstance();
    private final DatabaseReference mRootRef = conDatabaseRef.getReference("Visit");
    private final DatabaseReference locationRef = mRootRef.child("visitlocation");
//    private final String BASEURL = "https://asdf-764fe-default-rtdb.firebaseio.com/Visit/";

//    RecyclerView recyclerView;
//    ArrayList<VisitLocation> visitlocationList;
//    LinearLayoutManager manager;
//    VisitLocationAdapter visitlocationAdapter;
//    VisitLocation visitlocation;
//    int position;

    private Map<String, Object> resultUpdate = new HashMap<>();

    Button btnInsert, btnView;
    EditText editLoc1, editLoc2, editNote;
    Spinner spinner1;
    Switch swtSwitch;
    String spinnerValue = "";
    String swtValue = "On";
    String sIntLoc1 = null, sIntLoc2 = null, sIntGrade = null, sIntUsd = null, sIntNote = null; //Intent 전달 받은 값 저장변수
    String sOldVaule = null; //수정 전의 값 저장변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
//        setTitle("방문장소 등록");

        //등록화면에 선택값을 저장하기 위에 변수에 저장함
        btnInsert = findViewById(R.id.btnInsert);
        btnView = findViewById(R.id.btnView);
//        btnCancel = findViewById(R.id.btnCancel);

        editLoc1 = findViewById(R.id.editLoc1);
        editLoc2 = findViewById(R.id.editLoc2);
        editNote = findViewById(R.id.editNote);
        Spinner spinner1 = findViewById(R.id.spinner1);
        swtSwitch = findViewById(R.id.swtSwitch);

        Log.d("Loc1>>>>>", "" + getIntent().hasExtra("location1"));
        Log.d("Grade>>>>>", "" + getIntent().hasExtra("grade"));
        //Intent 전달 값을 받는 기능
        if (getIntent().hasExtra("location1") && getIntent().hasExtra("location2")
                && getIntent().hasExtra("grade") && getIntent().hasExtra("used")) {

            //from RecyclerView.Adapter의 Intent 데이터 받기
            Intent intentRcv = new Intent(getIntent());
            sIntLoc1 = intentRcv.getStringExtra("location1");
            sIntLoc2 = intentRcv.getStringExtra("location2");
            sIntGrade  = intentRcv.getStringExtra("grade");
            sIntUsd  = intentRcv.getStringExtra("used");
            sIntNote = intentRcv.getStringExtra("note");

            //Intent가 null아니고 수신값이 null아니면 Intent로 받은 데이터를 EditText 입력하기
            editLoc1.setText(sIntLoc1);
            editLoc2.setText(sIntLoc2);

            Log.d("Grade>>>>>", "" + sIntGrade);
            if (sIntGrade == "1등급") {
                spinner1.setSelection(0);
                spinner1.getSelectedItem().toString();
                spinnerValue= "1등급";
            } else if (sIntGrade == "2등급") {
                spinner1.setSelection(1);
                spinnerValue= "2등급";
            } else if (sIntGrade == "3등급") {
                spinner1.setSelection(2);
                spinnerValue= "3등급";
            }
            // swtSwitch.setText(sIntUsd);
            Log.d("used>>>>>", "" + sIntUsd);
            if (sIntUsd == "On") {
                swtSwitch.setChecked(true);
                swtValue = "On";
            } else if (sIntUsd == "Off") {
                swtSwitch.setChecked(false);
                swtValue = "Off";
            }

            editNote.setText(sIntNote);

            btnInsert.setText("수정");

        } //if (getIntent().hasExtra(location1) &&


        //grade_Array과 UI.XML레이아웃을 사용 ArrayAdpter만듬
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.grade_array, android.R.layout.simple_spinner_item);

        //선택 목록이 나타날때 사용할 레이아웃을 지정
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //스피너에 어댑터 적용
        spinner1.setAdapter(adapter);
        spinner1.setSelection(2); //position값을 초기값 지정(초기화)
        //스피너 Item선택값 가져오는 방법(ID값 아님)

        //spinner1.getSelectedItem().toString();
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerValue = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinnerValue = "3등급";
            }
        });


        //스위치 변경시 운영상태 값 가져옴
        swtSwitch.setOnCheckedChangeListener(new swtSwitchListener());


        //firebase인증 검증 후 저장기능으로 미로그인 사용자는 check하여 회원등록 화면으로 이동처리
        //-->미구현 작업예정

        //등록버튼 누르면 화면의 입력 값을 저장
        btnInsert.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               //등록 주요 항목이 null값이면 입력하라는 메시지를 처리
                String getedtLoc1 = editLoc1.getText().toString();
                String getedtLoc2 = editLoc2.getText().toString();
                getedtLoc1 = getedtLoc1.trim();
                getedtLoc2 = getedtLoc2.trim();

                if(getedtLoc1.getBytes().length <=0){
                    Toast.makeText(getApplicationContext(),"지점 및 공장을 입력하세요.!",Toast.LENGTH_SHORT).show();
                }

                if(editLoc2.getText().toString().equals("") || editLoc2.getText().toString() == null) {
                    Toast.makeText(getApplicationContext(),"방문장소를 입력하세요.!",Toast.LENGTH_SHORT).show();
                }


            if (btnInsert.getText().toString().equals("등록")) {  //등록작업
                // String 텍스트값을 문자열로 바꾸어 변수에 넣음
                // locationRef.setValue(editLoc1.getText().toString(),);
                addvisitlocation(editLoc1.getText().toString(),
                        editLoc2.getText().toString(),
//                      spinner1.getSelectedItem().toString(), //값을 가져오는 부분
                        spinnerValue,
                        swtValue, //checkBox로 변경예정 현재사용자 선택값이 안들어감
                        editNote.getText().toString());
            }else { //수정작업 전달받은 값 HashMap저장
              // 등록화면에서 수정한 값을 변수방에 이동 후 HashMap에 넣기
               sIntLoc1 = editLoc1.getText().toString();
               sIntLoc2 = editLoc2.getText().toString();
               sIntGrade = spinnerValue;
               sIntUsd = swtValue;
               sIntNote = editNote.getText().toString();

               Map<String, Object> resultUpdate = new HashMap<>();
                resultUpdate.put("location1", sIntLoc1); // 키, 값
                resultUpdate.put("location2", sIntLoc2);
                resultUpdate.put("grade", sIntGrade);
                resultUpdate.put("used", sIntUsd);
                resultUpdate.put("note", sIntNote);

                addvisitlocation(editLoc1.getText().toString(),
                        editLoc2.getText().toString(),
//                      spinner1.getSelectedItem().toString(), //값을 가져오는 부분
                        spinnerValue,
                        swtValue, //checkBox로 변경예정 현재사용자 선택값이 안들어감
                        editNote.getText().toString());

            }
                 //화면의 입력난 초기화
                 clearAlledit();
            } //  public void onClick(View v)
        }); //btnInsert.setOnClickListener

        //조회버튼
        btnView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity((new Intent(LocationActivity.this, Locationlist.class )));
                finish();
            }
        });//btnView.setOnClickListener

    } //protected void onCreate


        //저장 값을 firebase Realtime database로 넘기는 함수
        private void addvisitlocation(String location1, String location2, String grade, String used, String note) {
           //VisitLocation.java에서 선언한 함수
           VisitLocation location = new VisitLocation(location1,location2,grade,used,note);
           Log.d("addvisitlocation>>>>>", "" + location1+location2+grade+used+note);
           Log.d("등록과 수정구분 >>>>>", "" + btnInsert.getText().toString());

           if (btnInsert.getText().toString().equals("등록")) {
               Log.d("logcheck1>>>", ""+ location1 + "확인체크1");
               //push()는 값을 넣을 때 상위 키값을 랜덤으로 설정해주는 함수이며 이 경우에는 미사용(중복을 허용하고 시간 발생기준으로 저장하고자 할때 사용
               //child는 해당 키 위치로 이동하는 함수입니다.
               locationRef.push().setValue(location)
                       .addOnSuccessListener(new OnSuccessListener<Void>() {
                           @Override
                           public void onSuccess(Void unused) {
                               //저장 successful
                               Toast.makeText(getApplicationContext(), "등록이 잘 되었습니다.!", Toast.LENGTH_SHORT).show();
                               finish();
                           }
                       })
                       .addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               //저장 failed
                               Toast.makeText(getApplicationContext(), "등록이 실패하였습니다.!", Toast.LENGTH_SHORT).show();
                           }
                       });
           }else{ //수정작업 모드
              // Query query locationRef.orderByChild("location1").equalTo();
               Log.d("logcheck2>>>", ""+ location1 + "확인체크2");
              // Query query = mRootRef.child("visitlocation").orderByChild("location1").equalTo(location1,location2);
               Query query = mRootRef.child("visitlocation").orderByChild("location1").equalTo(sIntLoc1, "location1");

               query.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                          // dataSnapshot.child("visitlocation").getRef().updateChildren(location); //데이터 수정
                         //  dataSnapshot.child(key).child("visitlocation").setValue(location); //데이터 수정
                        //   locationRef.child(getRef(position).getKey()).updateChildren(resultUpdate);
                           String sKey = dataSnapshot.getKey();
                           Log.d("sKEY값 확인>>>", ""+ sKey + "  체크3");
                           mRootRef.child(sKey).child("visitlocation").updateChildren(resultUpdate);
                           btnInsert.setText("등록"); //등록버튼으로 전환
                       }
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {
                       Toast.makeText(getApplicationContext(),"error: " + error.getMessage(),
                               Toast.LENGTH_SHORT).show();
                   }
               }); //query.addListenerForSingleValueEvent

           } // else{ //수정작업 모드
        } // private void addvisitlocation(

    public class swtSwitchListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked) { //초기상태일 경우 //True이면 체크된 경우
                swtValue = "On";
            }else { // Flase이면 할 일
                swtValue = "Off";
            }
        }  //public void onCheckedChanged(CompoundButton
    } //private class swtSwitchListener

    //화면의 입력칸 초기화 함수
    private void clearAlledit() {
        editLoc1.setText("");
        editLoc2.setText("");
//        spinner1.setSelection(2); //초기화값 "3등급
        swtSwitch.setChecked(true);
//        editNote.getText().clear();
        editNote.setText("");
    }

}