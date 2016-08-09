package com.ctl.bookstore.test;

public class ThreadLocalTest2 implements Runnable {
	
	ThreadLocal<Student> threadLocal = new ThreadLocal<>();
	
	Student student = new Student();
	int i = 0;

	@Override
	public void run() {
		for(;i < 10; i++){
			student.setName(Thread.currentThread().getName());
			
//			try {
//				Thread.sleep(10);
//			} catch (InterruptedException e) {}

			threadLocal.set(student);
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {}
			
			System.out.println(Thread.currentThread().getName() + ": " + threadLocal.get().hashCode());				
		}
		
	}
	
	public static void main(String[] args) {
			
		ThreadLocalTest2 tlt = new ThreadLocalTest2();
		
		new Thread(tlt, "AAA").start();
		new Thread(tlt, "BBB").start();
		
		
	}

}
