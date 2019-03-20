package com.example.mahbuburrahman.resturantmanagement.activities;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mahbuburrahman.resturantmanagement.R;
import com.example.mahbuburrahman.resturantmanagement.database.DataSource;
import com.example.mahbuburrahman.resturantmanagement.fragments.DetailsFragment;
import com.example.mahbuburrahman.resturantmanagement.fragments.ReviewFragment;
import com.example.mahbuburrahman.resturantmanagement.model.Product;
import com.example.mahbuburrahman.resturantmanagement.model.Review;
import com.example.mahbuburrahman.resturantmanagement.singletones.ImageFactory;

public class DetailActivity extends AppCompatActivity implements ReviewFragment.onReviewCompleteListener {

    private ImageView toolbarImage;
    public static final String ITEM_KEY = "img_key";
    public static final String TEXT_KEY = "text_key";
    private FloatingActionButton ftb;
    private ImageButton favBtn;
    private FragmentManager mFragmentManager;
    public static DataSource mDataSource;

    private Bitmap mBitmap;
    private int id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mFragmentManager = getSupportFragmentManager();
        mDataSource = new DataSource(this);
        //favBtn = findViewById(R.id.favBtn);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        toolbarImage = findViewById(R.id.img_toolbar);
        actionBar.setTitle("");

        id = getIntent().getExtras().getInt(ITEM_KEY);
        final String name = getIntent().getExtras().getString(TEXT_KEY);

        Product product = mDataSource.getProductById(id, null);

        actionBar.setTitle("");
        Log.d("img", "onCreate: "+id);
        mBitmap = ImageFactory.getInstance().getImageBitmap(product.getId());
        if (mBitmap != null) {
            toolbarImage.setImageBitmap(mBitmap);
        }
        Log.d("img", "onCreate: "+(mBitmap == null));



        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setItemId(id);

        mFragmentManager
                .beginTransaction()
                .replace(R.id.detail_container,detailsFragment)
                .commit();

        ftb = findViewById(R.id.accept_float_btn);
        ftb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ftb.setImageResource(R.drawable.done);
               if (!MainActivity.isLoggedIn){
                   Intent logIntent = new Intent(DetailActivity.this, LoginActivity.class);
                   startActivity(logIntent);
               } else {
                ReviewFragment reviewFragment = new ReviewFragment();
                reviewFragment.setArgs(name);
                   mFragmentManager
                           .beginTransaction()
                           .replace(R.id.detail_container, reviewFragment)
                           .addToBackStack("review")
                           .commit();
               }
            }
        });



    }

    @Override
    public void afterReviewComplete(String comments) {
        View v = findViewById(R.id.coordinator);
        Snackbar snackbar = Snackbar.make(v, "Cancel the review? ", Snackbar.LENGTH_INDEFINITE);
        snackbar.setDuration(5000);
        snackbar.setActionTextColor(Color.RED);
        snackbar.setAction("Undo!", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailActivity.this, "Review cancel", Toast.LENGTH_SHORT).show();
                ftb.setImageResource(R.drawable.done);
                detailsShow();

            }
        });
        snackbar.show();
        ftb.setImageResource(R.drawable.plus);
        mFragmentManager.popBackStack("review",FragmentManager.POP_BACK_STACK_INCLUSIVE);
        if (comments != null && id != -1) {
            Review review = new Review(MainActivity.user.getId(),id, comments);
            if( mDataSource.insertReview(review)) {
                Toast.makeText(this, "Review complete",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Review failed!",Toast.LENGTH_SHORT).show();
            }
            detailsShow();
        }
    }
    public void detailsShow() {
        mFragmentManager
                .beginTransaction()
                .replace(R.id.detail_container, new ReviewFragment())
                .addToBackStack("detail")
                .commit();
    }


}
