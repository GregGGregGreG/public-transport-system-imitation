package ua.telesens.ostapenko.systemimitation.api;


import ua.telesens.ostapenko.systemimitation.model.internal.Passenger;
import ua.telesens.ostapenko.systemimitation.model.internal.Route;
import ua.telesens.ostapenko.systemimitation.model.internal.RouteMovement;

import javax.transaction.NotSupportedException;

/**
 * @author root
 * @since 13.12.15
 */
public interface PassengerManager {

    void addPassenger(Route route, RouteMovement routeMovement, Passenger add) throws NotSupportedException;

    Passenger getPassenger(Route route, RouteMovement routeMovement);

}
