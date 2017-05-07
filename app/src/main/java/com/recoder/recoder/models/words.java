package com.recoder.recoder.models;

/**
 * Created by WonderWorcer on 29.04.2017.
 */

public class words {
    private String word;
    private Integer priority;
    private String _id;
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
