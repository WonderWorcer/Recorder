package com.recoder.recoder.models;

import java.util.ArrayList;


/**
 * \brief Регистрация звонков.
 * \author WonderWorcer
 * \version 1.0
 * \date 5 мая 2017
 * <p>
 * Класс, хранящий информацию о записях, подлежающих
 * анализу и уже проанализированных
 */

public class analyzeRecords {
    private String name;///<Имя контакта или номер телефона
    private Integer priority;///<Взвешенный приоритет записи
    private ArrayList<words> listOfWords;///<Список слов, содержащихся в записи

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
