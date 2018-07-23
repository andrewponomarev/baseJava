package ru.javawebinar.basejava;

public class MainDeadLock {

    static class T implements Runnable {

        private Object lock_1;

        private Object lock_2;

        public T() {
        }

        public void setLock_1(Object lock) {
            this.lock_1 = lock;
        }

        public void setLock_2(Object lock) {
            this.lock_2 = lock;
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
        T A = new T();
        T B = new T();
        Object lock_1 = new Object();
        Object lock_2 = new Object();
        A.setLock_1(lock_1);
        A.setLock_2(lock_2);
        B.setLock_1(lock_2);
        B.setLock_2(lock_1);

        Thread t1 = new Thread(A);
        Thread t2 = new Thread(B);

        t1.start();
        t2.start();
    }
}
