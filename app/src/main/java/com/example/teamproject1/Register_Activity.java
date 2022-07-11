package com.example.teamproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register_Activity extends AppCompatActivity {

    //파이어베이스 인증
    private FirebaseAuth mFirebaseAuth;
    //실시간 데이터베이스
    private DatabaseReference mDatabaseRef;
    private EditText EtEmail,EtPwd,EtName,EtPhone,EtCompany,EtDept,EtRole;
    private Button BtnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitiy_register);
//파이어베이스 인증
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("UserAccount");
//EditText(회원가입정보)
        EtEmail = findViewById(R.id.et_email);
        EtPwd = findViewById(R.id.et_pwd);
        EtName = findViewById(R.id.et_name);
        EtPhone = findViewById(R.id.et_phone);
        EtCompany = findViewById(R.id.et_company);
        EtDept = findViewById(R.id.et_dept);
        EtRole = findViewById(R.id.et_role);

        BtnRegister = findViewById(R.id.btn_register);

        //회원가입
        BtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strEmail = EtEmail.getText().toString();
                String strPwd = EtPwd.getText().toString();
                String strName = EtName.getText().toString();
                String strPhone = EtPhone.getText().toString();
                String strCompany = EtCompany.getText().toString();
                String strDept = EtDept.getText().toString();
                String strRole = EtRole.getText().toString();

                //파이어베이스 인증
                mFirebaseAuth.createUserWithEmailAndPassword(strEmail,strPwd).addOnCompleteListener(Register_Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //파이어베이스인증
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                            UserAccount account = new UserAccount();
                            account.setIdToken(firebaseUser.getUid());
                            account.setEmailId(firebaseUser.getEmail());
                            account.setName(strName);
                            account.setCompany(strCompany);
                            account.setDept(strDept);
                            account.setPhone(strPhone);
                            account.setRole(strRole);
                            account.setPassword(strPwd);

                            //DB에 삽입
                            mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);

                            Toast.makeText(getApplicationContext(),"회원가입 성공!",Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),"회원가입 실패!",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }
}