package com.example.task21;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Sample {
    private static int floors = 5;
    private static int threads = floors;
    private static int count = 0;

    public static void main(String[] args) throws Exception {

        ExecutorService service = Executors.newFixedThreadPool(threads);
        final Lock lock = new ReentrantLock();

        for(int j=0; j<floors; j++) {

            service.submit(new Runnable() {
                @Override
                public void run() {
                    int localCount = 0;
                    for (int i = 0; i < 100; i++) {
                        localCount++;
                        try {
                            TimeUnit.MILLISECONDS.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    lock.lock();
                    try {
                        count += localCount;
                    } finally {
                        lock.unlock();
                    }
                }
            });
        }

        service.shutdown();
        while (!service.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
        }

        System.out.println("Количество людей на этажах составляет " + count);
    }
}
