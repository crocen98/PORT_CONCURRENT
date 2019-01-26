package com.epam.portsimulation.service.state.impl;

import com.epam.portsimulation.entity.Dock;
import com.epam.portsimulation.entity.Ship;
import com.epam.portsimulation.service.state.ShipState;


public class LoadingUnloadingState extends ShipState {
    public LoadingUnloadingState(Ship ship) {
        super(ship);
    }

    @Override
    public boolean doActionInPort(Dock dock) {
        int containersCount = ship.getContainersCount();
        boolean isSuccessful = dock.addContainers(containersCount);
        int maxCapacity = ship.getMaxCapacity();
        if (isSuccessful) {
            isSuccessful = dock.removeContainers(maxCapacity);
            ship.setContainersCount(maxCapacity);
        }
        return isSuccessful;
    }
}
