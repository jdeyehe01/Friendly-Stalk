package com.juanito.friendlystalk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyFriendsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyUserAdapter userAdapter;
    private List<String> userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        userList = new ArrayList<String>();

        userList.add("Jean");
        userList.add("deyehe");
        userList.add("Dujardin");

        userAdapter = new MyUserAdapter(userList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(userAdapter);
    }
}