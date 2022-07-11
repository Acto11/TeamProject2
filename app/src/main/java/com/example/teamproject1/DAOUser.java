package com.example.teamproject1;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

public class DAOUser {

    private DatabaseReference databaseReference;

    DAOUser(){

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(UserAccount.class.getName());

    }
    //등록
    public Task<Void> add(UserAccount user){
        return databaseReference.push().setValue(user);
    }
    //리스트
    public Query get(){
        return databaseReference;
    }
    //수정
    public Task<Void> update(String key, HashMap<String, Object> hashMap){

        return  databaseReference.child(key).updateChildren(hashMap);
    }
}
