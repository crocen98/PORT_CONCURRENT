package com.epam.portsimulation.service.state.impl;

import com.epam.portsimulation.entity.Ship;
import com.epam.portsimulation.service.state.ShipState;

public class QueueState  extends ShipState {
    public QueueState(Ship ship) {
        super(ship);
    }

    @Override
    public void doActionInPort() {

    }
}
