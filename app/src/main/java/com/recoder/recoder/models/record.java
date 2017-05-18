package com.recoder.recoder.models;


import android.graphics.Bitmap;


/**
 * Created by WonderWorcer on 29.04.2017.
 */
public class record {
    private Bitmap picture;
    private String number;
    private String time;
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private boolean ishod = false;
    private String time_call;
    private int _id;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }


    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean ishod() {
        return ishod;
    }

    public void setIshod(boolean ishod) {
        this.ishod = ishod;
    }

    public String getTime_call() {
        return time_call;
    }

    public void setTime_call(String time_call) {
        this.time_call = time_call;
    }
}
