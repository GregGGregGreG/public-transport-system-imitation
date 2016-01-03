package ua.telesens.ostapenko.systemimitation.model.internal;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author root
 * @since 16.12.15
 */
public enum RouteDirection {

    STRAIGHT {
        @Override
        public List<BusStation> toStation(BusRouteDecorator busRoute, BusStation busStation) {
//            List<Station> stations = RouteManager.getAllStation(route);
//            int position = stations.indexOf(station);
//
//            List<Station> result = new ArrayList<>();
//            for (int i = position + 1; i < stations.size(); i++) {
//                result.add(stations.get(i));
//            }
//            return result;
            return null;
        }
    }, BACK {
        @Override
        public List<BusStation> toStation(BusRouteDecorator busRoute, BusStation busStation) {
//            List<Station> stations = RouteManager.getAllStation(route);
//            List<Station> result = new ArrayList<>();
//            int position = stations.indexOf(station);
//
//            for (int i = position - 1; i >= 0; i--) {
//                result.add(stations.get(i));
//            }
//            return result;
            return null;
        }
    };

    public abstract List<BusStation> toStation(BusRouteDecorator busRoute, BusStation position);

    public static RouteDirection getRandom() {
        List<RouteDirection> letters = Arrays.asList(RouteDirection.values());
        Collections.shuffle(letters);
        return letters.stream().findFirst().get();
    }

    public static RouteDirection getRandom(BusRouteDecorator busRoute, BusStation busStation) {
        List<BusStation> stations = (List<BusStation>) busRoute.getStations();
        RouteDirection result = getRandom();
        if (stations.indexOf(busStation) == 0) {
            result = RouteDirection.STRAIGHT;
        } else if (stations.indexOf(busStation) == (stations.size() - 1)) {
            result = RouteDirection.BACK;
        }
        return result;
    }

}
