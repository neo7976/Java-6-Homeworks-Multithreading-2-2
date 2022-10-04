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

    public Buyer(int time, List<CarImp> list, Thread thread, ReentrantLock lock) {
        this.time = time;
        this.list = list;
        this.thread = thread;
        this.locker = lock;
    }

    @Override
    public void run() {
//        boolean locked = false;
        try {
            while (thread.isAlive()) {
                System.out.println(Thread.currentThread().getName() + " зашёл в магазин");
//                locker.lock();
                if (list.isEmpty()) {
                    System.out.println("Машин нет");
//                    locker.unlock();
                } else {
                    System.out.printf("%s купил %s.\n",
                            Thread.currentThread().getName(),
                            list.remove(0));
//                    locker.unlock();
                }
                Thread.sleep(time);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        finally {
//            locker.unlock();
//        }
    }
}

