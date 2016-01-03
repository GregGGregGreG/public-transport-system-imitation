package ua.telesens.ostapenko.systemimitation.api;

import ua.telesens.ostapenko.systemimitation.model.internal.BusRouteDecorator;

/**
 * @author root
 * @since 14.12.15
 */
public interface RouteManager {

    void registerRoute(BusRouteDecorator add);

}
