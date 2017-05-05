package com.recoder.recoder.Recognizer;


import java.util.ArrayList;
import java.util.List;

/*************************************************************************************
 * Класс, который содержит ответ и приватность от запроса распознования речи Google.
 *
 * @author WonderWorcer
 ************************************************************************************/
public class GoogleResponse {
    /**
     * Переменная, содержащая ответ запроса
     */
    private String response;
    /**
     * Переменная, содержащая доверительный балл
     */
    private String confidence;

    /**
     * Список, содержащий другие ответы от запроса
     */
    private List<String> otherPossibleResponses = new ArrayList<String>(20);


    private boolean finalResponse = true;

    /**
     * Констуктор
     */
    public GoogleResponse() {

    }


    /**
     * Получает текст, полученый из аудио-файла через Google
     *
     * @return Строковое представление аудиофайла
     */
    public String getResponse() {
        return response;
    }

    /**
     * Set the response
     *
     * @param response The response
     */
    protected void setResponse(String response) {
        this.response = response;
    }

    /**
     * Получает доверительную оценку для конкретного запроса.
     *
     * @return Показатель достоверности, например. 922343324323
     */
    public String getConfidence() {
        return confidence;
    }

    /**
     * Установливает доверительный балл для этого запроса
     *
     * @param confidence Показатель достоверности
     */
    protected void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    /**
     * Получает список, содержащий другие ответы от запроса
     *
     * @return Список других ответов
     */
    public List<String> getOtherPossibleResponses() {
        return otherPossibleResponses;
    }

    /**
     * Получает все возвращенные ответы для этого запроса
     *
     * @return Список всех ответов
     */
    public List<String> getAllPossibleResponses() {
        List<String> tmp = otherPossibleResponses;
        tmp.add(0, response);
        return tmp;
    }

    public boolean isFinalResponse() {
        return finalResponse;
    }

    public void setFinalResponse(boolean finalResponse) {
        this.finalResponse = finalResponse;
    }
}
