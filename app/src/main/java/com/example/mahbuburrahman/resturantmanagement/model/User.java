package com.example.mahbuburrahman.resturantmanagement.model;

import android.content.ContentValues;

import com.example.mahbuburrahman.resturantmanagement.database.DatabaseHelper;

/**
 * Created by Mahbuburrahman on 12/22/17.
 */

public class User {
    private int id;
    private String userName;
    private String userMail;
    private String userPassword;
    private String userPhone;
    private String userImage;
    private String userImagePath;
    private int fevCount;
    private int revCount;
    private int imageResourceID;

    public User(String userName, String userMail, String userPassword, String userPhone, String userImage, String userImagePath, int fevCount, int revCount) {
        this.userName = userName;
        this.userMail = userMail;
        this.userPassword = userPassword;
        this.userPhone = userPhone;
        this.userImage = userImage;
        this.userImagePath = userImagePath;
        this.fevCount = fevCount;
        this.revCount = revCount;
    }

    public User(int id, String userName, String userMail, String userPassword, String userPhone, String userImage, String userImagePath, int fevCount, int revCount) {
        this.id = id;
        this.userName = userName;
        this.userMail = userMail;
        this.userPassword = userPassword;
        this.userPhone = userPhone;
        this.userImage = userImage;
        this.userImagePath = userImagePath;
        this.fevCount = fevCount;
        this.revCount = revCount;
    }

    public int getImageResourceID() {
        return imageResourceID;
    }

    public void setImageResourceID(int imageResourceID) {
        this.imageResourceID = imageResourceID;
    }

    public void setFevCount(int fevCount) {
        this.fevCount = fevCount;
    }

    public void setRevCount(int revCount) {
        this.revCount = revCount;
    }

    public int getFevCount() {
        return fevCount;
    }

    public int getRevCount() {
        return revCount;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserMail() {
        return userMail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getUserImage() {
        return userImage;
    }

    public String getUserImagePath() {
        return userImagePath;
    }



    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public void setUserImagePath(String userImagePath) {
        this.userImagePath = userImagePath;
    }


    //TODO: convert to content value
    public ContentValues toValues(){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_USR_NAME, getUserName());
        values.put(DatabaseHelper.COL_USR_PASS,getUserPassword());
        values.put(DatabaseHelper.COL_USR_MAIL, getUserMail());
        values.put(DatabaseHelper.COL_USR_PHONE, getUserPhone());
        values.put(DatabaseHelper.COL_USR_IMG, getUserImage());
        values.put(DatabaseHelper.COL_USR_IMG_PATH, getUserImagePath());
        values.put(DatabaseHelper.COL_USR_FAV_CONT, getFevCount());
        values.put(DatabaseHelper.COL_USR_REV_COUNT, getRevCount());
        return values;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userMail='" + userMail + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userImage='" + userImage + '\'' +
                ", userImagePath='" + userImagePath + '\'' +
                '}';
    }
}
