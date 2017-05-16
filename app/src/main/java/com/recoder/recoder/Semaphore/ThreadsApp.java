package com.recoder.recoder.Semaphore;

import com.recoder.recoder.App;

import java.util.concurrent.Semaphore;

/**
 * Created by Роман on 07.05.2017.
 */

public class ThreadsApp {

    public void threadController() {

        Semaphore sem = new Semaphore(1);
        App.setSemaphore(sem);
        new Responser().start();

    }
}
