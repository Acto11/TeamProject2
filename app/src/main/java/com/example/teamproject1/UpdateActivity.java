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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.core.Tag;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UpdateActivity extends AppCompatActivity {

    String sKey, sName, sPhone;
    Button btn_update = findViewById(R.id.btn_update);
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText update_name = findViewById(R.id.update_name);
    EditText update_phone = findViewById(R.id.update_phone);
    EditText update_dept = findViewById(R.id.update_dept);
    EditText update_company = findViewById(R.id.update_company);
    EditText update_role = findViewById(R.id.update_role);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        DAOUser dao = new DAOUser();
        getAndSetIntentData();

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uName = update_name.getText().toString();
                String uPhone = update_phone.getText().toString();


                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("user_name", uName);
                hashMap.put("user_phone", uPhone);


                dao.update(sKey, hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "업데이트성공", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "업데이트실패", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void getAndSetIntentData () {
        if(getIntent().hasExtra("key") && getIntent().hasExtra("name") &&
         getIntent().hasExtra("phone")){
            sKey = getIntent().getStringExtra("key");
            sName = getIntent().getStringExtra("name");
            sPhone = getIntent().getStringExtra("phone");

            update_name.setText(sName);
            update_phone.setText(sPhone);
        }
        }




}