package com.perusudroid.numberswitch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.perusudroid.numberswitch.model.Data;
import com.perusudroid.numberswitch.model.ProductResponse;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ShoppingAdapter shoppingAdapter;
    List<Data> mList = new ArrayList<>();
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        setAssets();
        doLoadDummyData();
    }


    private void bindViews() {
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void setAssets() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void doLoadDummyData() {
        ProductResponse productResponse = new Gson().fromJson(readFromFile(), ProductResponse.class);
        for (int i = 0; i < productResponse.getData().size(); i++) {
            productResponse.getData().get(i).set_selected(false);
        }

        mList = productResponse.getData();

        setAdapter(mList);
    }


    private String readFromFile() {
        String ret = "";
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.sample_products);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String receiveString = "";
            StringBuilder stringBuilder = new StringBuilder();
            while ((receiveString = bufferedReader.readLine()) != null) {
                stringBuilder.append(receiveString);
            }
            inputStream.close();
            ret = stringBuilder.toString();
        } catch (FileNotFoundException e) {
            Log.e("readFromFile", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("readFromFile", "Can not read file: " + e.toString());
        }
        return ret;
    }

    private void setAdapter(List<Data> mList) {

        if (shoppingAdapter == null) {
            shoppingAdapter = new ShoppingAdapter(mList);
            recyclerView.setAdapter(shoppingAdapter);
        }else{
            shoppingAdapter.refresh(mList);
        }

    }


}
