package com.mashibing.juc.c_012_Volatile;

import java.util.concurrent.CountDownLatch;

/**
 * @author shao.hongxiao
 */
public class VolatileTest2 {
    public static volatile int race = 0;

    private static final int THREADS_COUNT = 20;

    private static CountDownLatch countDownLatch = new CountDownLatch(THREADS_COUNT);

    public static void increase() {
        race++;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[THREADS_COUNT];
        for (int i = 0; i < THREADS_COUNT; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10000; i++) {
                        increase();
                    }
                    countDownLatch.countDown();
                }
            });
            threads[i].start();
        }
        countDownLatch.await();
        System.out.println(race);
    }
}
