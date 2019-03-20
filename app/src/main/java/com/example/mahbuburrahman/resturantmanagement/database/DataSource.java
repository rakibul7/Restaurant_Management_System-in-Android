package com.example.mahbuburrahman.resturantmanagement.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.mahbuburrahman.resturantmanagement.model.Category;
import com.example.mahbuburrahman.resturantmanagement.model.Favorite;
import com.example.mahbuburrahman.resturantmanagement.model.Product;
import com.example.mahbuburrahman.resturantmanagement.model.Review;
import com.example.mahbuburrahman.resturantmanagement.model.User;

import java.util.ArrayList;

/**
 * Created by Mahbuburrahman on 12/17/17.
 */

public class DataSource {

    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;
    Context mContext;


    public DataSource(Context context) {
        mContext = context;
        mDatabaseHelper = new DatabaseHelper(context);
        mDatabase = mDatabaseHelper.getWritableDatabase();
    }

    public void open() {
        mDatabase = mDatabaseHelper.getWritableDatabase();
    }
    public void close() {
        mDatabase.close();
    }

    /**
     *
     * Product Table
     * */

    //TODO: insert
    public boolean insertProduct(Product product){
        this.open();
        ContentValues values = product.toValues();

        long insertedRow = mDatabase.insert(mDatabaseHelper.PRODUCT_TABLE, null, values);
        this.close();

        if (insertedRow > 0) {
            return true;
        }
        return false;
    }

    //TODO: read
    public ArrayList<Object> getAllProducts() {
        this.open();

       // String[] columns = new String[]{mDatabaseHelper.COL_EMP_ID, mDatabaseHelper.COL_EMP_NAME, mDatabaseHelper.COL_EMP_DESIGNATION};

        //mDatabase.rawQuery("select * from "+mDatabaseHelper.EMP_TABLE, null);
       /* mDatabase.query(mDatabaseHelper.EMP_TABLE, columns , null,
                null,null,null,null); */

       Cursor cursor = mDatabase.query(mDatabaseHelper.PRODUCT_TABLE, null, null,
                null,null,null,null);

       ArrayList<Object> allProducts = new ArrayList<>();

       cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_ID));
                String name = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_NAME));
                double price = cursor.getDouble(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_PRICE));
                double rating = cursor.getDouble(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_RATING));
                String category = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_CATEGORY));
                int ratCount = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_RATED_COUNT));
                int revCount = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_REVIEW_COUNT));
                String img = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_IMAGE));
                String imgPath = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_IMAGE_PATH));



                allProducts.add(new Product(id,name,price,img,imgPath,rating,category,ratCount,revCount));
                Log.d("all", "Employee ID: " + id);
            } while (cursor.moveToNext());

        }
        cursor.close();
        this.close();
       return allProducts;
    }
    public ArrayList<Object> getRecentProducts() {
        this.open();

        // String[] columns = new String[]{mDatabaseHelper.COL_EMP_ID, mDatabaseHelper.COL_EMP_NAME, mDatabaseHelper.COL_EMP_DESIGNATION};

        //mDatabase.rawQuery("select * from "+mDatabaseHelper.EMP_TABLE, null);
       /* mDatabase.query(mDatabaseHelper.EMP_TABLE, columns , null,
                null,null,null,null); */

        Cursor cursor = mDatabase.rawQuery("select * from "+ mDatabaseHelper.PRODUCT_TABLE +" ORDER BY prod_id  DESC LIMIT 5",null);
        ArrayList<Object> allProducts = new ArrayList<>();

        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_ID));
                String name = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_NAME));
                double price = cursor.getDouble(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_PRICE));
                double rating = cursor.getDouble(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_RATING));
                String category = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_CATEGORY));
                int ratCount = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_RATED_COUNT));
                int revCount = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_REVIEW_COUNT));
                String img = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_IMAGE));
                String imgPath = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_IMAGE_PATH));



                allProducts.add(new Product(id,name,price,img,imgPath,rating,category,ratCount,revCount));
                Log.d("all", "Employee ID: " + id);
            } while (cursor.moveToNext());

        }
        cursor.close();
        this.close();
        return allProducts;
    }

    public Product getProductById(int id,String[] columns) {
        this.open();
        Cursor cursor = mDatabase.query(mDatabaseHelper.PRODUCT_TABLE, columns,
                mDatabaseHelper.COL_PROD_ID +"="+id,null,null,null,null);

        Product product = null;
        if (cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            int uid = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_ID));
            String name = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_NAME));
            double price = cursor.getDouble(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_PRICE));
            double rating = cursor.getDouble(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_RATING));
            String category = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_CATEGORY));
            int ratCount = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_RATED_COUNT));
            int revCount = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_REVIEW_COUNT));
            String img = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_IMAGE));
            String imgPath = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_IMAGE_PATH));

            product = new Product(uid,name,price,img,imgPath,rating,category,ratCount,revCount);
        }

        cursor.close();
        this.close();

        return product;
    }
    //TODO: Get Products by price in between
    public ArrayList<Object> getProductsInBetweenPrice(String prices){
        this.open();
        Log.d("price", "in between: called "+prices);

        //String[] selection = new String[] {pName, "Android Developer"};
        Cursor cursor = null;

        String[] pricess = prices.split("-");
        int start = Integer.parseInt(pricess[0]);
        int end = Integer.parseInt(pricess[1]);


        String query = "select * from "+mDatabaseHelper.PRODUCT_TABLE+ " where "+
                mDatabaseHelper.COL_PROD_PRICE +" between "+start +" and "+end;

        cursor = mDatabase.rawQuery(query, null);
/*        cursor = mDatabase.query(mDatabaseHelper.PRODUCT_TABLE, null,
                    mDatabaseHelper.COL_PROD_ID + " = '" + pName + "'",
                    null, null, null, null);*/

        ArrayList<Object> allSelectedProducts = new ArrayList<>();

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                int uid = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_ID));
                String name = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_NAME));
                double price = cursor.getDouble(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_PRICE));
                double rating = cursor.getDouble(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_RATING));
                String category = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_CATEGORY));
                int ratCount = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_RATED_COUNT));
                int revCount = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_REVIEW_COUNT));
                String img = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_IMAGE));
                String imgPath = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_IMAGE_PATH));

                allSelectedProducts.add(new Product(uid,name,price,img,imgPath,rating,category,ratCount,revCount));
                Log.d("price", "found : " + name+" id "+uid);
            } while (cursor.moveToNext());
        }
        cursor.close();
        this.close();
        return allSelectedProducts;
    }

    //TODO: Get Products by its category
    public ArrayList<Object> getProductsByCategory(String cName, String prices) {
        this.open();
        Log.d("category", "category: called "+cName);

        //String[] selection = new String[] {pName, "Android Developer"};
        Cursor cursor = null;
        if (prices != null ){
            String[] price = prices.split("-");
            int start = Integer.parseInt(price[0]);
            int end = Integer.parseInt(price[1]);
            String query = "select * from "+mDatabaseHelper.PRODUCT_TABLE+ " where "+
                    mDatabaseHelper.COL_PROD_CATEGORY + " = '" + cName + "' and "+
                    mDatabaseHelper.COL_PROD_PRICE +" between "+start +" and "+end;

            cursor = mDatabase.rawQuery(query, null);

        } else {
            cursor = mDatabase.query(mDatabaseHelper.PRODUCT_TABLE, null,
                    mDatabaseHelper.COL_PROD_CATEGORY + " = '" + cName + "'",
                    null, null, null, null);
        }

        ArrayList<Object> allSelectedProducts = new ArrayList<>();

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {

                int uid = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_ID));
                String name = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_NAME));
                double price = cursor.getDouble(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_PRICE));
                double rating = cursor.getDouble(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_RATING));
                String category = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_CATEGORY));
                int ratCount = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_RATED_COUNT));
                int revCount = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_REVIEW_COUNT));
                String img = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_IMAGE));
                String imgPath = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_IMAGE_PATH));

                allSelectedProducts.add(new Product(uid,name,price,img,imgPath,rating,category,ratCount,revCount));
                Log.d("category", "found : " + name+" id "+uid);
            } while (cursor.moveToNext());
        }
        cursor.close();
        this.close();
        return allSelectedProducts;
    }

    //TODO: Get Products by its Name
    public ArrayList<Object> searchProductByName(String pName) {
        this.open();
        Log.d("search", "search: called "+pName);

        //String[] selection = new String[] {pName, "Android Developer"};
        Cursor cursor = null;

        cursor = mDatabase.query(mDatabaseHelper.PRODUCT_TABLE, null,
                    mDatabaseHelper.COL_PROD_NAME + " LIKE '%" + pName + "%'",
                    null, null, null, null);
/*        cursor = mDatabase.query(mDatabaseHelper.PRODUCT_TABLE, null,
                    mDatabaseHelper.COL_PROD_ID + " = '" + pName + "'",
                    null, null, null, null);*/

        ArrayList<Object> allSelectedProducts = new ArrayList<>();

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                int uid = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_ID));
                String name = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_NAME));
                double price = cursor.getDouble(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_PRICE));
                double rating = cursor.getDouble(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_RATING));
                String category = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_CATEGORY));
                int ratCount = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_RATED_COUNT));
                int revCount = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_REVIEW_COUNT));
                String img = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_IMAGE));
                String imgPath = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_IMAGE_PATH));

                allSelectedProducts.add(new Product(uid,name,price,img,imgPath,rating,category,ratCount,revCount));
                Log.d("search", "found : " + name+" id "+uid);
            } while (cursor.moveToNext());
        }
        cursor.close();
        this.close();
        return allSelectedProducts;
    }

    //TODO: update

    public boolean updateProduct(Product product) {
        this.open();
        ContentValues values = product.toValues();
        //Log.d("update", "updateEmployee: "+employee.toString());

        int updatedValue = mDatabase.update(mDatabaseHelper.PRODUCT_TABLE, values,
                mDatabaseHelper.COL_PROD_ID +"="+product.getId(),null);

        this.close();
        if (updatedValue > 0) {
            return true;
        }
        return false;

    }

    //TODO: delete
    public boolean deleteProductById(int id) {
        this.open();
        int deleteValue = mDatabase.delete(mDatabaseHelper.PRODUCT_TABLE, mDatabaseHelper.COL_PROD_ID +"="+ id, null);
        this.close();
        if (deleteValue > 0) {
            return true;
        }
        return false;
    }
    public boolean deleteAllProducts() {
        this.open();
        mDatabase.execSQL("delete from "+ mDatabaseHelper.PRODUCT_TABLE);
        this.close();
        return true;
    }
    /**
     *
     * Users Table
     *
     * */

    public boolean insertUser(User user){
        this.open();
        ContentValues values = user.toValues();

        long insertedRow = mDatabase.insert(mDatabaseHelper.USER_TABLE, null, values);
        this.close();

        if (insertedRow > 0) {
            return true;
        }
        return false;
    }

    //TODO: read
    public ArrayList<Object> getAllUsers() {
        this.open();

        // String[] columns = new String[]{mDatabaseHelper.COL_EMP_ID, mDatabaseHelper.COL_EMP_NAME, mDatabaseHelper.COL_EMP_DESIGNATION};

        //mDatabase.rawQuery("select * from "+mDatabaseHelper.EMP_TABLE, null);
       /* mDatabase.query(mDatabaseHelper.EMP_TABLE, columns , null,
                null,null,null,null); */

        Cursor cursor = mDatabase.query(mDatabaseHelper.USER_TABLE, null, null,
                null,null,null,null);

        ArrayList<Object> allUsers = new ArrayList<>();

        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_USR_ID));
                String name = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_USR_NAME));
                String pass = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_USR_PASS));
                String phone = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_USR_PHONE));
                String mail = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_USR_MAIL));
                int ratCount = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_USR_FAV_CONT));
                int revCount = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_USR_REV_COUNT));

                String img = null;
                String imgPath = null;
                try {
                    img = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_USR_IMG));
                    imgPath = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_IMAGE_PATH));
                }catch (Exception e) {
                    e.printStackTrace();
                }

                allUsers.add(new User(id,name,mail,pass,phone,img,imgPath,ratCount,revCount));

                Log.d("all", "Employee ID: " + id);
            } while (cursor.moveToNext());

        }
        cursor.close();
        this.close();
        return allUsers;
    }
    public User getUserById(int id,String[] columns) {
        this.open();
        Cursor cursor = mDatabase.query(mDatabaseHelper.USER_TABLE, columns,
                mDatabaseHelper.COL_USR_ID +"="+id,null,null,null,null);

        User user = null;
        if (cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            int uid = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_USR_ID));
            String name = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_USR_NAME));
            String phone = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_USR_PHONE));
            String pass = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_USR_PASS));
            String mail = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_USR_MAIL));
            int ratCount = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_USR_FAV_CONT));
            int revCount = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_USR_REV_COUNT));
            String img = null;
            String imgPath = null;

            try {
                img = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_USR_IMG));
                imgPath = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_USR_IMG_PATH));
            } catch (Exception e) {
                e.printStackTrace();
            }

            user = new User(uid,name,mail,pass,phone,img,imgPath,ratCount,revCount);
            Log.d("upload", "getUserById: "+user.toString());
        }

        cursor.close();
        this.close();

        return user;
    }

    public User getUserByMainAndPass(String mail, String pass) {
        this.open();
        Cursor cursor = mDatabase.query(mDatabaseHelper.USER_TABLE, null,
                mDatabaseHelper.COL_USR_MAIL +"=? AND "+mDatabaseHelper.COL_USR_PASS+"=?",
                new String[] {mail, pass},null,null,null);

        User user = null;
        if (cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            int uid = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_USR_ID));
            String name = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_USR_NAME));
            String phone = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_USR_PHONE));
            int ratCount = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_USR_FAV_CONT));
            int revCount = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_USR_REV_COUNT));
            String passw = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_USR_PASS));
            String img = null;
            String imgPath = null;
            Log.d("login", "getUserByMainAndPass: "+uid);
            try {
                img = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_USR_IMG));
                imgPath = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_USR_IMG_PATH));
            }catch (Exception e) {
                e.printStackTrace();
            }

            Log.d("login", "getUserByMainAndPass: "+uid);
            user = new User(uid,name,mail,passw,phone,img,imgPath,ratCount,revCount);
        }
        Log.d("login", "getUserByMainAndPass: "+(cursor == null)+" and "+mail+" "+pass+" "+(user== null));
        cursor.close();
        this.close();

        return user;
    }

    public ArrayList<Object> searchUserByName(String pName) {
        this.open();
        Log.d("search", "searchUserByName: called "+pName);

        //String[] selection = new String[] {pName, "Android Developer"};
        Cursor cursor = null;

        cursor = mDatabase.query(mDatabaseHelper.USER_TABLE, null,
                mDatabaseHelper.COL_USR_NAME + " LIKE '%" + pName + "%'",
                null, null, null, null);

        ArrayList<Object> allSelectedUsers = new ArrayList<>();

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                int id = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_USR_ID));
                String name = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_USR_NAME));
                String pass = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_USR_PASS));
                String phone = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_USR_PHONE));
                String mail = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_USR_MAIL));
                int ratCount = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_USR_FAV_CONT));
                int revCount = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_USR_REV_COUNT));
                String img = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_USR_IMG));
                String imgPath = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_IMAGE_PATH));

                allSelectedUsers.add(new User(id,name,mail,pass,phone,img,imgPath,ratCount,revCount));
                Log.d("search", "searchEmployeeByName: " + name);
            } while (cursor.moveToNext());
        }
        cursor.close();
        this.close();
        return allSelectedUsers;
    }

    //TODO: update

    public boolean updateUser(User user) {
        this.open();
        ContentValues values = user.toValues();
        Log.d("update", "updateData: "+user.toString());

        int updatedValue = mDatabase.update(mDatabaseHelper.USER_TABLE, values,
                mDatabaseHelper.COL_USR_ID +"="+user.getId(),null);

        this.close();
        if (updatedValue > 0) {
            return true;
        }
        return false;

    }

    //TODO: delete
    public boolean deleteUserById(int id) {
        this.open();
        int deleteValue = mDatabase.delete(mDatabaseHelper.USER_TABLE, mDatabaseHelper.COL_USR_ID +"="+ id, null);
        this.close();
        if (deleteValue > 0) {
            return true;
        }
        return false;
    }

    /**
     *
     * Category Table
     *
     * */

    //TODO: insert
    public boolean insertCategory(Category category){
        this.open();
        ContentValues values = new ContentValues();
        values.put(mDatabaseHelper.COL_CAT_NAME, category.getCategoryName());

        long insertedRow = mDatabase.insert(mDatabaseHelper.CATEGORY_TABLE, null, values);
        this.close();

        if (insertedRow > 0) {
            return true;
        }
        return false;
    }
    //TODO: read
    public ArrayList<Category> getAllCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        this.open();
        Cursor cursor = null;

        cursor = mDatabase.query(mDatabaseHelper.CATEGORY_TABLE, null,
                null,
                null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                int id = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_CAT_ID));
                String name = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_CAT_NAME));

                categories.add(new Category(id,name));
            } while (cursor.moveToNext());
        }
        cursor.close();
        this.close();
        return categories;

    }

    public boolean isExistsCategory(String name) {

        this.open();
        Cursor cursor = null;

        cursor = mDatabase.query(mDatabaseHelper.CATEGORY_TABLE, null,
                mDatabaseHelper.COL_CAT_NAME +"=?",
                new String[] {name},
                null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            return true;

        }
        cursor.close();
        this.close();
        return false;

    }
    /**
     *
     * Favorite Table
     *
     * */

    //TODO: insert
    public boolean insertFavorite(Favorite favorite){
        this.open();
        ContentValues values = new ContentValues();
        values.put(mDatabaseHelper.COL_FAV_PROD_ID, favorite.getPid());
        values.put(mDatabaseHelper.COL_FAV_USER_ID, favorite.getUid());

        long insertedRow = mDatabase.insert(mDatabaseHelper.FAVORITE_TABLE, null, values);
        this.close();

        if (insertedRow > 0) {
            return true;
        }
        return false;
    }

    public boolean isFavorite(int uid, int pid){
        this.open();

        Cursor cursor = mDatabase.query(mDatabaseHelper.FAVORITE_TABLE,null,
                mDatabaseHelper.COL_FAV_USER_ID +"=? AND "+mDatabaseHelper.COL_FAV_PROD_ID +"=?",
                new String[] {String.valueOf(uid), String.valueOf(pid)},
            null,null,null,null);

        boolean isFav = false;
        if (cursor != null && cursor.getCount() > 0) {
            isFav = true;
        }
        cursor.close();
        this.close();
        return isFav;
    }

    public boolean removFavorite(int uid, int pid){
        this.open();

        long deletedRow = mDatabase.delete(mDatabaseHelper.FAVORITE_TABLE,
                mDatabaseHelper.COL_FAV_USER_ID +"=? AND "+mDatabaseHelper.COL_FAV_PROD_ID +"=?"
                , new String[] {String.valueOf(uid), String.valueOf(pid)});
        this.close();

        if (deletedRow > 0) {
            return true;
        }
        return false;
    }
    //TODO: read
    public ArrayList<Product> getAllFavoriteProducts(int id) {
        ArrayList<Product> favProducts = new ArrayList<>();
        this.open();
        Cursor cursor = null;

        String[] columns = new String[] {
                mDatabaseHelper.COL_PROD_ID,
                mDatabaseHelper.COL_PROD_NAME,
                mDatabaseHelper.COL_PROD_PRICE,
                mDatabaseHelper.COL_PROD_RATING,
                mDatabaseHelper.COL_PROD_RATED_COUNT,
                mDatabaseHelper.COL_PROD_REVIEW_COUNT,
                mDatabaseHelper.COL_PROD_IMAGE,
                mDatabaseHelper.COL_PROD_IMAGE_PATH
        };
        String SELECT_QUERY = "SELECT * FROM "+ mDatabaseHelper.PRODUCT_TABLE +
                " t1 INNER JOIN "+ mDatabaseHelper.FAVORITE_TABLE +" t2 ON t1.id = t2.pid and t2.uid ="+ id;

        String query = "SELECT * FROM  "+ mDatabaseHelper.PRODUCT_TABLE +","+mDatabaseHelper.FAVORITE_TABLE  +
                "WHERE Table1.id = Table2.pid  AND Table2.uid =" +id;

       /* cursor = mDatabase.query(mDatabaseHelper.PRODUCT_TABLE, columns,
                mDatabaseHelper.COL_PROD_ID + "=?" + " AND " + mDatabaseHelper.COL_FAV_USER_ID + "=?",
                new String[] {mDatabaseHelper.COL_FAV_PROD_ID, String.valueOf(id)},
                null, null, null, null);*/

       cursor = mDatabase.rawQuery(query,null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                Log.d("FAV", "pid: "+cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_ID)));

            } while (cursor.moveToNext());
        }
        cursor.close();
        this.close();
        return favProducts;

    }

    /**
     *
     * REVIEW Table
     * */

    public boolean insertReview(Review review){
        this.open();
        Log.d("review", "insertReview: "+review.toString());

        ContentValues values = new ContentValues();
        values.put(mDatabaseHelper.COL_REV_USER_ID, review.getUser_id());
        values.put(mDatabaseHelper.COL_REV_PROD_ID, review.getProduct_id());
        values.put(mDatabaseHelper.COL_REV_COMMENT, review.getComment());

        long insertedRow = mDatabase.insert(mDatabaseHelper.REVIEW_TABLE, null, values);
        this.close();

        if (insertedRow > 0) {
            return true;
        }
        return false;
    }

    public ArrayList<Review> getReviewById(int id) {
        ArrayList<Review> reviews = new ArrayList<>();
        this.open();
        Cursor cursor = null;
        Log.d("review", "getReviewById: called from product "+id);


       /* String SELECT_QUERY = "SELECT rev_comment,user_name FROM "+ mDatabaseHelper.REVIEW_TABLE +", "+mDatabaseHelper.PRODUCT_TABLE+", "+
            mDatabaseHelper.USER_TABLE +" where rev_prod_id = prod_id and rev_user_id = user_id";

        String query = "SELECT * FROM  "+ mDatabaseHelper.PRODUCT_TABLE +","+mDatabaseHelper.FAVORITE_TABLE  +
                "WHERE Table1.id = Table2.pid  AND Table2.uid =" +id;
*/
        cursor = mDatabase.query(mDatabaseHelper.REVIEW_TABLE, null,
                mDatabaseHelper.COL_REV_PROD_ID + "="+id,
               null,
                null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
               // Log.d("review", "pid: "+cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_PROD_ID)));
                int rid = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_REV_ID));
                int uid = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_REV_USER_ID));
                int pid = cursor.getInt(cursor.getColumnIndex(mDatabaseHelper.COL_REV_PROD_ID));
                String comment = cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_REV_COMMENT));
                reviews.add(new Review(rid,uid,pid,comment));
                Log.d("review", "getReviewById: "+uid);

            } while (cursor.moveToNext());
        }
        cursor.close();
        this.close();
        return reviews;

    }
}
