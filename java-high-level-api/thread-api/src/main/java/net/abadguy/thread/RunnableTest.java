package net.abadguy.thread;

public class RunnableTest {
    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        new Thread(myThread).start();
    }
}


class MThread implements Runnable{

    public void run() {

        for (int i = 0; i < 100; i++) {
            if(i%2==0){
                System.out.println(i);
            }
        }
    }
}
