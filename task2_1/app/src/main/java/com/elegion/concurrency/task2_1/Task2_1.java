package com.elegion.concurrency.task2_1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Azret Magometov
 */

class House {

    private static final int SLEEP_TIME_FOR_COUNT_PEOPLE_ON_FLOOR=10;

    private final int FLOOR_COUNT;

    private int tenantOnFloorCount;
    private int threadCount;

    private int tenantInHouseCount;

    private final Lock locker=new ReentrantLock();

    class FloorPeopleCounter implements Runnable{
        private int peopleFloorCount;

        public FloorPeopleCounter(int peopleCount){
            this.peopleFloorCount=peopleCount;
        }

        @Override
        public void run() {
            int peopleCounter=0;
            for (int people = 0; people < peopleFloorCount; people++) {
                peopleCounter++;
                try {
                    TimeUnit.MILLISECONDS.sleep(SLEEP_TIME_FOR_COUNT_PEOPLE_ON_FLOOR);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println( "Thread:"+ Thread.currentThread().getName());

            locker.lock();
            tenantInHouseCount +=peopleCounter;
            locker.unlock();
        }
    }
    public House(int floorCount,int peopleOnFloorCount, int threadCount){
        FLOOR_COUNT=floorCount;
        this.tenantOnFloorCount =peopleOnFloorCount;
        this.threadCount=threadCount;
    }

    public int getTenantInHouseCount() {
        tenantInHouseCount =0;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        for (int floor = 0; floor < FLOOR_COUNT; floor++) {
                executorService.submit(new FloorPeopleCounter(tenantOnFloorCount));
            }
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination((FLOOR_COUNT * tenantOnFloorCount * SLEEP_TIME_FOR_COUNT_PEOPLE_ON_FLOOR),
                        TimeUnit.MILLISECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
            }

        return tenantInHouseCount;
    }
}

public class Task2_1{

    public static void main(String[] args) throws InterruptedException {
        int floorCount=10;
        int tenantOnFloorCount=100;
        int threadCount=2;
        House  house=new House(floorCount,tenantOnFloorCount,threadCount);
        System.out.println("All people:"+house.getTenantInHouseCount());
    }
}