package com.trafimchuk.veranika.task4;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.IntStream.range;

public class BlockingObjectPool {
    List<Object> pool = new ArrayList<>();
    int size = 10;
    public BlockingObjectPool(int size) {
        synchronized (pool) {
            range(0, size)
                    .forEach(i -> pool.add(new Object()));
        }
    }

    public synchronized Object get() throws InterruptedException {
        while (pool.isEmpty()) {
            this.wait();
        }
        Object object = pool.remove(0);
        notifyAll();
        return object;

    }

    public synchronized void take(Object object) throws InterruptedException {
        while (pool.size() >= size) {
            wait();
        }
        pool.add(object);
        notifyAll();
    }
}