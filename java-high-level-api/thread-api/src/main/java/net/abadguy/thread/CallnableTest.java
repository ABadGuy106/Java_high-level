package net.abadguy.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class CallnableTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        NumThread numThread = new NumThread();
        FutureTask futureTask = new FutureTask(numThread);
        new Thread(futureTask).start();

        Object o = futureTask.get();
        System.out.println(o);
    }
}



class NumThread implements Callable{

    public Object call() throws Exception {
        int sum=0;
        for (int i = 0; i <= 100 ; i++) {
            if(i%2==0){
                System.out.println(i);
                sum+=i;
            }
        }
        return sum;
    }
}
