package com.ly;

import java.util.Date;
import java.util.concurrent.*;

/**
 * Created by Lo__oY on 2017/6/25.
 */
public class ScheduleExecutor {

    public static void main(String args[]){

        ScheduledThreadPoolExecutor executor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(1);

        System.out.printf("Main:start at %s\n",new Date());
        for (int i = 0; i < 10 ; i++) {

            ScheduleTask task = new ScheduleTask("Task"+i);
            executor.schedule(task,i+1, TimeUnit.SECONDS);
        }

        executor.shutdown();

        try {
            executor.awaitTermination(1,TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("Main:End at %s\n",new Date());
    }
}


class ScheduleTask implements Callable<String>{

    private String name;
    public ScheduleTask(String name) {
        this.name = name;
    }

    @Override
    public String call() throws Exception {

        System.out.printf("%s start at %s\n",name,new Date(System.currentTimeMillis()));
        return "Hello World";
    }
}