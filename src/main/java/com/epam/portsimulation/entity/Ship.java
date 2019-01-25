package com.epam.portsimulation.entity;

import com.epam.portsimulation.service.port.Port;
import com.epam.portsimulation.service.state.ShipState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;

public class Ship implements Runnable, Serializable {
    private static final Logger LOGGER = LogManager.getLogger(Ship.class);
    private String name;
    private ShipState state;
    private Purpose purpose;
    private int containersCount;
    private final int maxCapacity;
    private Port port = Port.getInstance();


    public Ship(int containersCount, int maxCapacity, String name, Purpose purpose) {
        this.containersCount = containersCount;
        this.maxCapacity = maxCapacity;
        this.name = name;
        this.purpose = purpose;
    }


    @Override
    public void run() {
        LOGGER.info("Start run for ship: " + name);
        boolean isSuccessful = false;
        int numberEnterToDock = 0;
        while (!isSuccessful) {
            LOGGER.info(this);
            Dock dock = port.pollDock();
            LOGGER.info("Get dock: " + dock);
            numberEnterToDock++;
            switch (purpose) {
                case LOADING:
                    LOGGER.info("LOADING");
                    int countToLoad = maxCapacity - containersCount;
                    isSuccessful = dock.removeContainers(countToLoad);
                    if (isSuccessful) {
                        containersCount = maxCapacity;
                    }
                    break;
                case UNLOADING:
                    LOGGER.info("UNLOADING");
                    isSuccessful = dock.addContainers(containersCount);
                    if (isSuccessful) {
                        containersCount = 0;
                    }
                    break;
                case LOADING_UNLOADING:
                    LOGGER.info("LOADING_UNLOADING");
                    isSuccessful = dock.addContainers(containersCount);
                    if (isSuccessful) {
                        isSuccessful = dock.removeContainers(maxCapacity);
                        containersCount = maxCapacity;
                    }
                default:
                    throw new IllegalArgumentException("NOT SUPPORTEDE STATE!!!");
            }
            LOGGER.info(port.getCountEnabledDocs() + "before getCountEnabledDocs()");
            port.returnDock(dock);
            LOGGER.info(port.getCountEnabledDocs() + "after getCountEnabledDocs()");
//            if (numberEnterToDock >= 100) {
//                try {
//                    throw new SimulationErrorException("This port cannot serve ship " + name + " at the present time, the prowling will go to another port.");
//                } catch (SimulationErrorException e) {
//                    LOGGER.error(e);
//                    return;
//                }
//            }
        }
    }


    public ShipState getState() {
        return state;
    }

    public void setState(ShipState state) {
        this.state = state;
    }

    public Purpose getPurpose() {
        return purpose;
    }

    public void setPurpose(Purpose purpose) {
        this.purpose = purpose;
    }

    public int getContainersCount() {
        return containersCount;
    }

    public void setContainersCount(int containersCount) {
        this.containersCount = containersCount;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Port getPort() {
        return port;
    }

    public void setPort(Port port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "name='" + name + '\'' +
                ", state=" + state +
                ", purpose=" + purpose +
                ", containersCount=" + containersCount +
                ", maxCapacity=" + maxCapacity +
                ", port=" + port +
                '}';
    }
}
