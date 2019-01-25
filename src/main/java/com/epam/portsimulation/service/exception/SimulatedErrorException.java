package com.epam.portsimulation.service.exception;

public class SimulatedErrorException extends Exception{
    public SimulatedErrorException(Exception e){
        super(e);
    }
}
