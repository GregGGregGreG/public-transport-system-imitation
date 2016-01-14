package ua.telesens.ostapenko.systemimitation.api.decorator;

import ua.telesens.ostapenko.systemimitation.model.internal.Bus;
import ua.telesens.ostapenko.systemimitation.model.internal.RouteArc;
import ua.telesens.ostapenko.systemimitation.model.internal.RouteTrafficRuleList;
import ua.telesens.ostapenko.systemimitation.model.internal.RouteType;

import java.time.LocalTime;
import java.util.Collection;
import java.util.Set;

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

    Set<RouteTrafficRuleList> getRules();

}
