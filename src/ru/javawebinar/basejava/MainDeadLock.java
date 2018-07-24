package ru.javawebinar.basejava;

public class MainDeadLock {

    static class T implements Runnable {

        private final Object lock_1;

        private final Object lock_2;

        public T(Object lock_1, Object lock_2) {
            this.lock_1 = lock_1;
            this.lock_2 = lock_2;
        }

        private void method() {
            System.out.println("Waiting for " + lock_1);
            synchronized (lock_1) {
                System.out.println(lock_1 + " is holded");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Waiting for " + lock_2);
                synchronized (lock_2) {
                    System.out.println(lock_2 + " is holded");
                }
            }
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "," + this + " run()");
            method();
        }
    }

    public static void main(String[] args) {
        Object lock_1 = new Object();
        Object lock_2 = new Object();
        T A = new T(lock_1, lock_2);
        T B = new T(lock_2, lock_1);
        Thread t1 = new Thread(A);
        Thread t2 = new Thread(B);

        t1.start();
        t2.start();
    }
}
