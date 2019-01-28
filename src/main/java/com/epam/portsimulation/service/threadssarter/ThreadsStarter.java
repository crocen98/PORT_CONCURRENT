package com.epam.portsimulation.service.threadssarter;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadsStarter {
    public <T> void startAllThreads(List<? extends Callable<T>> callables) {
        ExecutorService service = Executors.newFixedThreadPool(10);
        callables.stream().forEach(service::submit);
        service.shutdown();
    }
}
