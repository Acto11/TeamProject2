package com.example.teamproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    UserAdapter adapter;

    //데이터베이스
    DAOUser dao;

    //키변수
    String key = "";

    ArrayList<UserAccount> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.rv);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        //어댑터
        adapter = new UserAdapter(this, list);

        //리싸이클러 뷰 어댑터
        recyclerView.setAdapter(adapter);

        //db초기화
        dao = new DAOUser();

        loadData();
    }

    private void loadData(){
        dao.get().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();

                for(DataSnapshot data : snapshot.getChildren()){

                    UserAccount user = data.getValue(UserAccount.class);

                    //키값 가져오기
                    key = data.getKey();

                    //키값 담기
                    user.setIdToken(key);

                    //리스트담기
                    list.add(user);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}