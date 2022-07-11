package com.example.teamproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LocationActivity extends AppCompatActivity {
    //firebase 인증?
    //private FirebaseAuth LocFirebaseAuth;
    //RealtimeDatabase 연동 & key(테이블?) 정의
    private final FirebaseDatabase conDatabaseRef = FirebaseDatabase.getInstance();
    private final DatabaseReference mRootRef = conDatabaseRef.getReference("Visit");
    private final DatabaseReference locationRef = mRootRef.child("visitlocation");
//    private final String BASEURL = "https://asdf-764fe-default-rtdb.firebaseio.com/Visit/";

//    private VisitLocationApi visitLocationApi;
    RecyclerView recyclerView;
    LinearLayoutManager manager;
//    VisitLocationAdapter visitlocationAdapter;  //임시로 주석처리하였음 나중에 해제 예정
    VisitLocation visitlocation;
    int position;

    Button btnInsert, btnUpdate, btnCancel;
    EditText editLoc1, editLoc2, editNote;
    Spinner spinner1;
    Switch swtSwitch;
    String spinnerValue = "";
    String swtValue = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
//        setTitle("방문장소 등록");

        //등록화면에 선택값을 저장하기 위에 변수에 저장함
        btnInsert = findViewById(R.id.btnInsert);
//        btnUpdate = findViewById(R.id.btnUpdate);
//        btnCancel = findViewById(R.id.btnCancel);

        editLoc1 = findViewById(R.id.editLoc1);
        editLoc2 = findViewById(R.id.editLoc2);
        editNote = findViewById(R.id.editNote);
        Spinner spinner1 = findViewById(R.id.spinner1);
        swtSwitch = findViewById(R.id.swtSwitch);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //임시주석처리 나중에 해제예정
//        FirebaseRecyclerOptions<VisitLocation> options =
//                new FirebaseRecyclerOptions.Builder<VisitLocation>()
//                        .setQuery(locationRef, VisitLocation.class).build();
        //임시주석처리 나중에 해제예정
//        visitlocationAdapter = new VisitLocationAdapter(options);
//        recyclerView.setAdapter(visitlocationAdapter);

        //grade_Array과 UI.XML레이아웃을 사용 ArrayAdpter만듬
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.grade_array, android.R.layout.simple_spinner_item);

        //선택 목록이 나타날때 사용할 레이아웃을 지정
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //스피너에 어댑터 적용
        spinner1.setAdapter(adapter);
        spinner1.setSelection(2); //position값을 초기값 지정(초기화)
        //스피너 Item선택값 가져오는 방법(ID값 아님)

        spinnerValue = "";
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


        //스위치 변경시 운영상태 값 가져옴 --> 현재 사용자 선택값이 안들어감===>checkBox로 변경예정
        swtSwitch.setOnCheckedChangeListener(new swtSwitchListener());


        //firebase인증 검증 후 저장기능으로 미로그인 사용자는 check하여 회원등록 화면으로 이동처리
        //-->미구현 나중에 작업예정

        //등록버튼 누르면 값을 저장
        btnInsert.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               //등록 주요 항목이 null값이면 입력하라는 메시지를 처리

               // String 텍스트값을 문자열로 바꾸어 변수에 넣음
               // locationRef.setValue(editLoc1.getText().toString(),);
                addvisitlocation(editLoc1.getText().toString(),
                        editLoc2.getText().toString(),
//                      spinner1.getSelectedItem().toString(), //값을 가져오는 부분
                        spinnerValue,
                        swtValue, //checkBox로 변경예정 현재사용자 선택값이 안들어감
                        editNote.getText().toString());
                 //화면의 입력난 초기화
                 clearAlledit();
            }


        }); //btnInsert.setOnClickListener

    } //protected void onCreate

    //화면의 입력난 초기화 함수
    private void clearAlledit() {
        editLoc1.setText("");
        editLoc2.setText("");
        spinner1.setSelection(2); //초기화값 "3등급"
        //swtValue = "On";
        swtSwitch.setChecked(true);
//        editNote.getText().clear();
        editNote.setText("");
    }
//임시 주석처리 나중에 해제 대상임
//     @Override
//     protected void onStart() {
//             super.onStart();
//            visitlocationAdapter.startListening();
//     }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        visitlocationAdapter.stopListening();
//    }

//    //검색기능 - 나중에 해제 예정
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.searchlocation,menu);
//        MenuItem item = menu.findItem(R.id.search);
//        SearchView searchView = (SearchView) item.getActionView();
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//
//
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                txtSearch(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String query) {
//                txtSearch(query);
//                return false;
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    //검색실행 - 검색기능 나중에 해제 예정
//    private void txtSearch(String str) {
//        FirebaseRecyclerOptions<VisitLocation> options =
//                new FirebaseRecyclerOptions.Builder<VisitLocation>()
//                .setQuery(locationRef.orderByChild("location1").startAt(str).endAt(str+"~"),VisitLocation.class).build();
//
//        visitlocationAdapter = new VisitLocationAdapter(options);
//        visitlocationAdapter.startListening();
//        recyclerView.setAdapter(visitlocationAdapter);
//    } //private void txtSearch(String str)

    //화면 Display와 함께 저장된 데이터 리스트 조회 --> 미구현 작업예정
//    @Override
//    protected void onStart(){
//         super.onStart();

        //firebase인증 검증 후 저장기능으로 미로그인 사용자는 check하여 회원등록 화면으로 이동처리
        //-->미구현 작업예정

        //등록버튼 누르면 값을 저장
//        btnInsert.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//               //등록 주요 항목이 null값이면 입력하라는 메시지를 처리
//
//               // String 텍스트값을 문자열로 바꾸어 변수에 넣음
//               // locationRef.setValue(editLoc1.getText().toString(),);
//                addvisitlocation(editLoc1.getText().toString(),
//                        editLoc2.getText().toString(),
////                      spinner1.getSelectedItem().toString(), //값을 가져오는 부분
//                        spinnerValue,
//                        swtValue,
//                        editNote.getText().toString());
//            }
//        });
//    } //protected void onStart(){


        //저장 값을 firebase Realtime database로 넘기는 함수
        private void addvisitlocation(String location1, String location2, String grade, String used, String note) {
           //VisitLocation.java에서 선언한 함수
           VisitLocation location = new VisitLocation(location1,location2,grade,used,note);

           //push()는 값을 넣을때 상위 키값을 랜덤으로 설정해주는 함수이며 이 경우에는 미사용(중복을 허용하고 시간 발생기준으로 저장하고자 할때 사용
           //child는 해당 키 위치로 이동하는 함수입니다.
           //키가 없는데 "visitlocation"과 id같이 값을 지정한 경우 자동으로 생성함
           // locationRef.child(id).setValue(location);
           locationRef.push().setValue(location)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void unused) {
                       //저장 successful
                       Toast.makeText(getApplicationContext(),"저장이 잘 되었습니다.!",Toast.LENGTH_SHORT).show();
                 }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //저장 failed
                        Toast.makeText(getApplicationContext(),"저장이 실패하였습니다.!",Toast.LENGTH_SHORT).show();
                     //   android.R.attr snapshot = null; //잘못된 코드
                    //    Log.e("ChildChange",snapshot.key.toString().orEmpty()); //잘못된 코드
                    }
                });
        }

    public static class swtSwitchListener implements CompoundButton.OnCheckedChangeListener {
        private String swtValue;

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked)  //초기상태일 경우 //True이면 체크된 경우
                swtValue = "On";
            else // Flase이면 할 일
                swtValue = "Off";
        }
    } //private class swtSwitchListener

}