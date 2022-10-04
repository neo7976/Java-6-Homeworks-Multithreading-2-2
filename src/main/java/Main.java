import avto.CarImp;
import avto.Toyota;
import avto.Volvo;
import thread.Buyer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public final static int ISSUE = 6;
    public final static int TIME_ADMISSION = 3000;
    public final static int TIME_PEOPLE_BUY = 2500;

    public static void main(String[] args) {
        List<CarImp> auto = new ArrayList<>();
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
            Thread buy = new Thread(new Buyer(TIME_PEOPLE_BUY, auto, carThread, locker));
            buy.setName("Покупатель " + i);
            buy.start();
        }
    }
}
