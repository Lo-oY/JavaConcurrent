package com.ly;

import com.sun.org.apache.xpath.internal.functions.FuncTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by Lo__oY on 2017/6/25.
 */
public class FactorialCalculator implements Callable<Integer>{

    private Integer num;

    public FactorialCalculator(int num){

        this.num = num;
    }


    @Override
    public Integer call() throws Exception {

        int result = 1;
        if(num == 0 || num == 1)
            result = 1;
        else {
            for (int i = 2; i <= num; i++) {
                result *= i;
                TimeUnit.MILLISECONDS.sleep(20);
            }
        }

        System.out.printf("%s:%d\n",Thread.currentThread().getName(),result);
        return result;
    }

    public  static void main(String args[]) {


        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

        List<Future<Integer>> list = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            Integer num = random.nextInt(10);
            FactorialCalculator calcu = new FactorialCalculator(num);
            Future<Integer> future = executor.submit(calcu);
            list.add(future);
        }

        do{
            System.out.printf("Main:Num of  Completed Task:%d\n",executor.getCompletedTaskCount());

            for (int i = 0 ; i < list.size() ; i++ ){

                Future<Integer> result = list.get(i);
                System.out.printf("Main:Task %d : %s\n",i,result.isDone());

            }

            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }while (executor.getCompletedTaskCount() < list.size());

        System.out.printf("Main :Results\n");

        for (int i = 0; i < list.size(); i++) {
             Future<Integer> result = list.get(i);
            Integer num = null;
            try{
                num = result.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            System.out.printf("Main:Task %d:%d\n",i,num);
        }


        executor.shutdown();
    }


}


