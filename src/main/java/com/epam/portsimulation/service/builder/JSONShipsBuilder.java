package com.epam.portsimulation.service.builder;

import com.epam.portsimulation.entity.Ship;
import com.epam.portsimulation.service.exception.NotValidShipException;
import com.epam.portsimulation.service.exception.ServiceFailNotFoundException;
import com.epam.portsimulation.service.validation.impl.ImplShipValidator;
import com.epam.portsimulation.service.validation.ShipValidator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

public class JSONShipsBuilder implements ShipsBuilder {
    public List<Ship> parse(String source) throws NotValidShipException, ServiceFailNotFoundException {
        Gson gson = new Gson();
        try {
            Type shipsListType = new TypeToken<List<Ship>>() {
            }.getType();
            List<Ship> ships = gson.fromJson(new BufferedReader(new FileReader(source)), shipsListType);
            ShipValidator validator = new ImplShipValidator();
            validator.validate(ships);
            return ships;
        } catch (FileNotFoundException e) {
            throw new ServiceFailNotFoundException(e);
        }
    }

}