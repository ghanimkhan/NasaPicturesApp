package com.Obvious.nasapicturesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.Obvious.nasapicturesapp.Adapter.GridAdapter;
import com.Obvious.nasapicturesapp.DataModel.DataModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    public static List<DataModel> nasaModelArrayList = new ArrayList<>();
    GridAdapter Adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.UserList);
        getJsonFileFromLocally();

    }
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = MainActivity.this.getAssets().open("Data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void getJsonFileFromLocally() {
        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            JSONArray jsonArray = new JSONArray(loadJSONFromAsset());
            nasaModelArrayList = new ArrayList();
            for (int i = 0; i < jsonArray.length(); i++) {
                //Log.d("TAG", jsonArray.getJSONObject(i)+"getJsonFileFromLocally: ");
                DataModel model = gson.fromJson(jsonArray.getString(i), DataModel.class);
                nasaModelArrayList.add(model);
            }

            Adapter = new GridAdapter(MainActivity.this, nasaModelArrayList);
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            recyclerView.setAdapter(Adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}