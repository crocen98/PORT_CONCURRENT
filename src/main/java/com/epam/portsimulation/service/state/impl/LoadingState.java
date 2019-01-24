package com.epam.portsimulation.service.state.impl;

import com.epam.portsimulation.entity.Ship;
import com.epam.portsimulation.service.state.ShipState;

public class LoadingState extends ShipState {
    @Override
    public void doActionInPort() {

    }

    public LoadingState(Ship ship) {
        super(ship);
    }
}
