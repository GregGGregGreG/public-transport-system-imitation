package ua.telesens.ostapenko.systemimitation.api;


import ua.telesens.ostapenko.systemimitation.model.internal.BusRouteDecorator;
import ua.telesens.ostapenko.systemimitation.model.internal.Passenger;
import ua.telesens.ostapenko.systemimitation.model.internal.RouteDirection;

import javax.transaction.NotSupportedException;

/**
 * @author root
 * @since 13.12.15
 */
public interface PassengerManager {

    void addPassenger(BusRouteDecorator busRoute, RouteDirection direction, Passenger add) throws NotSupportedException;

    Passenger getPassenger(BusRouteDecorator busRoute, RouteDirection direction);

}
