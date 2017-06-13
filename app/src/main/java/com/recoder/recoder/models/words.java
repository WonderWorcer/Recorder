package com.recoder.recoder.models;

/**
 * \brief Регистрация звонков.
 * \author WonderWorcer
 * \version 1.0
 * \date 5 мая 2017
 * <p>
 * Класс, хранящий информацию о словах
 */

public class words {
    private String word;///<Название слова
    private Integer priority;///<Приоритет слова
    private String _id; ///<Уникальный номер слова
    private String filterName;///<Название фильтра, к которому принадлежит слово
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
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
