package com.epam.portsimulation.entity;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShipTest {


    @Test
    public void shouldGetShipsAndChangeStateOfAllShips() throws InterruptedException {
        Ship ship1 = new Ship(0, 10000, "Name1", Purpose.LOADING);
        Ship ship2 = new Ship(0, 10000, "Name2", Purpose.LOADING);
        Ship ship3 = new Ship(10000, 10000, "Name3", Purpose.UNLOADING);
        Ship ship4 = new Ship(10000, 10000, "Name4", Purpose.UNLOADING);

        final ExecutorService service = Executors.newFixedThreadPool(10);
        service.submit(ship1);
        service.submit(ship2);
        service.submit(ship3);
        service.submit(ship4);

        Thread.sleep(100);
        Assert.assertEquals(10000, ship1.getContainersCount());
        Assert.assertEquals(10000, ship2.getContainersCount());
        Assert.assertEquals(0, ship3.getContainersCount());
        Assert.assertEquals(0, ship4.getContainersCount());
    }
}

