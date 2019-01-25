package com.epam.portsimulation.service.port;


import com.epam.portsimulation.entity.Dock;
import com.epam.portsimulation.entity.Purpose;
import com.epam.portsimulation.entity.Ship;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
    private static final Logger LOGGER = LogManager.getLogger(Port.class);

    private static volatile Port instance;
    private static final int COUNT_DOCKS = 3;
    private ReentrantLock lock = new ReentrantLock();
    private ReentrantLock lock2 = new ReentrantLock();

    private Queue<Dock> docks;
    private Semaphore semaphore = new Semaphore(COUNT_DOCKS);

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

    public int getCountEnabledDocs(){
        return docks.size();
    }
    public Dock pollDock() {
        try {
            lock.lock();
            semaphore.acquire();
            return docks.poll();
        } catch (InterruptedException e) {
            throw new RuntimeException("NOT SUPPORT");
        } finally {
            lock.unlock();
        }
    }

    public void returnDock(Dock dock) {
        try {
            LOGGER.info("start returnDock()");
            lock2.lock();
            LOGGER.info("before lock returnDock()");

            docks.add(dock);
            LOGGER.info("returnDock()");
        } finally {
            semaphore.release();
            lock2.unlock();
        }
    }


    private Port() {
        docks = new ArrayDeque<>(COUNT_DOCKS);
        for (int i = 0 ; i < COUNT_DOCKS; ++i){
            docks.add(new Dock(0 , 11000,i));
        }
    }


    public void startWork() {
    }

    public static int getCountDocks() {
        return COUNT_DOCKS;
    }



    public Semaphore getSemaphore() {
        return semaphore;
    }

    public void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }


    public static void main(String... args) throws InterruptedException {
        Port.getInstance();
        Ship ship1 = new Ship(10000, 10000, "Ship1", Purpose.UNLOADING);
        Ship ship3 = new Ship(0, 10000, "Ship3", Purpose.LOADING);

        Ship ship6 = new Ship(10000, 10000, "Ship6", Purpose.UNLOADING);
        Ship ship2 = new Ship(0, 10000, "Ship2", Purpose.LOADING);

        Ship ship9 = new Ship(10000, 10000, "Ship9", Purpose.UNLOADING);
        Ship ship10 = new Ship(0, 10000, "Ship10", Purpose.LOADING);


        Ship ship4 = new Ship(11000, 11000, "Ship4", Purpose.UNLOADING);
        Ship ship5 = new Ship(0, 11000, "Ship5", Purpose.LOADING);

        Ship ship7 = new Ship(10010, 10010, "Ship7", Purpose.UNLOADING);
        Ship ship8 = new Ship(0, 10010, "Ship8", Purpose.LOADING);

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


        //port.setContainersCount(new AtomicInteger(100_000_000));


    }
}
