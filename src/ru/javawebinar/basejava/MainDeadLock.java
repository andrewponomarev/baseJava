package ru.javawebinar.basejava;

public class MainDeadLock {

    static class T implements Runnable {

        private Object lock;

        public T() {
        }

        public void setLock(Object lock) {
            this.lock = lock;
        }


        private synchronized void method() {
            try {
                System.out.println(Thread.currentThread().getName() + "," + this + " before wait()");
                wait();
                System.out.println(Thread.currentThread().getName() + "," + this + " after wait()");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + "," + this + " before notify()");
                lock.notifyAll();
                System.out.println(Thread.currentThread().getName() + "," + this + " after notify()");
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
        A.setLock(B);
        B.setLock(A);

        Thread t1 = new Thread(A);
        Thread t2 = new Thread(B);

        t1.start();
        t2.start();
    }
}
