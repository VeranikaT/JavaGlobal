package com.trafimchuk.veranika.task2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class App {

    public static void main(String[] args) throws InterruptedException {
        run();
    }

    private static List<Integer> list = new ArrayList<>();
    private static Random random = new Random();
    private static Lock lock = new ReentrantLock();

    private static void run() throws InterruptedException {

        Thread thread1 = new Thread(() -> {
            lock.lock();
            list.add(random.nextInt(100));
            lock.unlock();
        });

        Thread thread2 = new Thread(() -> {
            lock.lock();
            int sum = 0;
            for (Integer integer : list) {
                sum += integer;
            }
            System.out.println(sum);
            lock.unlock();
        });

        Thread thread3 = new Thread(() -> {
            lock.lock();
            int sum = 0;

            for (Integer integer : list) {
                sum += Math.pow(integer, 2);
            }
            System.out.println(sum);
            lock.unlock();
        });

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();
    }
}