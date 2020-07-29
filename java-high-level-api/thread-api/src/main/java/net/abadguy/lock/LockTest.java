package net.abadguy.lock;


import java.util.concurrent.locks.ReentrantLock;

class Window implements Runnable{

    private int ticket=100;

    //ReentrantLock创建对象时，传true表示为公平锁，先进先出。默认是false,不公平锁
    private ReentrantLock lock=new ReentrantLock(true);

    public void run() {
        while (true){
            try {
                //调用加锁方法
                lock.lock();
                if(ticket>0){

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(Thread.currentThread().getName()+": 售票,票号："+ticket);
                    ticket--;
                }else {
                    break;
                }
            }finally {
                //调用解锁方法
                lock.unlock();
            }
        }
    }
}


public class LockTest {
    public static void main(String[] args) {
        Window window = new Window();

        Thread t1 = new Thread(window);
        Thread t2 = new Thread(window);
        Thread t3 = new Thread(window);

        t1.setName("窗口1");
        t2.setName("窗口2");
        t3.setName("窗口3");

        t1.start();
        t2.start();
        t3.start();
    }
}
