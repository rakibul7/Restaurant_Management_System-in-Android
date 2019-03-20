package com.example.mahbuburrahman.resturantmanagement.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mahbuburrahman on 12/17/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {



    public static final String DATABASE_NAME = "restaurant.db";
    public static final int DATABASE_VERSION = 1;

    //TODO: PRODUCT Table
    public static final String PRODUCT_TABLE = "product_tbl";
    public static final String COL_PROD_ID = "prod_id";
    public static final String COL_PROD_NAME = "prod_name";
    public static final String COL_PROD_PRICE = "prod_price";
    public static final String COL_PROD_IMAGE = "prod_img";
    public static final String COL_PROD_IMAGE_PATH = "prod_img_path";
    public static final String COL_PROD_RATING = "prod_rating";
    public static final String COL_PROD_CATEGORY = "prod_category";
    public static final String COL_PROD_RATED_COUNT = "prod_rate_count";
    public static final String COL_PROD_REVIEW_COUNT = "prod_review_count";

    //TODO: USER table
    public static final String USER_TABLE = "user_tbl";
    public static final String COL_USR_ID = "user_id";
    public static final String COL_USR_NAME = "user_name";
    public static final String COL_USR_MAIL = "user_mail";
    public static final String COL_USR_PASS = "user_pass";
    public static final String COL_USR_PHONE = "user_phone";
    public static final String COL_USR_IMG = "user_img";
    public static final String COL_USR_IMG_PATH = "user_img_path";
    public static final String COL_USR_FAV_CONT = "user_rated";
    public static final String COL_USR_REV_COUNT = "user_reviewed";

    //TODO: REVIEW table
    public static final String REVIEW_TABLE = "review_tbl";
    public static final String COL_REV_ID = "rev_id";
    public static final String COL_REV_USER_ID = "rev_user_id";
    public static final String COL_REV_PROD_ID = "rev_prod_id";
    public static final String COL_REV_COMMENT= "rev_comment";

    //TODO: FAVORITE table
    public static final String FAVORITE_TABLE = "favorite_tbl";
    public static final String COL_FAV_ID = "fav_id";
    public static final String COL_FAV_USER_ID = "fav_user_id";
    public static final String COL_FAV_PROD_ID = "fav_prod_id";

    //TODO: CATEGORY table
    public static final String CATEGORY_TABLE = "category_tbl";
    public static final String COL_CAT_ID = "cat_id";
    public static final String COL_CAT_NAME= "cat_name";


    //TODO: create PRODUCT
    public static final String CREATE_TABLE_PRODUCT = "create table " +
            PRODUCT_TABLE + "( " +
            COL_PROD_ID +" integer primary key, "+
            COL_PROD_NAME +" text, "+
            COL_PROD_PRICE+" real, "+
            COL_PROD_RATING+" real, "+
            COL_PROD_CATEGORY +" text, "+
            COL_PROD_RATED_COUNT+" integer, "+
            COL_PROD_REVIEW_COUNT+" integer, "+
            COL_PROD_IMAGE+" text,"+
            COL_PROD_IMAGE_PATH+" text );";

    //TODO: create USER
    public static final String CREATE_TABLE_USER = "create table " +
            USER_TABLE + "( " +
            COL_USR_ID +" integer primary key, "+
            COL_USR_NAME +" text, "+
            COL_USR_MAIL+" text, "+
            COL_USR_PHONE+" integer, "+
            COL_USR_PASS +" text, "+
            COL_USR_FAV_CONT +" integer, "+
            COL_USR_REV_COUNT+" integer, "+
            COL_USR_IMG+" text, "+
            COL_USR_IMG_PATH+" text );";

    //TODO: create REVIEW
    public static final String CREATE_TABLE_REVIEW= "create table " +
            REVIEW_TABLE + "( " +
            COL_REV_ID+" integer primary key, "+
            COL_REV_PROD_ID +" integer, "+
            COL_REV_USER_ID+" integer, "+
            COL_REV_COMMENT+" text );";

        //TODO: create FAVORITE
    public static final String CREATE_TABLE_FAVORITE = "create table " +
            FAVORITE_TABLE+ "( " +
            COL_FAV_ID+" integer primary key, "+
            COL_FAV_USER_ID+" integer, "+
            COL_FAV_PROD_ID+" integer );";

    //TODO: create CATEGORY
    public static final String CREATE_TABLE_CATEGORY= "create table " +
            CATEGORY_TABLE+ "( " +
            COL_CAT_ID+" integer primary key, "+
            COL_CAT_NAME+" text );";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //called once in life time of app
        db.execSQL(CREATE_TABLE_PRODUCT);
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_REVIEW);
        db.execSQL(CREATE_TABLE_FAVORITE);
        db.execSQL(CREATE_TABLE_CATEGORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
