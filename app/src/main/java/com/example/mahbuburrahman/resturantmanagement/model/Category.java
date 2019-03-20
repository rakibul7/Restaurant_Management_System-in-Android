package com.example.mahbuburrahman.resturantmanagement.model;

/**
 * Created by Mahbuburrahman on 12/22/17.
 */

public class Category {

    private int id;
    private String categoryName;

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public Category(int id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
