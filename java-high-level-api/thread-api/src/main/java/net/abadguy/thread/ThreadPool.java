package net.abadguy.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPool {
    public static void main(String[] args) {
        //提供指定线程数量的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        //设置线程的属性
        ThreadPoolExecutor threadPoolExecutor= (ThreadPoolExecutor) executorService;

        threadPoolExecutor.setCorePoolSize(10);

        //执行指定的线程操作
        executorService.execute(new NumberThread());//适用于Runnable接口
        executorService.execute(new NumberThread1());
//        executorService.submit();//适用于Callable接口

        //关闭连接池
        executorService.isShutdown();
    }
}


class NumberThread implements Runnable{

    public void run() {
        for (int i = 0; i < 100; i++) {
            if(i%2==0){
                System.out.println(Thread.currentThread().getName()+":"+i);
            }
        }
    }
}
class NumberThread1 implements Runnable{

    public void run() {
        for (int i = 0; i < 100; i++) {
            if(i%2==0){
                System.out.println(Thread.currentThread().getName()+":"+i);
            }
        }
    }
}

