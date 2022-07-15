package com.example.teamproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class UpdateActivity extends AppCompatActivity {

    String sKey, sName, sPhone, sDept, sCompany, sRole;
    EditText update_name,update_phone,update_dept,update_company,update_role;

    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Log.d("key2>>>>>", "" + getIntent().hasExtra("key"));
        mDatabase = FirebaseDatabase.getInstance().getReference("Visit").child("UserAccount");
        Button btn_update = findViewById(R.id.btn_update);
         update_name = findViewById(R.id.update_name);
         update_phone = findViewById(R.id.update_phone);
         update_dept = findViewById(R.id.update_dept);
         update_company = findViewById(R.id.update_company);
         update_role = findViewById(R.id.update_role);

        getAndSetIntentData();

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //변경값
                String uName = update_name.getText().toString();
                String uPhone = update_phone.getText().toString();
                String uDept = update_dept.getText().toString();
                String uCompany = update_company.getText().toString();
                String uRole = update_role.getText().toString();


                //파라미터 세팅
                Map<String, Object> hashMap = new HashMap<>();
                hashMap.put("name", uName);
                hashMap.put("phone", uPhone);
                hashMap.put("dept", uDept);
                hashMap.put("company", uCompany);
                hashMap.put("role", uRole);
                Log.d("name>>", "" + uName);


                mDatabase.child(sKey).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("정보변경", "Complete");
                        finish();
                    }


                });
            }
        });
    }

        public void getAndSetIntentData () {
                  if (getIntent().hasExtra("key") && getIntent().hasExtra("name") &&
                         getIntent().hasExtra("phone") && getIntent().hasExtra("Dept") &&
                            getIntent().hasExtra("company") && getIntent().hasExtra("Role")) {

            //데이터가져오기
            sKey = getIntent().getStringExtra("key");
            sName = getIntent().getStringExtra("name");
            sPhone = getIntent().getStringExtra("phone");
            sCompany = getIntent().getStringExtra("company");
            sDept = getIntent().getStringExtra("Dept");
            sRole = getIntent().getStringExtra("Role");


            //데이터 넣기
            update_name.setText(sName);
            update_phone.setText(sPhone);
            update_company.setText(sCompany);
            update_dept.setText(sDept);
            update_role.setText(sRole);
            Log.d("key2>>>", "" + sKey);
                    }
        }
}