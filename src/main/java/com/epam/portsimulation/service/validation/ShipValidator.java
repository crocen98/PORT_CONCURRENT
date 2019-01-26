package com.epam.portsimulation.service.validation;

import com.epam.portsimulation.entity.Ship;
import com.epam.portsimulation.service.exception.NotValidShipException;

import java.util.Collection;

public interface ShipValidator {
     void validate(Collection<Ship> ships) throws NotValidShipException;
}
