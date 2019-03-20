package com.example.mahbuburrahman.resturantmanagement.model;

import android.content.ContentValues;

import com.example.mahbuburrahman.resturantmanagement.database.DatabaseHelper;

/**
 * Created by Mahbuburrahman on 12/22/17.
 */

public class Product {

    private int id;
    private String itemName;
    private double itemPrice;
    private String itemImage;
    private String itemImagePath;
    private double rating;
    private String category;
    private int numOfPeopleRated;
    private int getNumOfPeopleReview;

    public Product(String itemName, double itemPrice, String itemImage, String itemImagePath, double rating, String category) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemImage = itemImage;
        this.itemImagePath = itemImagePath;
        this.rating = rating;
        this.category = category;
        this.numOfPeopleRated = 0;
        this.getNumOfPeopleReview = 0;
    }

    public Product(int id, String itemName, double itemPrice, String itemImage, String itemImagePath, double rating, String category, int numOfPeopleRated, int getNumOfPeopleReview) {
        this.id = id;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemImage = itemImage;
        this.itemImagePath = itemImagePath;
        this.rating = rating;
        this.category = category;
        this.numOfPeopleRated = numOfPeopleRated;
        this.getNumOfPeopleReview = getNumOfPeopleReview;
    }

    public String getItemImagePath() {
        return itemImagePath;
    }

    public void setItemImagePath(String itemImagePath) {
        this.itemImagePath = itemImagePath;
    }

    public int getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getNumOfPeopleRated() {
        return numOfPeopleRated;
    }

    public void setNumOfPeopleRated(int numOfPeopleRated) {
        this.numOfPeopleRated = numOfPeopleRated;
    }

    public int getGetNumOfPeopleReview() {
        return getNumOfPeopleReview;
    }

    public void setGetNumOfPeopleReview(int getNumOfPeopleReview) {
        this.getNumOfPeopleReview = getNumOfPeopleReview;
    }
    //TODO: convert to content value
    public ContentValues toValues(){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_PROD_NAME, getItemName());
        values.put(DatabaseHelper.COL_PROD_PRICE,getItemPrice());
        values.put(DatabaseHelper.COL_PROD_CATEGORY, getCategory());
        values.put(DatabaseHelper.COL_PROD_RATING, getRating());
        values.put(DatabaseHelper.COL_PROD_IMAGE, getItemImage());
        values.put(DatabaseHelper.COL_PROD_IMAGE_PATH, getItemImagePath());
        values.put(DatabaseHelper.COL_PROD_RATED_COUNT, getNumOfPeopleRated());
        values.put(DatabaseHelper.COL_PROD_REVIEW_COUNT, getGetNumOfPeopleReview());
        return values;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", itemPrice=" + itemPrice +
                ", itemImage='" + itemImage + '\'' +
                ", itemImagePath='" + itemImagePath + '\'' +
                '}';
    }
}
