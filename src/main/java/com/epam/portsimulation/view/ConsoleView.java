package com.epam.portsimulation.view;

import com.epam.portsimulation.controller.Controller;
import com.epam.portsimulation.controller.impl.ConsoleController;

public class ConsoleView {
    private static final String PATH_TO_JSON_FILE = "resources/json/ships.json";
    public static void main(String ... args){
        Controller controller = new ConsoleController();
        controller.startPortSimulation(PATH_TO_JSON_FILE);
    }
}
