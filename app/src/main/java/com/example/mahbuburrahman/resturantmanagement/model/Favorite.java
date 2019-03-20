package com.example.mahbuburrahman.resturantmanagement.model;

/**
 * Created by Mahbuburrahman on 12/22/17.
 */

public class Favorite {
    private int id;
    private int uid;
    private int pid;

    public Favorite(int uid, int pid) {
        this.uid = uid;
        this.pid = pid;
    }

    public Favorite(int id, int uid, int pid) {
        this.id = id;
        this.uid = uid;
        this.pid = pid;
    }

    public int getId() {
        return id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }
}
