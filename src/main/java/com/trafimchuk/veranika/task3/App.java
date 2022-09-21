package com.trafimchuk.veranika.task3;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

public class App {

    static int count = 10;

    public static void main(String[] args) throws InterruptedException {

        ProducerConsumer producerConsumer = new ProducerConsumer();

        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < count; i++) {
                    producerConsumer.produce();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < count; i++){
                    producerConsumer.consume();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        producer.start();
        consumer.start();

        consumer.join();
        producer.join();
    }

    public static class ProducerConsumer {
        private Queue<Integer> messageBus = new PriorityQueue<>();
        private static int sizeMB = 10;

        public void produce() throws InterruptedException {

            synchronized (this) {
                while (messageBus.size() == sizeMB) {
                    try {
                        wait();
                    } catch (InterruptedException exception) {
                        Thread.currentThread().interrupt();
                    }
                }

                Integer value = ThreadLocalRandom.current().nextInt(10);
                System.out.println("produce " + value);
                messageBus.offer(value);

                System.out.println(messageBus);
                notifyAll();

                Thread.sleep(100);
            }
        }

        public void consume() throws InterruptedException {
            synchronized (this) {
                while (messageBus.isEmpty()) {
                    wait();
                }

                System.out.println("consume " + messageBus.poll());
                System.out.println(messageBus);
                notifyAll();

                Thread.sleep(100);
            }
        }
    }
}