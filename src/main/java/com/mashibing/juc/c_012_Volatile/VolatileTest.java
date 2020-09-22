package com.mashibing.juc.c_012_Volatile;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author shao.hongxiao
 */
public class VolatileTest {
    private static int a = 100;
    private static  volatile boolean ischanged = false;
    @Test
    public  void test1() throws InterruptedException {
        for(int i=0; i<100;i++){
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    if(!ischanged){
                        ischanged = true;
                        System.out.println("Thread"+Thread.currentThread());
                        a = 120;
                    }
                }
            });
            t.start();
        }
        Thread.currentThread().join();
    }
    //AtomicIntegerFieldUpdater  必须是int,必须用volatile修饰 原子操作
    private static AtomicIntegerFieldUpdater<VolatileTest> update = AtomicIntegerFieldUpdater.newUpdater(VolatileTest.class, "b");
    private static VolatileTest test = new VolatileTest();
    public volatile int b = 0;
    @Test
    public  void test2() throws Exception {
        for(int i=0; i<100;i++){
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    int r = update.getAndIncrement(test);
                    System.out.println("已修改："+r);
                }
            });
            t.start();
        }
        Thread.currentThread().join();
    }
}
