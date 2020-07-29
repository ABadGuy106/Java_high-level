package net.abadguy.thread;

/**
 * 多线程的创建
 *      方式一：继承Thread类
 */
public class TestThread {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());
        MyThread myThread = new MyThread();
        myThread.start();
    }
}



class MyThread extends Thread{
    @Override
    public void run() {
        for(int i=0;i<100;i++){
            if(i%2 ==0){
                System.out.println(Thread.currentThread().getName()+":"+i);
            }
        }
    }
}
