package ua.telesens.ostapenko.systemimitation.api.decorator;

import ua.telesens.ostapenko.systemimitation.model.internal.*;

import java.time.LocalTime;
import java.util.Collection;
import java.util.Map;

/**
 * @author root
 * @since 29.12.15
 */
public interface RouteTransportPublic {

    String getName();

    RouteType getType();

    Collection<RouteArc> getArcList();

    Collection<Bus> getBuses();

    LocalTime getStarting();

    Map<DayType, RouteTrafficRuleList> getRules();

}
