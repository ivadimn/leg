import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainClass {
    private static  int mCount = 0;
    private static final int FLOORS = 10;
    private static final int THREAD_COUNT = 4;
    private static final int  PEOPLE_ON_FLOOR= 100;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        Lock lock = new ReentrantLock();
        for (int i = 0; i < FLOORS; i++) {
            Future<Integer> future = executorService.submit(new CountCallable());

            try {
                lock.lock();
                mCount += future.get();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            finally {
                lock.unlock();
            }

        }
        executorService.shutdown();
        System.out.println("Всего - " + mCount);
    }

    public static class CountCallable implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            Integer count = 0;
            for (int i = 0; i < PEOPLE_ON_FLOOR; i++) {
                count++;
            }
            System.out.println(Thread.currentThread().getName());
            return count;
        }
    }
}
