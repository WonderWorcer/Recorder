package com.recoder.recoder.models;

import java.util.ArrayList;


/**
 * \brief Регистрация звонков.
 * \author WonderWorcer
 * \version 1.0
 * \date 5 мая 2017
 * <p>
 * Класс, хранящий информацию о словах в записях
 */

public class analyzeWords {
    private String word; ///< Название слова
    private Integer frequency;///<Частота встречи слова
    private ArrayList<recordMin> listOfRecords;///<Список записей, в которых было найдено слово




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
