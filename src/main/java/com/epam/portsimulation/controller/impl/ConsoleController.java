package com.epam.portsimulation.controller.impl;

import com.epam.portsimulation.controller.Controller;
import com.epam.portsimulation.entity.Ship;
import com.epam.portsimulation.service.builder.JSONShipsBuilder;
import com.epam.portsimulation.service.builder.ShipsBuilder;
import com.epam.portsimulation.service.exception.NotValidShipException;
import com.epam.portsimulation.service.exception.ServiceFailNotFoundException;
import com.epam.portsimulation.service.threadssarter.ThreadsStarter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ConsoleController implements Controller {
    private static final Logger LOGGER = LogManager.getLogger(ConsoleController.class);

    public void startPortSimulation(String pathToFile) {
        ShipsBuilder builder = new JSONShipsBuilder();
        try {
            List<Ship> ships= builder.parse(pathToFile);
            ThreadsStarter starter = new ThreadsStarter();
            starter.startAllThreads(ships);
        } catch (NotValidShipException | ServiceFailNotFoundException e) {
            LOGGER.error(e);
        }

    }
}
