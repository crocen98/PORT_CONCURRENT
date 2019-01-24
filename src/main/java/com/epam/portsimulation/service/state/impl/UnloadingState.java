package com.epam.portsimulation.service.state.impl;

import com.epam.portsimulation.entity.Ship;
import com.epam.portsimulation.service.state.ShipState;

public class UnloadingState extends ShipState {
    public UnloadingState(Ship ship) {
        super(ship);
    }
    @Override
    public void doActionInPort() {

    }
}
