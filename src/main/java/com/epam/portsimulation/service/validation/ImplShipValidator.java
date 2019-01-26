package com.epam.portsimulation.service.validation;

import com.epam.portsimulation.entity.Ship;
import com.epam.portsimulation.service.exception.NotValidShipException;

import java.util.Collection;
import java.util.List;

public class ImplShipValidator implements ShipValidator {
    @Override
    public void validate(Collection<Ship> ships) throws NotValidShipException {
        for (Ship ship : ships) {
            if (ship.getMaxCapacity() < 0
                    || ship.getContainersCount() < 0) {
                throw new NotValidShipException("Ships capacity and containers count should be greater than 0!");
            }
            if (ship.getContainersCount() < ship.getContainersCount()) {
                throw new NotValidShipException("Ships capacity should be greater than containers count!");

            }
        }
    }
}
