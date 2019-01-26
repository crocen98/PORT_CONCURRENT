package com.epam.portsimulation.service.threadssarter;

import java.util.List;

public class ThreadsStarter {
    public<T extends Runnable> void startAllThreads(List<T> runnables) {
        runnables.stream().forEach(runnable ->
                new Thread(runnable).start()
        );
    }
}
