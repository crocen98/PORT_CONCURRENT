package com.epam.portsimulation.service.thradssarter;

import java.util.List;

public class ThradsStarter<T extends Runnable> {
    public void startAllThrads(List<T> runnables) {
        runnables.stream().forEach(runnable -> {
            new Thread(runnable).start();
        });
    }
}
