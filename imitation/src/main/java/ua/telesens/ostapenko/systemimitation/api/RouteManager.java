package ua.telesens.ostapenko.systemimitation.api;

import ua.telesens.ostapenko.systemimitation.model.internal.Arc;
import ua.telesens.ostapenko.systemimitation.model.internal.Route;
import ua.telesens.ostapenko.systemimitation.model.internal.Station;

import java.util.ArrayList;
import java.util.List;

/**
 * @author root
 * @since 14.12.15
 */
public interface RouteManager {

    void registerRoute(Route add);

    static List<Station> getAllStation(Route route) {
        List<Station> result = new ArrayList<>();
        List<Arc> buff = (List<Arc>) route.getArcList();
        for (int i = 0; i < buff.size(); i++) {
            result.add(buff.get(i).getInitialStation().getStation());
            if ((i + 1) == buff.size()) {
                result.add(buff.get(i).getFinalStation().getStation());
            }
        }
        return result;
    }
}
