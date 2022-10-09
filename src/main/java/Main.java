import avto.CarImp;
import avto.Toyota;
import avto.Volvo;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public final static int ISSUE = 10;
    public final static int TIME_ADMISSION = 1000;
    public final static int TIME_PEOPLE_BUY = 2000;

    public static void main(String[] args) {
        List<CarImp> auto = new ArrayList<>();
        ReentrantLock locker = new ReentrantLock(true);
        Condition condition = locker.newCondition();
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
                    condition.signal();
                    try {
                        Thread.sleep(TIME_ADMISSION);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } finally {
                    locker.unlock();
                }
            }
            Thread.currentThread().interrupt();
        });
        carThread.start();

        Runnable buyer = () -> {
            try {
                while (!carThread.isInterrupted()) {
                    locker.lock();
                    System.out.println(Thread.currentThread().getName() + " зашёл в магазин");
                    Thread.sleep(TIME_PEOPLE_BUY);
                    if (auto.isEmpty()) {
                        if (carThread.isInterrupted()) {
                            System.out.printf("%s остановлен", Thread.currentThread().getName());
                            Thread.currentThread().interrupt();
                        }
                        System.out.printf("%s встал в очередь на машину\n", Thread.currentThread().getName());
                        condition.await();
                    }
                    System.out.printf("%s купил автомобиль - %s\n", Thread.currentThread().getName(), auto.remove(0));
                    Thread.sleep(TIME_PEOPLE_BUY);
//                    locker.unlock();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        for (int i = 1; i <= 5; i++) {
            Thread buy = new Thread(buyer);
            buy.setName("Покупатель " + i);
            buy.start();
        }
    }
}
