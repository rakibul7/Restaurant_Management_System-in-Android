package com.example.mahbuburrahman.resturantmanagement.activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.mahbuburrahman.resturantmanagement.R;
import com.example.mahbuburrahman.resturantmanagement.fragments.LoginFragment;

public class LoginActivity extends AppCompatActivity {

    public static FragmentManager loginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loginManager = getSupportFragmentManager();


        loginManager.beginTransaction()
                .add(R.id.log_container, new LoginFragment())
                //.addToBackStack("log")
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (loginManager.getBackStackEntryCount() > 0) {
            loginManager.popBackStack();
        }else {

            super.onBackPressed();
        }
    }
}
