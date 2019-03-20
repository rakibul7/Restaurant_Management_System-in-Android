package com.example.mahbuburrahman.resturantmanagement.activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.example.mahbuburrahman.resturantmanagement.R;
import com.example.mahbuburrahman.resturantmanagement.adapters.RecylerAdapter;
import com.example.mahbuburrahman.resturantmanagement.database.DataSource;
import com.example.mahbuburrahman.resturantmanagement.fragments.AllProductsFragment;
import com.example.mahbuburrahman.resturantmanagement.fragments.NewProductFragment;
import com.example.mahbuburrahman.resturantmanagement.fragments.StoresFragment;
import com.example.mahbuburrahman.resturantmanagement.fragments.UsersFragment;
import com.example.mahbuburrahman.resturantmanagement.model.Product;

public class AdminActivity extends AppCompatActivity
        implements RecylerAdapter.OnFoodItemClickedListener,
        NewProductFragment.OnUploadClickListener{

    private ViewPager pager;
    public static DataSource mDataSource;
    private FrameLayout mFrameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        mDataSource = new DataSource(this);


        //Attach the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //TODO: View Pager and TabBar
        SectionPagerAdapter pagerAdapter = new SectionPagerAdapter(getSupportFragmentManager(),this);

        pager = findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);
        pager.setVisibility(View.VISIBLE);

        //Attach the Tab bar
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);

        Log.d("admin", "onCreate: admin activity");

    }

    @Override
    public void onItemClicked(Product item) {
        //TODO: on item click
        //UpdateProductFragment updateProductFragment = new UpdateProductFragment();
        //updateProductFragment.setData(item);
        Intent updateIntenet = new Intent(this, UpdateActivity.class);
        updateIntenet.putExtra(UpdateActivity.PROD_ID, item.getId());
        this.finish();
        startActivity(updateIntenet);

    }

    @Override
    public void onUploadClick(Product product) {
        //TODO: on upload product
        Log.d("upload", "onUpload: "+product.toString());
        mDataSource.insertProduct(product);
        MainActivity.allProducts = mDataSource.getAllProducts();


    }



    private class SectionPagerAdapter extends FragmentPagerAdapter {

        Context mContext;
        public SectionPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            mContext = context;
        }

        @Override
        public Fragment getItem(int position) {
        switch (position) {

            case 0:
                return new AllProductsFragment();
            case 1:
                return new NewProductFragment();
            case 2:
                return new UsersFragment();
            case 3:
                return new StoresFragment();
        }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return mContext.getResources().getText(R.string.all_products_tab);
                case 1:
                    return mContext.getResources().getText(R.string.new_product_tab);
                case 2:
                    return mContext.getResources().getText(R.string.users_tab);
                case 3:
                    return mContext.getResources().getText(R.string.stores_tab);

            }
            return null;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("Activity", "onActivityResult: ");
        super.onActivityResult(requestCode, resultCode, data);
    }
}
