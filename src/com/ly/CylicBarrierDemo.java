package com.ly;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by Lo__oY on 2017/6/26.
 */
public class CylicBarrierDemo {

    public static void main(String args[]){
        final int ROWS = 10000;
        final int NUMBER = 1000;
        final int SEARCH = 5;
        final int PARTICIPANTS = 5;
        final int LINES_PARTICIPANTS = 2000;

        MatrixMock matrixmock = new MatrixMock(ROWS,NUMBER,SEARCH);
        Result result = new Result(ROWS);
        Grouper grouper = new Grouper(result);
        CyclicBarrier cyclicbarrier = new CyclicBarrier(PARTICIPANTS,grouper);
        for (int i = 0; i < 5; i++) {

            new Thread(new Searcher(i*LINES_PARTICIPANTS,(i+1)*LINES_PARTICIPANTS,
                    result,matrixmock,SEARCH,cyclicbarrier)).start();        }


    }
}


class MatrixMock{

    private int data[][];

    public MatrixMock(int size,int length,int number){

        int counter = 0;
        data = new int[size][length];
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < length; j++) {
                data[i][j] = random.nextInt(10);
                if (data[i][j] == number)
                    counter++;
            }
        }

        System.out.printf("Mock:There are %d ocurrence of %d in generated data\n",counter,number);
    }

    public int[] getRow(int row){
        if(row >= 0 && row < data.length)
            return data[row];
        return null;
    }
}


class Result{

    private int data[];
    public Result(int size){

        data = new int[size];
    }

    public void setResult(int index,int counter){
        data[index] = counter;
    }

    public int[] getData(){
        return data;
    }

}


class Searcher implements Runnable{


    private int firstRow;
    private int lastRow;
    private Result result;
    private MatrixMock matrixMock;
    private int number;
    private CyclicBarrier cyclicBarrier;
    public Searcher(int firstRow, int lastRow, Result result, MatrixMock matrixMock, int number, CyclicBarrier cyclicBarrier){

        this.firstRow = firstRow;
        this.lastRow = lastRow;
        this.result = result;
        this.matrixMock = matrixMock;
        this.number = number;
        this.cyclicBarrier = cyclicBarrier;
    }


    @Override
    public void run() {

        System.out.printf("%s: Processing lines from %d to %d.\n",Thread.currentThread().getName(),firstRow,lastRow);

        for (int i = firstRow; i < lastRow; i++) {
            int[] data = matrixMock.getRow(i);
            int counter = 0;
            for (int j = 0; j < data.length; j++) {
                if (data[j] == number)
                    counter++;
            }
            result.setResult(i,counter);
        }
        System.out.printf("%s: Lines processed.\n",Thread. currentThread().getName());

        try {
            cyclicBarrier.await();
            System.out.printf("%s  -------------------process End.\n",Thread. currentThread().getName());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}

class Grouper implements Runnable{

    private Result result;
    public Grouper(Result result){
        this.result = result;

    }
    @Override
    public void run() {

        int data[] = result.getData();
        int counter = 0;
        System.out.printf("Grouper: Processing results...\n");
        for (int i = 0; i < data.length; i++) {

            counter += data[i];
        }
        System.out.printf("Grouper: Total result: %d.\n",counter);

    }
}
