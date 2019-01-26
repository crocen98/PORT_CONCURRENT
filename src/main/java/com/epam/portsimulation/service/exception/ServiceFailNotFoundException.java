package com.epam.portsimulation.service.exception;

import java.io.IOException;

public class ServiceFailNotFoundException extends IOException {
    public ServiceFailNotFoundException(Exception e){
        super(e);
    }
}
