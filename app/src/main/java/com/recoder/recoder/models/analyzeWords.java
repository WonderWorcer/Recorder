package com.recoder.recoder.models;

import java.util.ArrayList;


/**
 * Created by WonderWorcer on 29.04.2017.
 */

public class analyzeWords {
    private String word;
    private Integer frequency;
    private ArrayList<recordMin> listOfRecords;




    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public ArrayList<recordMin> getListOfRecords() {
        return listOfRecords;
    }

    public void setListOfRecords(ArrayList<recordMin> listOfRecords) {
        this.listOfRecords = listOfRecords;
    }
}
