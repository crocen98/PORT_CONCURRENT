package com.epam.portsimulation.entity;

import com.epam.portsimulation.service.exception.SimulationErrorException;
import com.epam.portsimulation.service.port.Port;
import com.epam.portsimulation.service.state.ShipState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.Objects;

public class Ship implements Runnable, Serializable {
    private static final Logger LOGGER = LogManager.getLogger(Ship.class);
    private String name;
    private ShipState state;
    private Purpose purpose;
    private int containersCount;
    private final int maxCapacity;
    private Port port;


    public Ship(int containersCount, int maxCapacity, String name, Purpose purpose) {
        this.containersCount = containersCount;
        this.maxCapacity = maxCapacity;
        this.name = name;
        this.purpose = purpose;
    }


    @Override
    public void run() {
        port = Port.getInstance();
        LOGGER.info("Start run for ship: " + name);
        boolean isSuccessful = false;
        int numberEnterToDock = 0;
        while (!isSuccessful) {
            LOGGER.info(this + " try to find suitable dock. " + numberEnterToDock + " iteration of loop");
            Dock dock = port.pollDock();
            numberEnterToDock++;
            switch (purpose) {
                case LOADING:
                    int countToLoad = maxCapacity - containersCount;
                    isSuccessful = dock.removeContainers(countToLoad);
                    if (isSuccessful) {
                        containersCount = maxCapacity;
                    }
                    break;
                case UNLOADING:
                    isSuccessful = dock.addContainers(containersCount);
                    if (isSuccessful) {
                        containersCount = 0;
                    }
                    break;
                case LOADING_UNLOADING:
                    isSuccessful = dock.addContainers(containersCount);
                    if (isSuccessful) {
                        isSuccessful = dock.removeContainers(maxCapacity);
                        containersCount = maxCapacity;
                    }
                    break;
                default:
                    throw new IllegalArgumentException("NOT SUPPORTED STATE!!!");
            }
            port.returnDock(dock);
            if (numberEnterToDock >= 30) {
                try {
                    throw new SimulationErrorException("This port cannot serve ship " + name + " at the present time, the prowling will go to another port.");
                } catch (SimulationErrorException e) {
                    LOGGER.error(e);
                    return;
                }
            }
        }

        LOGGER.info(this+ "End run()");
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ship ship = (Ship) o;
        return containersCount == ship.containersCount &&
                maxCapacity == ship.maxCapacity &&
                Objects.equals(name, ship.name) &&
                Objects.equals(state, ship.state) &&
                Objects.equals(purpose,ship.purpose);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, state, purpose, containersCount, maxCapacity);
    }
}
