package com.epam.portsimulation.service.state;

import com.epam.portsimulation.entity.Ship;

public abstract class ShipState {
    private Ship ship;

    public ShipState(Ship ship) {
        this.ship = ship;
    }

    public abstract void doActionInPort();
}
