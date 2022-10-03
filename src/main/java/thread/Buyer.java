package thread;

import avto.CarImp;

import java.util.List;

public class Buyer extends Thread {
    private int time;
    private final List<CarImp> list;
    private Thread thread;

    public Buyer(int time, List<CarImp> list, Thread thread) {
        this.time = time;
        this.list = list;
        this.thread = thread;
    }

    @Override
    public void run() {

        do {
            synchronized (list) {
                System.out.println(Thread.currentThread().getName() + " зашёл в магазин");
                if (list.isEmpty()) {
                    System.out.println("Машин нет");
                } else {
                    System.out.printf("%s купил %s.\n",
                            Thread.currentThread().getName(),
                            list.remove(0));
                }
                list.notify();
            }
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (thread.isAlive());
    }
}
