package com.mchgmail.pavel.testapp;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.mchgmail.pavel.testapp.adapters.PicturesAdapter;
import com.mchgmail.pavel.testapp.helper.Ajax;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    RecyclerView recycler;
    PicturesAdapter trendingAdapter;
    PicturesAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fresco.initialize(this);

        setTitle("Trending GIFs");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

        recycler = findViewById(R.id.recycler);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        startTrending();
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        getMenuInflater().inflate( R.menu.menu, menu );

        final MenuItem myActionMenuItem = menu.findItem(R.id.app_bar_search);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Search(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Search(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu( menu );
    }

    public void startTrending() {
        Log.d("SCREEN", "Trending");
        trendingAdapter = new PicturesAdapter(this, "trending", null);
        recycler.setAdapter(trendingAdapter);
    }

    public void Search (String text){
        if(text.trim().equals("")) {
            startTrending();
        } else {
            Log.d("SCREEN", "Search");
            searchAdapter = new PicturesAdapter(this, "search", text);
            recycler.setAdapter(searchAdapter);
        }

    }

}
