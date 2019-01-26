package com.epam.portsimulation.service.state.impl;

import com.epam.portsimulation.entity.Dock;
import com.epam.portsimulation.entity.Ship;
import com.epam.portsimulation.service.state.ShipState;

public class LoadingState extends ShipState {
    @Override
    public boolean doActionInPort(Dock dock) {
        int maxCapacity =ship.getMaxCapacity();
        int containersCount =ship.getContainersCount();

        int countToLoad = maxCapacity - containersCount;
        boolean isSuccessful = dock.removeContainers(countToLoad);
        if (isSuccessful) {
            ship.setContainersCount(maxCapacity) ;
        }
        return isSuccessful;
    }

    public LoadingState(Ship ship) {
        super(ship);
    }
}
