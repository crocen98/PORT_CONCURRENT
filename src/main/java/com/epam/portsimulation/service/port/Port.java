package com.epam.portsimulation.service.port;


import com.epam.portsimulation.entity.Purpose;
import com.epam.portsimulation.entity.Ship;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Port {

    private static volatile Port instance;
    private static final int COUNT_DOCKS = 3;
    private  AtomicInteger containersCount = new AtomicInteger(0);
    private final int maxCapacity = 1_000_00000;
   // private Queue<Dock> docks = new ArrayDeque<>();
    private Semaphore semaphore = new Semaphore(COUNT_DOCKS, true);

    public static Port getInstance() {
        Port localInstance = instance;
        if (localInstance == null) {
            synchronized (Port.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Port();
                }
            }
        }
        return localInstance;
    }

    private Port() {
    }

    public void startWork() {
    }

    public static int getCountDocks() {
        return COUNT_DOCKS;
    }

    public AtomicInteger getContainersCount() {
        return containersCount;
    }

    public void setContainersCount(AtomicInteger containersCount) {
        this.containersCount = containersCount;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    public void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }


    public static void main(String ... args) throws InterruptedException {
        Port port = Port.getInstance();
        Ship ship1 = new Ship(1_000_100, 1_000_000 , "Victoria", Purpose.UNLOADING);
        Ship ship2 = new Ship(0, 10000 , "Titanick", Purpose.LOADING);
        Ship ship3 = new Ship(0, 1_000_00 , "Varag", Purpose.LOADING);
        Ship ship4 = new Ship(1_000_100, 1_000_000 , "Black", Purpose.UNLOADING);
        Ship ship5 = new Ship(0, 1_000_00 , "White sale", Purpose.LOADING);
        Ship ship6 = new Ship(1_000_100, 1_000_000 , "1111111", Purpose.UNLOADING);
        Ship ship7 = new Ship(1_000_100, 1_000_100 , "2222222", Purpose.UNLOADING);

        Ship ship8 = new Ship(0, 1_000_00 , "3333333", Purpose.LOADING);

        Ship ship9 = new Ship(1_000_100, 1_000_100 , "4444444", Purpose.UNLOADING);

        Ship ship10 = new Ship(0, 10000 , "234567654345676543345676543234", Purpose.LOADING);
        Ship ship11 = new Ship(0, 10000 , "6666", Purpose.LOADING);

        new Thread(ship1).start();
        new Thread(ship2).start();
        new Thread(ship3).start();
        new Thread(ship4).start();
        new Thread(ship5).start();
        new Thread(ship6).start();
        new Thread(ship7).start();
        new Thread(ship8).start();
        new Thread(ship9).start();
        new Thread(ship10).start();
        new Thread(ship11).start();



        //port.setContainersCount(new AtomicInteger(100_000_000));






    }
}
