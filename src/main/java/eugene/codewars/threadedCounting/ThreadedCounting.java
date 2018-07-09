package eugene.codewars.threadedCounting;

/*
    Threaded counting
    https://www.codewars.com/kata/threaded-counting/java

    Your task is simple. You have to call counter.count(int) with the numbers 1 to 100 inclusive. So a simple solution might look like this:
        for (int i = 1; i <= 100; i++) {
          counter.count(i);
        }

    But there's a catch. Your solution also has to satisfy the following conditions:
    - Three different threads must be used
    - Numbers of the form 3n (multiples of 3) must be called in one thread
    - Numbers of the form 3n + 1 must be called in a second
    - Numbers of the form 3n + 2 must be called in a third
    - The numbers have to be called in sequence 1 to 100

    Also, make sure your method doesn't return until all three threads have completed.
    Otherwise the tests may not work even if your solution is correct.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadedCounting {

    private static final int THREAD_COUNT = 3;
    private static final int MAX_VALUE = 100;

    private static final Semaphore lock = new Semaphore(1, true);
    private static AtomicInteger count;

    public static void countInThreads(Counter counter) {
        count = new AtomicInteger(0);

        List<Thread> threadList = new ArrayList<>(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            final int mod = i;
            threadList.add(new Thread(() -> counterRoutine(counter, mod)));
        }

        for (Thread thread : threadList) {
            thread.start();
        }

        for (Thread thread : threadList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void counterRoutine(Counter counter, int mod) {
        while (true) {
            try {
                lock.acquire();

                if (count.get() >= MAX_VALUE) {
                    return;
                }

                if (count.get() % THREAD_COUNT == mod) {
                    counter.count(count.incrementAndGet());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.release();
            }
        }
    }
}