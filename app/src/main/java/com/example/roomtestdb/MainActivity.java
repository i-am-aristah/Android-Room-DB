package com.example.roomtestdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button btnAdd, btnReset;
    LinearLayoutManager linearLayoutManager;
    EditText edtxtUser;

    List<MainData> dataList;

    RoomDB database;

    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //assign variables

        recyclerView=findViewById(R.id.rcv);
        btnAdd=findViewById(R.id.btn_add_user);
        btnReset =findViewById(R.id.btn_reset_users);
        edtxtUser = findViewById(R.id.edtxt_uname);

        recyclerView.setHasFixedSize(true);


        database = RoomDB.getInstance(this);
        dataList=database.mainDao().getAll();
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter= new MainAdapter(MainActivity.this,dataList);

        recyclerView.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainData data = new MainData();
                String uname= edtxtUser.getText().toString().trim();
                data.setUsername(uname);

                Log.d("TAG",uname);

                if(!uname.equals(""))
                {

                    Log.d("TAG",data.getUsername());

                    database.mainDao().insert(data);
                    dataList.clear();
                    dataList.addAll(database.mainDao().getAll());
                    adapter.notifyDataSetChanged();
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                database.mainDao().reset(dataList);
                dataList.clear();
                dataList.addAll(database.mainDao().getAll());
                adapter.notifyDataSetChanged();
            }
        });


    }
}