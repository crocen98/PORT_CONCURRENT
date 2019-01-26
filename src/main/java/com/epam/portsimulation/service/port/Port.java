package com.epam.portsimulation.service.port;


import com.epam.portsimulation.entity.Dock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.ArrayDeque;

import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
    private static final Logger LOGGER = LogManager.getLogger(Port.class);
    private static final int START_CONTAINERS_NUMBER_IN_DOCK = 20_000;
    private static final int START_MAX_CAPACITY_IN_DOCK = 100_000;
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
            LOGGER.error(e);
            Thread.currentThread().interrupt();
        } finally {
            lockForPollDock.unlock();
        }
        return null;
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

}
