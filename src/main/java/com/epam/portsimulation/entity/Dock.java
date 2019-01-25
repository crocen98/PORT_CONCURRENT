package com.epam.portsimulation.entity;

public class Dock {
    private int numberOfContainers;
    private final int maxCapacity;

    public Dock(int startContainersNumber , int maxCapacity){
        numberOfContainers = maxCapacity;
        this.maxCapacity = maxCapacity;
    }


    public int getNumberOfContainers() {
        return numberOfContainers;
    }

    public void setNumberOfContainers(int numberOfContainers) {
        this.numberOfContainers = numberOfContainers;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }
}
