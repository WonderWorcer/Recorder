package com.recoder.recoder.models;

import java.util.ArrayList;

/**
 * Created by JoshRonaldMine on 30.04.2017.
 */

public class analyzeRecords {
    private String name;
    private Integer priority;
    private ArrayList<words> listOfWords;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public ArrayList<words> getListOfWords() {
        return listOfWords;
    }

    public void setListOfWords(ArrayList<words> listOfWords) {
        this.listOfWords = listOfWords;
    }
}
