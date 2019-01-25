package com.epam.portsimulation.entity;

import com.epam.portsimulation.service.exception.SimulationErrorException;
import com.epam.portsimulation.service.port.Port;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class ShipTest {

    @Before
    public void portReset(){
        Port port = Port.getInstance();
        //port.getContainersCount().set(0);
    }

    @Test
    public void shouldGetEmptyShipAndReturnShipWithFullCountOfContainers() throws InterruptedException {
        Port.getInstance();
       // port.getContainersCount().set(10000);

        Ship ship = new Ship(0, 10000, "Name1",Purpose.LOADING);
        new Thread(ship).start();
      // Thread.sleep(10000);
       // Assert.assertEquals(10000, ship.getContainersCount());
    }


    @Test
    public void shouldGetShipsAndChangeStateOfAllShips() throws InterruptedException {
        Ship ship1 = new Ship(0, 10000, "Name1",Purpose.LOADING);
        Ship ship2 = new Ship(0, 10000,"Name2", Purpose.LOADING);
        Ship ship3 = new Ship(10000, 10000, "Name3",Purpose.UNLOADING);
        Ship ship4 = new Ship(10000, 10000,"Name4", Purpose.UNLOADING);

        new Thread(ship1).start();
        new Thread(ship2).start();
        new Thread(ship3).start();
        new Thread(ship4).start();
        Thread.sleep(100);
        Assert.assertEquals(10000, ship1.getContainersCount());
        Assert.assertEquals(10000, ship2.getContainersCount());
        Assert.assertEquals(0, ship3.getContainersCount());
        Assert.assertEquals(0, ship4.getContainersCount());
    }


    @Test(expected = SimulationErrorException.class)
    public void shouldGetNotSimulatedDataAndThrowException() {
        Ship ship1 = new Ship(0, 10000, "Name1",Purpose.LOADING);
        Ship ship2 = new Ship(0, 10000,"Name2", Purpose.LOADING);

        new Thread(ship1).start();
        new Thread(ship2).start();

    }



}