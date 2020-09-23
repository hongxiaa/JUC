/**
 * reentrantlock用于替代synchronized
 * 条件锁
 *
 * @author mashibing
 */
package com.mashibing.juc.c_020_reentranlock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class T05_ReentrantLock6{

    final Lock lock = new ReentrantLock();
    final Condition put = lock.newCondition();
    final Condition take = lock.newCondition();

    final Object[] items = new Object[100];
    int putptr, takeptr, count;

    public void put(Object x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                System.out.println("current id-put:"+Thread.currentThread().getId());
                put.await();
            }
            items[putptr] = x;
            if (++putptr == items.length) {
                putptr = 0;
            }
            ++count;
            System.out.println("current id-put:"+Thread.currentThread().getId());
            take.signal();
        } finally {
            lock.unlock();
        }
    }

    public Object take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                System.out.println("current id-take:"+Thread.currentThread().getId());
                take.await();
            }
            Object x = items[takeptr];
            if (++takeptr == items.length) {
                takeptr = 0;
            }
            --count;
            put.signal();
            System.out.println("current id-take:"+Thread.currentThread().getId());
            return x;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        T05_ReentrantLock6 lo = new T05_ReentrantLock6();
        for (int i = 0; i < 1000; i++) {
            Thread t1 = new Thread(){
                @Override
                public void run() {
                    try {
                        lo.put(new Object());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            t1.start();
            Thread t2 = new Thread(){
                @Override
                public void run() {
                    try {
                        lo.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            t2.start();
        }
        Thread.sleep(5*1000);
        System.out.println("end");
    }

}
