package com.ly;

import java.util.ArrayDeque;
import java.util.Date;
import java.util.Deque;
import java.util.concurrent.TimeUnit;

/**
 * Created by Lo__oY on 2017/3/31.
 */
public class DaemonThread {

    public static void main(String args[]){


        Deque<Event> deque = new ArrayDeque<>();
        WriteTask writeTask = new WriteTask(deque);
        Thread[] threads = new Thread[3];

        for (int i = 0; i <3 ; i++) {
            threads[i] = new Thread(writeTask);
            threads[i].start();
        }


        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CleanerTask cleanerTask = new CleanerTask(deque);
        cleanerTask.start();






    }
}


/*
    事件
 */
class Event{

    private Date date;
    private String event;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}

/*
    写线程
 */
class WriteTask implements Runnable{

    private Deque<Event> deque;

    public WriteTask(Deque<Event> deque){

        this.deque = deque;
    }

    @Override
    public void run() {

        for (int i = 0; i < 100; i++) {
            Event event = new Event();
            event.setDate(new Date());
            event.setEvent(String.format("the thread %s has generated an event %d",Thread.currentThread().getId(),deque.size()));
            deque.addFirst(event);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class CleanerTask extends Thread{

    private  Deque<Event> deque;
    public CleanerTask(Deque<Event> deque){

        this.deque = deque;
        setDaemon(true);

    }

    @Override
    public void run() {
       while (true){
           Date date = new Date();
           clean(date);
       }
    }

    private void clean(Date date){
        long difference;
        boolean delete;

        if(deque.size() == 0)
            return;

        delete = false;
        do{
            Event event = deque.getLast();

            difference = date.getTime() - event.getDate().getTime();

            if(difference > 10000){
                System.out.println("Cleaner:" + event.getEvent());
                deque.removeLast();
                delete = true;
            }
        }while(difference > 10000);

        if(delete)
            System.out.println("size of the dequen="+deque.size());
    }
}