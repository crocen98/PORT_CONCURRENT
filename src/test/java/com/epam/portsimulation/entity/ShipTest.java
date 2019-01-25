package com.epam.portsimulation.entity;

import com.epam.portsimulation.service.exception.SimulatedErrorException;
import com.epam.portsimulation.service.port.Port;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class ShipTest {

    @Before
    public void portReset(){
        Port port = Port.getInstance();
        port.getContainersCount().set(0);
    }

    @Test
    public void shouldGetEmptyShipAndReturnShipWithFullCountOfContainers() {
        Port port = Port.getInstance();
        port.getContainersCount().set(10000);

        Ship ship = new Ship(0, 10000, "Name1",Purpose.LOADING);

        Ship testShip = new Ship(10000, 10000, "Name1",Purpose.UNLOADING);
        new Thread(ship).start();
        Assert.assertEquals(testShip, ship);
    }


    @Test
    public void shouldGetShipsAndChangeStateOfAllShips() {
        Ship ship1 = new Ship(0, 10000, "Name1",Purpose.LOADING);
        Ship ship2 = new Ship(0, 10000,"Name2", Purpose.LOADING);
        Ship ship3 = new Ship(10000, 10000, "Name3",Purpose.UNLOADING);
        Ship ship4 = new Ship(10000, 10000,"Name4", Purpose.UNLOADING);


        Ship testShip1 = new Ship(10000, 10000, "Name1",Purpose.UNLOADING);
        Ship testShip2 = new Ship(10000, 10000, "Name2",Purpose.UNLOADING);
        Ship testShip3 = new Ship(0, 10000, "Name3",Purpose.LOADING);
        Ship testShip4 = new Ship(0, 10000, "Name4",Purpose.LOADING);

        new Thread(ship1).start();
        new Thread(ship2).start();
        new Thread(ship3).start();
        new Thread(ship4).start();

        Assert.assertEquals(testShip1, ship1);
        Assert.assertEquals(testShip2, ship2);
        Assert.assertEquals(testShip3, ship3);
        Assert.assertEquals(testShip4, ship4);
    }


    @Test(expected = SimulatedErrorException.class)
    public void shouldGetNotSimulatedDataAndThrowException() {
        Ship ship1 = new Ship(0, 10000, "Name1",Purpose.LOADING);
        Ship ship2 = new Ship(0, 10000,"Name2", Purpose.LOADING);

        new Thread(ship1).start();
        new Thread(ship2).start();

    }



}