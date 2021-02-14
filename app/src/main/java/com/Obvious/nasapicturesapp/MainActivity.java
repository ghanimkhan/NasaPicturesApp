package com.Obvious.nasapicturesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.Obvious.nasapicturesapp.Adapter.GridAdapter;
import com.Obvious.nasapicturesapp.DataModel.DataModel;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.UserList);



        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

}