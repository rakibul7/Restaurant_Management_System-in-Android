package com.example.mahbuburrahman.resturantmanagement.model;

/**
 * Created by Mahbuburrahman on 12/22/17.
 */

public class Review {

    private int id;
    private int user_id;
    private int product_id;
    private String comment;

    public Review(int user_id, int product_id, String comment) {
        this.user_id = user_id;
        this.product_id = product_id;
        this.comment = comment;
    }

    public Review(int id, int user_id, int product_id, String comment) {
        this.id = id;
        this.user_id = user_id;
        this.product_id = product_id;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public String getComment() {
        return comment;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", product_id=" + product_id +
                ", comment='" + comment + '\'' +
                '}';
    }
}
