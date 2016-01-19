package ua.telesens.ostapenko.systemimitation.api;

import ua.telesens.ostapenko.systemimitation.model.internal.RouteList;

/**
 * @author root
 * @since 18.01.16
 */
public interface XMLRouteListConverter {

    String toXML(RouteList list,String path);

    RouteList fromXML(String xml);

}
