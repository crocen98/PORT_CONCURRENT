package com.epam.portsimulation.view;

import com.epam.portsimulation.controller.Controller;
import com.epam.portsimulation.controller.impl.ConsoleController;

public class ConsoleView {
    public static void main(String ... args){
        Controller controller = new ConsoleController();
        controller.startPortSimulation("resources/json/ships.json");
    }
}
