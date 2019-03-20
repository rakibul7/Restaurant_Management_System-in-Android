package com.example.mahbuburrahman.resturantmanagement.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.mahbuburrahman.resturantmanagement.R;
import com.example.mahbuburrahman.resturantmanagement.adapters.RecylerAdapter;
import com.example.mahbuburrahman.resturantmanagement.database.DataSource;
import com.example.mahbuburrahman.resturantmanagement.fragments.MainFragment;
import com.example.mahbuburrahman.resturantmanagement.fragments.UserProfileFragment;
import com.example.mahbuburrahman.resturantmanagement.model.Product;
import com.example.mahbuburrahman.resturantmanagement.model.User;
import com.example.mahbuburrahman.resturantmanagement.singletones.ImageFactory;
import com.example.mahbuburrahman.storemanagement.feedback_app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements RecylerAdapter.OnFoodItemClickedListener,
        NavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "main";
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private BottomNavigationView mBottomNavigationView;
    public static boolean isLoggedIn = false;

    public static SharedPreferences preferences;
    public static ArrayList<Object> allProducts;

    public static FragmentManager manager;
    public static DataSource mDataSource;
    public static User user = null;
    public static String QueryString = null;
    private Spinner spinner = null;
    private String priceRange = null;

    public static boolean Admin = false;

    MainFragment mMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer_layout);


        //TODO: save user to sharedPrefs


        //Attach the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        manager = getSupportFragmentManager();

        //TODO: navigation view
        mDrawerLayout = findViewById(R.id.nav_drawer_id);
        mNavigationView = findViewById(R.id.nav_view_id);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,toolbar, R.string.open_drawer,R.string.close_drawer);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);

        //TODO: sub menu navigation




        //TODO: BottomNavigation
        mBottomNavigationView = findViewById(R.id.nav_bottom_id);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);

        mDataSource = new DataSource(this);
        getALlProduct();


      //TODO: starting fragment

        Log.d("search", "onCreate: Called");

    }
    public void checkQuery(ArrayList<Object> products) {
        //SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
      //  String query = preferences.getString(SearchActivity.SEARCH_QUERY, null);
        String query = QueryString;
        mMainFragment = new MainFragment();
        if (query != null) {

            mMainFragment.setData( mDataSource.searchProductByName(query));
            Log.d("search", "on query from Main: "+mDataSource.searchProductByName(query).size());
            QueryString = null; //query should be null after search

        }else if (products != null) {
            mMainFragment.setData(products);
        }
        else {
            mMainFragment.setAllProduct();

        }
        manager.popBackStack();
        manager.beginTransaction().replace(R.id.main_container, mMainFragment).commit();
    }
    public void getALlProduct() {
        //TODO: get all products
        allProducts = mDataSource.getAllProducts();
        ImageFactory imageFactory = ImageFactory.getInstance();

            for(Object object : allProducts) {
                Product product = (Product) object;
                Bitmap bitmap = loadImageFromStorage(product.getItemImage(), product.getItemImagePath());
                imageFactory.setImageBitmap(product.getId(), bitmap);
            }

    }
    //TODO: retrieve images from storage
    private Bitmap loadImageFromStorage(String file_name, String path) {

        try {
            File f=new File(path, file_name);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            return b;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void onItemClicked(Product item) {
        Log.d(TAG, "onItemClicked: "+(item.getId()));

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.ITEM_KEY, item.getId());
        intent.putExtra(DetailActivity.TEXT_KEY, item.getItemName());

        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem itemSpinner = menu.findItem(R.id.action_price_limit_spinner);

        spinner = (Spinner) MenuItemCompat.getActionView(itemSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.priceLimit, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                priceRange = (String) spinner.getItemAtPosition(i);
                if (i > 0) {
                    Log.d(TAG, "on Spinner item Selected: "+priceRange);
                    checkQuery(mDataSource.getProductsInBetweenPrice(priceRange));
                } else{
                    checkQuery(null);
                    priceRange = null;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_setting:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.action_search:
                Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(searchIntent);
                break;
            case R.id.action_logout:
                isLoggedIn = false;
                user = null;
                Admin = false;
                manager.popBackStack();
                manager.beginTransaction().replace(R.id.main_container, new MainFragment());
                break;
            case R.id.action_admin:
                Intent adminIntent = new Intent(MainActivity.this, AdminActivity.class);
                startActivity(adminIntent);
                break;
            case R.id.action_price_limit_spinner:
                if (spinner != null) {
                    Log.d(TAG, "on Spinner item Selected: "+spinner.getSelectedItem());
                }
                Log.d(TAG, "Spinner : "+(spinner == null));
                break;
            case R.id.feedback:
                 Intent intent1=new Intent(getApplicationContext(),feedback_app.class);
                 startActivity(intent1);
                 break;
        }

        return super.onOptionsItemSelected(item);
    }

//TODO: NavigationDrawer and Bottom Navigation
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.pizza_menu_id:
                checkQuery(mDataSource.getProductsByCategory("Pizza", priceRange));
                break;
            case R.id.pasta_menu_id:
                checkQuery(mDataSource.getProductsByCategory("Pasta", priceRange));
                break;
            case R.id.burgar_menu_id:
                checkQuery(mDataSource.getProductsByCategory("Burger", priceRange));
                break;
            case R.id.drink_menu_id:
                checkQuery(mDataSource.getProductsByCategory("Drinks", priceRange));
                break;
            case R.id.menu_indiana:
                checkQuery(mDataSource.getProductsByCategory("Indiana", priceRange));
                break;
            case R.id.menu_french:
                checkQuery(mDataSource.getProductsByCategory("French", priceRange));
                break;
            case R.id.menu_bengali:
                checkQuery(mDataSource.getProductsByCategory("Bengali", priceRange));
                break;
            case R.id.menu_chicness:
                checkQuery(mDataSource.getProductsByCategory("Chinese", priceRange));
                break;
            case R.id.menu_japaness:
                checkQuery(mDataSource.getProductsByCategory("Japanese", priceRange));
                break;


            case R.id.user_menu_id:
                if (!isLoggedIn && !Admin) {
                    Intent logIntent = new Intent(this, LoginActivity.class);
                    startActivity(logIntent);
                }else {
                    manager.beginTransaction().replace(R.id.main_container, new UserProfileFragment()).commit();
                }
                break;
            case R.id.home_menu_id:
                checkQuery(null);
                break;
            case R.id.new_come_menu_id:
                checkQuery(mDataSource.getRecentProducts());
                break;
        }


        mDrawerLayout.closeDrawer(GravityCompat.START);



        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem logoutItem = menu.findItem(R.id.action_logout);
        MenuItem adminItem = menu.findItem(R.id.action_admin);
        if (Admin) {
            adminItem.setVisible(true);
        }else{
            adminItem.setVisible(false);
        }
        if (isLoggedIn || Admin) {
            logoutItem.setVisible(true);
        }else{
            logoutItem.setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //TODO: check if any particular search
        checkQuery(null);
        Log.d(TAG, "onResume: Called and back stack "+manager.getBackStackEntryCount());
    }
}
