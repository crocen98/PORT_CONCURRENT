package com.epam.portsimulation.entity;

import com.epam.portsimulation.service.exception.SimulationErrorException;
import com.epam.portsimulation.service.port.Port;
import com.epam.portsimulation.service.state.ShipState;
import com.epam.portsimulation.service.state.StateFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.Callable;

public class Ship implements Callable<String>, Serializable {
    private static final Logger LOGGER = LogManager.getLogger(Ship.class);
    private static final int MAXIMUM_ATTEMPTS_NUMBER = 30;
    private String name;
    private Purpose purpose;
    private int containersCount;
    private final int maxCapacity;
    private transient Port port;


    public Ship(int containersCount, int maxCapacity, String name, Purpose purpose) {
        this.containersCount = containersCount;
        this.maxCapacity = maxCapacity;
        this.name = name;
        this.purpose = purpose;
    }


    @Override
    public String call() {
        port = Port.getInstance();
        LOGGER.info("Start run for ship: " + name);
        ShipState state = StateFactory.getInstance().getState(this);
        boolean isSuccessful = false;
        int numberEnterToDock = 0;
        while (!isSuccessful) {
            LOGGER.info(this.name + " try to find suitable dock. " + numberEnterToDock + "rd attempt to enter the dock");
            Dock dock = port.pollDock();
            LOGGER.info("Count enabled docks: " + port.getCountEnabledDocs());
            numberEnterToDock++;
            isSuccessful = state.doActionInPort(dock);
            port.returnDock(dock);
            if (numberEnterToDock >= MAXIMUM_ATTEMPTS_NUMBER) {
                try {
                    throw new SimulationErrorException("This port cannot serve ship " + name + " at the present time, the prowling will go to another port.");
                } catch (SimulationErrorException e){
                    LOGGER.error(e);
                }
            }
        }
        LOGGER.info(this + "End run()");
        return "Ship: " + this.name + " finish " + this.purpose + " successful!";
    }

    public Purpose getPurpose() {
        return purpose;
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
                Objects.equals(purpose, ship.purpose);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, purpose, containersCount, maxCapacity);
    }
}
