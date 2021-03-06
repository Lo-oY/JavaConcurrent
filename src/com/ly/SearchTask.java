package com.ly;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by Lo__oY on 2017/6/13.
 */
public class SearchTask implements Runnable {

    public SearchTask(String result) {
        this.result = result;
    }

    private String result;

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println("Thread Start " + name);
        try {
            doTask();
            result = name;
        } catch (InterruptedException e) {
            System.out.printf("Thread %s: Interrupted\n", name);
            return;
        }
        System.out.println("Thread end " + name);
    }

    private void doTask() throws InterruptedException {
        Random random = new Random((new Date()).getTime());
        int value = (int) (random.nextDouble() * 50);
        System.out.printf("Thread %s: %d\n", Thread.currentThread().getName(),
                value);
        TimeUnit.SECONDS.sleep(value);
    }

    public static void main(String[] args) {
        //创建5个线程，并入group里面进行管理
        ThreadGroup threadGroup = new ThreadGroup("Searcher");
        String result = "";
        SearchTask searchTask = new SearchTask(result);
        for (int i = 0; i < 5; i++) {
            Thread thred = new Thread(threadGroup, searchTask);
            thred.start();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //通过这种方法可以看group里面的所有信息
        System.out.printf("Number of Threads: %d\n", threadGroup.activeCount());
        System.out.printf("Information about the Thread Group\n");
        threadGroup.list();

        //这样可以复制group里面的thread信息
        Thread[] threads = new Thread[threadGroup.activeCount()];
        threadGroup.enumerate(threads);
        for (int i = 0; i < threadGroup.activeCount(); i++) {
            System.out.printf("Thread %s: %s\n", threads[i].getName(),
                    threads[i].getState());
        }

        waitFinish(threadGroup);
        //将group里面的所有线程都给interpet
        threadGroup.interrupt();
    }

    private static void waitFinish(ThreadGroup threadGroup) {
        while (threadGroup.activeCount() > 4) {
            try {
                System.out.println(threadGroup.activeCount());
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
