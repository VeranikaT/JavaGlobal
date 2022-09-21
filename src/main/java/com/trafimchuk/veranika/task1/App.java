package com.trafimchuk.veranika.task1;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class App{

    public static void main(String[] args) throws InterruptedException {
        sum();
    }

    private static Map<Integer, Integer> map = new HashMap<>();
    private static int count = 10;

    private static void sum() throws InterruptedException {
        Lock lock = new ReentrantLock();

        Runnable runnable = () -> {
            lock.lock();
            for (int i = 0; i < count; i++) {
                map.put(i, i);
            }
            lock.unlock();

        };
        lock.lock();

        Thread thread = new Thread(runnable);
        lock.unlock();

        Runnable runnable1 = () -> {
            lock.lock();

            int sum = 0;

            for (int i = 0; i < count; i++) {
                for (Integer key : map.keySet()) {
                    sum += map.get(key);
                    System.out.println(sum);
                }
            }

            System.out.println(sum);
            lock.unlock();

        };
        lock.lock();

        Thread thread1 = new Thread(runnable1);
        lock.unlock();

        thread.start();
        thread1.start();

        thread.join();
        thread1.join();
    }
}