package com.example.mahbuburrahman.resturantmanagement.activities;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.SearchView;

import com.example.mahbuburrahman.resturantmanagement.R;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "search";
    public static final String SEARCH_QUERY = "query";
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("Search");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
        mSearchView.setSearchableInfo(searchableInfo);


        mSearchView.setIconified(false);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                preferences.edit().putString(SEARCH_QUERY, s).apply();
                MainActivity.QueryString = s;
                Log.d(TAG, "onQueryTextSubmit: called "+s);
                mSearchView.clearFocus();
                finish();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d(TAG, "onQueryTextChange: "+s);
                return false;
            }
        });


        return true;
    }

}
