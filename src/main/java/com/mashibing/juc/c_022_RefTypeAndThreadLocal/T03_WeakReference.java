/**
 * 弱引用遭到gc就会回收
 * 只要有gc过程，直接gc掉，没回收之前是可以get到的
 */
package com.mashibing.juc.c_022_RefTypeAndThreadLocal;

import java.lang.ref.WeakReference;

public class T03_WeakReference {
    public static void main(String[] args) {
        WeakReference<M> m = new WeakReference<>(new M());

        System.out.println(m.get());
        System.gc();
        System.out.println(m.get());


        ThreadLocal<M> tl = new ThreadLocal<>();
        tl.set(new M());
        tl.remove();

    }
}

