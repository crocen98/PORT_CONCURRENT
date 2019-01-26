package com.epam.portsimulation.entity;

public class Dock {
    private int numberOfContainers;
    private final int maxCapacity;
    private final int id;

    @Override
    public String toString() {
        return "Dock{" +
                "numberOfContainers=" + numberOfContainers +
                ", maxCapacity=" + maxCapacity +
                ", id=" + id +
                '}';
    }

    public Dock(int startContainersNumber, int maxCapacity,int id) {
        numberOfContainers = startContainersNumber;
        this.maxCapacity = maxCapacity;
        this.id = id;
    }

    public boolean addContainers(int containersCount) {
        if (numberOfContainers + containersCount > maxCapacity) {
            return false;
        }
        numberOfContainers += containersCount;
        return true;
    }

    public boolean removeContainers(int containersCount) {
        if (numberOfContainers - containersCount < 0) {
            return false;
        }
        numberOfContainers -= containersCount;
        return true;
    }
}
