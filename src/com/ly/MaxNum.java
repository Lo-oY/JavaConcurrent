package com.ly;

import java.util.Scanner;

/**
 * Created by Lo__oY on 2017/4/9.
 */
public class MaxNum {


    //求两个数的最大公约数
   static int gcd(int x,int y)
    {
        int a;
        if(x<y){  a= x;x=y;y=a ;}
        while (y!=0)
        {
            a=x%y;
            x=y;
            y=a;
        }
        return x;
    }
    public static void main(String args[])
    {
        int t;
        int n;
        int temp,end;
        Scanner sc = new Scanner(System.in);


            n = sc.nextInt();
            t = sc.nextInt();
            for (int i=0;i<n-1;i++)
            {
                temp = sc.nextInt();
                t=gcd(t,temp);
            }
        System.out.println(t);


    }
}
