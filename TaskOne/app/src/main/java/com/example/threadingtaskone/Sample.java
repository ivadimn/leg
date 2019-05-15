package com.example.threadingtaskone;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Sample {
    private static int mCount = 0;
    static Lock lock = new ReentrantLock();
    private static Future<Integer> future;

    private static int numberOfThreads = 10;
    private static int numberOfFloors = 10;

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        final ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

        for (int i = 0; i < numberOfFloors; i++) {

            future = executorService.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {

                    lock.lock();
                    int localCount = 0;
                    for (int y = 0; y < 100; y++) {

                        localCount++;
                        System.out.println(localCount + " " + Thread.currentThread().getName());
                    }
                    mCount = mCount + localCount;
                    lock.unlock();
                    TimeUnit.MILLISECONDS.sleep(1000);

                    return mCount;
                }

            });
        }


        System.out.println("\nResult: " + future.get());
        executorService.shutdown();

    }
}