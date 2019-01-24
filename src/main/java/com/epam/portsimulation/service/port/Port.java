package com.epam.portsimulation.service.port;

import java.util.concurrent.Semaphore;

public class Port {

    private static volatile Port instance;
    private static final int COUNT_DOCKS = 3;
    private Semaphore semaphore = new Semaphore(3, true);

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
}
