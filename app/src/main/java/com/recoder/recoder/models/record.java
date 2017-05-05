package com.recoder.recoder.models;


/**
 * Created by WonderWorcer on 29.04.2017.
 */
public class record {
    private String picture;
    private String number;
    private String time;
    private boolean ishod = false;
    private String time_call;


    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
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
