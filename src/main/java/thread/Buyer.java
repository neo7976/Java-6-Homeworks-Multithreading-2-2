package thread;

import avto.CarImp;

import java.util.Deque;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.locks.ReentrantLock;

public class Buyer extends Thread {
    private int time;
    private final List<CarImp> list;
    private Thread thread;
    ReentrantLock locker;
    Queue<String> deque;

    public Buyer(int time, List<CarImp> list, Thread thread, ReentrantLock lock, Queue<String> deque) {
        this.time = time;
        this.list = list;
        this.thread = thread;
        this.locker = lock;
        this.deque = deque;
    }

    @Override
    public void run() {
        try {
            while (thread.isAlive()) {
                locker.lock();
                System.out.println(Thread.currentThread().getName() + " зашёл в магазин");
                if (list.isEmpty()) {
                    System.out.printf("%s ушёл без машины\n", Thread.currentThread().getName());
                    deque.add(Thread.currentThread().getName());
                } else {
                    System.out.printf("%s купил %s.\n",
                            Thread.currentThread().getName(),
                            list.remove(0));
                    deque.poll();
                }

                Thread.sleep(time);
                locker.unlock();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

