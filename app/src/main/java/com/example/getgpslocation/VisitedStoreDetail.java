package com.example.getgpslocation;

import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.getgpslocation.models.VistedStore;


public class VisitedStoreDetail extends AppCompatActivity {

    VistedStore vistedStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visited_store_detail);

        vistedStore = (VistedStore) getIntent().getSerializableExtra("Store");
        Log.e("LatLog",vistedStore.getLoge() +" "+ vistedStore.getLat());

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }
}
