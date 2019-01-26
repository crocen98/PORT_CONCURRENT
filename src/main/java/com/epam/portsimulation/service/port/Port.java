package com.epam.portsimulation.service.port;


import com.epam.portsimulation.entity.Dock;
import com.epam.portsimulation.entity.Purpose;
import com.epam.portsimulation.entity.Ship;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
    private static final Logger LOGGER = LogManager.getLogger(Port.class);
private static final   int  START_CONTAINERS_NUMBER_IN_DOCK = 20_000;
    private static final   int  START_MAX_CAPACITY_IN_DOCK = 100_000;
    private static final int COUNT_DOCKS = 3;

    private static volatile Port instance;
    private ReentrantLock lockForPollDock = new ReentrantLock();
    private ReentrantLock lockForReturnDock = new ReentrantLock();

    private final Queue<Dock> docks;
    private final Semaphore semaphore = new Semaphore(COUNT_DOCKS);

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

    public int getCountEnabledDocs() {
        return docks.size();
    }

    public Dock pollDock() {
        try {
            lockForPollDock.lock();
            semaphore.acquire();
            LOGGER.info("semaphore.acquire(); in method pollDock()");
            return docks.poll();
        } catch (InterruptedException e) {
            throw new RuntimeException("NOT SUPPORT");
        } finally {
            lockForPollDock.unlock();
        }
    }

    public void returnDock(Dock dock) {
        try {
            LOGGER.info("start returnDock()");
            lockForReturnDock.lock();
            LOGGER.info("before lock returnDock()");

            docks.add(dock);
            LOGGER.info("returnDock()");
        } finally {
            semaphore.release();
            LOGGER.info("semaphore.release(); in method returnDock()");
            lockForReturnDock.unlock();
        }
    }


    private Port() {
        docks = new ArrayDeque<>(COUNT_DOCKS);
        for (int i = 0; i < COUNT_DOCKS; ++i) {
            docks.add(new Dock(START_CONTAINERS_NUMBER_IN_DOCK, START_MAX_CAPACITY_IN_DOCK, i));
        }
    }




    public static int getCountDocks() {
        return COUNT_DOCKS;
    }





    public static void main(String... args) throws InterruptedException {
        Port.getInstance();
        Ship ship1 = new Ship(10000, 10000, "Ship1", Purpose.UNLOADING);
        Ship ship3 = new Ship(0, 10000, "Ship3", Purpose.LOADING);

        Ship ship6 = new Ship(10000, 10000, "Ship6", Purpose.UNLOADING);
        Ship ship2 = new Ship(0, 10000, "Ship2", Purpose.LOADING);

        Ship ship9 = new Ship(10000, 10000, "Ship9", Purpose.UNLOADING);
        Ship ship10 = new Ship(0, 10000, "Ship10", Purpose.LOADING);


        Ship ship4 = new Ship(10000, 10000, "Ship4", Purpose.UNLOADING);
        Ship ship5 = new Ship(0, 10000, "Ship5", Purpose.LOADING);

        Ship ship7 = new Ship(10000, 10000, "Ship7", Purpose.UNLOADING);
        Ship ship8 = new Ship(0, 10000, "Ship8", Purpose.LOADING);

        Ship ship11 = new Ship(500, 10000, "Ship11", Purpose.LOADING_UNLOADING);
        Ship ship12 = new Ship(500, 500, "Ship12", Purpose.UNLOADING);

        List<Ship> list = new ArrayList<>(12);
        list.add(ship1);
        list.add(ship2);
        list.add(ship3);
        list.add(ship4);
        list.add(ship5);
        list.add(ship6);
        list.add(ship7);
        list.add(ship8);
        list.add(ship9);
        list.add(ship10);
        list.add(ship11);
        list.add(ship12);

        Gson gson = new Gson();

        String json = gson.toJson(list);
        Type shipsListType = new TypeToken<ArrayList<Ship>>() {
        }.getType();
        // List<Ship> ships = gson.fromJson(json, shipsListType);
        System.out.println(json);


//        new Thread(ship1).start();
//        new Thread(ship2).start();
//        new Thread(ship3).start();
//        new Thread(ship4).start();
//        new Thread(ship5).start();
//        new Thread(ship6).start();
//        new Thread(ship7).start();
//        new Thread(ship8).start();
//        new Thread(ship9).start();
//        new Thread(ship10).start();
//        new Thread(ship11).start();
//        new Thread(ship12).start();


        //port.setContainersCount(new AtomicInteger(100_000_000));


    }
}
