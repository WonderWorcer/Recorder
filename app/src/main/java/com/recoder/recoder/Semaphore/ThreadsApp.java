package com.recoder.recoder.Semaphore;

import com.recoder.recoder.App;

import java.util.concurrent.Semaphore;

/**
 * \brief Регистрация звонков.
 * \author WonderWorcer
 * \version 1.0
 * \date 07 мая 2017
 * <p>
 * Класс, реализующий инициализацию изолированного потока для отправки аудио-файла на сервер.
 */

public class ThreadsApp {
    /**
     * Метод, выполняющий инициализацию изолированного потока
     */
    public void threadController() {

        Semaphore sem = new Semaphore(1);
        App.setSemaphore(sem);
        new Responser().start();

    }
}
