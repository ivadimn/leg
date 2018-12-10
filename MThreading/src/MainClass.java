import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainClass {
    private static int mCount = 0;
    private static int mFloors = 10;
    private static int mThreads = 4;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(mThreads);
        Lock lock = new ReentrantLock();
        for (int i = 0; i < mFloors; i++) {
            Future<Integer> future = executorService.submit(new CountCallable());
<<<<<<< ef4366c76660c20f4319d3d9f7c53258e59c6465
            //while(!future.isDone()) {
            //    TimeUnit.MILLISECONDS.sleep(100);
            //}
=======
>>>>>>> update
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
            for (int i = 0; i < 100; i++) {
                count++;
            }
            System.out.println(Thread.currentThread().getName());
            return count;
        }
    }
}
