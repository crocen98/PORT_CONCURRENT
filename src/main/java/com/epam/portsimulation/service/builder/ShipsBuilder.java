package com.epam.portsimulation.service.builder;

import com.epam.portsimulation.entity.Ship;
import com.epam.portsimulation.service.exception.NotValidShipException;
import com.epam.portsimulation.service.exception.ServiceFailNotFoundException;

import java.util.List;

public interface ShipsBuilder {
    List<Ship> parse(String source) throws NotValidShipException, ServiceFailNotFoundException;
}
