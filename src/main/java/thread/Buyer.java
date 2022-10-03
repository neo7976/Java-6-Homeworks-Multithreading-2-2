package thread;

import avto.CarImp;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buyer extends Thread {
    private int time;
    private final List<CarImp> list;
    private Thread thread;
    ReentrantLock locker;
    Condition condition;

    public Buyer(int time, List<CarImp> list, Thread thread, ReentrantLock lock, Condition condition) {
        this.time = time;
        this.list = list;
        this.thread = thread;
        this.locker = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        locker.lock();
        try {
            while (thread.isAlive()) {
                System.out.println(Thread.currentThread().getName() + " зашёл в магазин");
                if (list.isEmpty()) {
                    System.out.println("Машин нет");
                    condition.wait();
                } else {
                    System.out.printf("%s купил %s.\n",
                            Thread.currentThread().getName(),
                            list.remove(0));
                }
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            locker.unlock();
        }
    }
}

