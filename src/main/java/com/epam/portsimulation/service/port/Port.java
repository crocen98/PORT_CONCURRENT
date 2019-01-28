package com.epam.portsimulation.service.port;


import com.epam.portsimulation.entity.Dock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.ArrayDeque;

import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
    private static final Logger LOGGER = LogManager.getLogger(Port.class);

    private static final int START_CONTAINERS_NUMBER_IN_DOCK = 20_000;
    private static final int START_MAX_CAPACITY_IN_DOCK = 100_000;
    private static final int COUNT_DOCKS = 3;

    private static Port instance;
    private Lock lock = new ReentrantLock();

    private final Queue<Dock> docks;
    private final Semaphore semaphore = new Semaphore(COUNT_DOCKS);
    private static  AtomicBoolean isCreated = new AtomicBoolean(false);
    private static final Lock singletonLock = new ReentrantLock();
    public static Port getInstance() {
        try {
            singletonLock.lock();
            if (isCreated.get()) {
                return instance;
            } else {
                instance = new Port();
                isCreated.set(true);
                return instance;
            }
        } finally {
            singletonLock.unlock();
        }
    }

    public int getCountEnabledDocs() {
        return docks.size();
    }

    public Dock pollDock() {
        try {
            LOGGER.info("start pollDock()");
            semaphore.acquire();
            lock.lock();
            LOGGER.info("Lock in pollDock() and semaphore.acquire()");
            return docks.poll();
        } catch (InterruptedException e) {
            LOGGER.error(e);
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
            LOGGER.info("UnLock in pollDock() and semaphore.acquire()");
        }
        return null;
    }

    public void returnDock(Dock dock) {
        try {
            LOGGER.info("start returnDock()");
            lock.lock();
            LOGGER.info("Lock returnDock()");

            docks.add(dock);
            LOGGER.info("returnDock()");
        } finally {
            semaphore.release();
            LOGGER.info("semaphore.release()");
            lock.unlock();
            LOGGER.info("UnLock returnDock()");

        }
    }


    private Port() {
        docks = new ArrayDeque<>(COUNT_DOCKS);
        for (int i = 0; i < COUNT_DOCKS; ++i) {
            docks.add(new Dock(START_CONTAINERS_NUMBER_IN_DOCK, START_MAX_CAPACITY_IN_DOCK, i));
        }
    }
}
