import avto.CarImp;
import avto.Toyota;
import avto.Volvo;
import thread.Buyer;

import java.util.*;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public final static int ISSUE = 6;
    public final static int TIME_ADMISSION = 3000;
    public final static int TIME_PEOPLE_BUY = 1000;

    public static void main(String[] args) {
        List<CarImp> auto = new ArrayList<>();
        Queue<String> deque = new LinkedList<>();
        ReentrantLock locker = new ReentrantLock(true);
        Random random = new Random();

        Thread carThread = new Thread(() -> {
            CarImp newCar = null;
            int value;
            for (int i = 0; i < ISSUE; i++) {
                value = random.nextInt(3);
                locker.lock();
                try {
                    switch (value) {
                        case 0 -> auto.add(newCar = new Toyota("Camry", 2016));
                        case 1 -> auto.add(newCar = new Volvo("XC90", 2021));
                        case 2 -> auto.add(newCar = new Toyota("RAV4", 2019));
                    }
                    newCar.admission();
                    if (!deque.isEmpty() && !auto.isEmpty()) {
                        System.out.printf("%s купил %s.\n",
                                deque.poll(),
                                auto.remove(0));
                    }
                    try {
                        Thread.sleep(TIME_ADMISSION);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } finally {
                    locker.unlock();
                }
            }
        });
        carThread.start();

        for (int i = 1; i <= 4; i++) {
            Thread buy = new Thread(new Buyer(TIME_PEOPLE_BUY, auto, carThread, locker, deque));
            buy.setName("Покупатель " + i);
            buy.start();
        }
    }
}
