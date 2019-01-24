package com.epam.portsimulation.service.builder;

import com.epam.portsimulation.entity.Ship;

import java.util.List;

public interface ShipsBuilder {
    List<Ship> parse(String source);
}
