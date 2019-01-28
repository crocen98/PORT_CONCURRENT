package com.epam.portsimulation.service.builder;

import com.epam.portsimulation.entity.Purpose;
import com.epam.portsimulation.entity.Ship;
import com.epam.portsimulation.service.exception.NotValidShipException;
import com.epam.portsimulation.service.exception.ServiceFailNotFoundException;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class JSONShipsBuilderTest {
    private static final ShipsBuilder BUILDER = new JSONShipsBuilder();

    @Test
    public void shouldBuildListEqualsTestData() throws NotValidShipException, ServiceFailNotFoundException {

        Ship ship1 = new Ship(10000, 10000, "Ship1", Purpose.UNLOADING);
        Ship ship3 = new Ship(0, 10000, "Ship3", Purpose.LOADING);
        Ship ship6 = new Ship(10000, 10000, "Ship6", Purpose.UNLOADING);
        Ship ship2 = new Ship(0, 10000, "Ship2", Purpose.LOADING);
        Ship ship9 = new Ship(10000, 10000, "Ship9", Purpose.UNLOADING);
        Ship ship10 = new Ship(0, 10000, "Ship10", Purpose.LOADING);
        Ship ship4 = new Ship(10000, 10000, "Ship4", Purpose.UNLOADING);
        Ship ship5 = new Ship(0, 10000, "Ship5", Purpose.LOADING);
        Ship ship7 = new Ship(10000, 10000, "Ship7", Purpose.UNLOADING);
        Ship ship8 = new Ship(0, 10000, "Ship8", Purpose.LOADING);
        Ship ship11 = new Ship(500, 10000, "Ship11", Purpose.LOADING_UNLOADING);
        Ship ship12 = new Ship(500, 500, "Ship12", Purpose.UNLOADING);
        List<Ship> testShips = new ArrayList<>(12);
        testShips.add(ship1);
        testShips.add(ship2);
        testShips.add(ship3);
        testShips.add(ship4);
        testShips.add(ship5);
        testShips.add(ship6);
        testShips.add(ship7);
        testShips.add(ship8);
        testShips.add(ship9);
        testShips.add(ship10);
        testShips.add(ship11);
        testShips.add(ship12);

        List<Ship> ships = BUILDER.parse("resources/json/ships.json");

        Assert.assertEquals(12,ships.size());
        Assert.assertEquals(testShips,ships);
    }

    @Test(expected = ServiceFailNotFoundException.class)
    public void shouldGetNotValidPathAndTrowException() throws NotValidShipException, ServiceFailNotFoundException {
        BUILDER.parse("resources/json/ship");
    }

    @Test(expected = NotValidShipException.class)
    public void shouldGetNotValidDataAndTrowException() throws NotValidShipException, ServiceFailNotFoundException {
        BUILDER.parse("resources/json/ships2.json");
    }

}