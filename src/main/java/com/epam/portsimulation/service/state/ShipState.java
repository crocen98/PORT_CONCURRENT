package com.epam.portsimulation.service.state;

import com.epam.portsimulation.entity.Dock;
import com.epam.portsimulation.entity.Ship;

public abstract class ShipState {
    protected Ship ship;

    public ShipState(Ship ship) {
        this.ship = ship;
    }

    public abstract boolean doActionInPort(Dock dock);
}
