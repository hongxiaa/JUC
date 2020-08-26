
package com.mashibing.juc.c_022_RefTypeAndThreadLocal;

import java.util.concurrent.TimeUnit;
/**
 * ThreadLocal线程局部变量
 *
 * ThreadLocal是使用空间换时间，synchronized是使用时间换空间
 * 比如在hibernate中session就存在与ThreadLocal中，避免synchronized的使用
 *
 * 运行下面的程序，理解ThreadLocal
 * https://wemp.app/posts/a04a03f1-ac4b-4cbf-93ed-b47f591e9c51
 * @author 马士兵
 */
public class ThreadLocal2 {
	//volatile static Person p = new Person();
	static ThreadLocal<Person> tl = new ThreadLocal<>();
	
	public static void main(String[] args) {
				
		new Thread(()->{
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println(Thread.currentThread()  + " "+tl.get());
		}).start();
		
		new Thread(()->{
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread());
			tl.set(new Person());
		}).start();

	}
	
	static class Person {
		String name = "zhangsan";
	}
}


