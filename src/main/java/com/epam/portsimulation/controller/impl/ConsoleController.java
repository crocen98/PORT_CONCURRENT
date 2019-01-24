package com.epam.portsimulation.controller.impl;

import com.epam.portsimulation.controller.Controller;
import com.epam.portsimulation.entity.Ship;
import com.epam.portsimulation.service.builder.JSONShipsBuilder;
import com.epam.portsimulation.service.builder.ShipsBuilder;
import com.epam.portsimulation.service.port.Port;

import java.util.List;

public class ConsoleController implements Controller {
    public void startPortSimulation(String pathToFile) {
        ShipsBuilder builder = new JSONShipsBuilder();
        List<Ship> ships= builder.parse(pathToFile);
        Port port = Port.getInstance();
        port.startWork();
    }
}
