package com.epam.portsimulation.service.state;

import com.epam.portsimulation.entity.Ship;
import com.epam.portsimulation.service.state.impl.LoadingState;
import com.epam.portsimulation.service.state.impl.UnloadingState;

public class StateFactory {
    private static StateFactory ourInstance = new StateFactory();

    public static StateFactory getInstance() {
        return ourInstance;
    }

    private StateFactory() {
    }

    public ShipState getState(Ship ship){
        switch (ship.getPurpose()){
            case LOADING_UNLOADING:
                return new LoadingState(ship);
            case UNLOADING:
                return new UnloadingState(ship);
            case LOADING:
                return new LoadingState(ship);
            default:
                throw new IllegalArgumentException("Not supported purpose");
        }
    }
}
