package com.epam.portsimulation.service.state.impl;

import com.epam.portsimulation.entity.Purpose;
import com.epam.portsimulation.entity.Ship;
import com.epam.portsimulation.service.port.Port;
import com.epam.portsimulation.service.state.ShipState;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class QueueState  extends ShipState {
    public QueueState(Ship ship) {
        super(ship);
    }

    @Override
    public void doActionInPort() throws InterruptedException {
        Lock lock = ship.getLock();
        Purpose purpose = ship.getPurpose();
        Port port = ship.getPort();
        Condition lockCondition = lock.newCondition();
        lock.lock();
        try {
            if (purpose == Purpose.LOADING) {
                while (port.getContainersCount().intValue() < ship.getMaxCapacity()){
                    lockCondition.await();
                }
            } else if(purpose == Purpose.UNLOADING){
                while (port.getMaxCapacity() > ship.getContainersCount() + port.getContainersCount().intValue()){
                    lockCondition.await();
                }
            }
        }finally {
            lock.unlock();
        }





    }
}
