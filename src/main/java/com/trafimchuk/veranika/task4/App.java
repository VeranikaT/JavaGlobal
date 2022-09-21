package com.trafimchuk.veranika.task4;

public class App {

    public static void main(String[] args) throws InterruptedException {

        BlockingObjectPool pool = new BlockingObjectPool(10);

        System.out.println(pool.get());
        System.out.println(pool.get());
        System.out.println(pool.get());

        Object obj = new Object();
        pool.take(obj);
        System.out.println(obj);
        System.out.println(pool.get());
        System.out.println(pool.get());
        System.out.println(pool.get());

    }
}
