package com.ly;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Lo__oY on 2017/4/1
 *
 * 测试线程池.
 */
public class Server {
    private ThreadPoolExecutor executor;

    public Server(){
        executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    }

    public void executeTask(Task task) {
        System.out.printf("Server: A new task has arrived\n");

        executor.execute(task);

        System.out.printf("Server: Pool Size: %d\n", executor.getPoolSize());
        System.out.printf("Server: Active Count: %d\n", executor.getActiveCount());
        System.out.printf("Server: Completed Tasks: %d\n", executor.getCompletedTaskCount());
    }

    public void endServer() {
        executor.shutdown();
    }
    public static void main(String[] args) {
        Server server=new Server();
        for (int i=0; i<100; i++){
            Task task=new Task("MyTask "+i);
            server.executeTask(task);
        }
        server.endServer();
    }

}


class Task implements Runnable{

    private Date date;
    private String name;

    public Task(String name){
        this.name = name;
        this.date = new Date();
    }



    @Override
    public void run() {

        System.out.printf("%s: MyTask %s: Created on: %s\n",Thread.currentThread().getName(),name,date);
        System.out.printf("%s: MyTask %s: Started on: %s\n",Thread.currentThread().getName(),name,new Date());

        try {
            Long duration=(long)(Math.random()*10);
            System.out.printf("%s: MyTask %s: Doing a task during %dseconds\n",Thread.currentThread().getName(),name,duration);
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("%s: MyTask %s: Finished on: %s\n",Thread.currentThread().getName(),name,new Date());
    }
}
