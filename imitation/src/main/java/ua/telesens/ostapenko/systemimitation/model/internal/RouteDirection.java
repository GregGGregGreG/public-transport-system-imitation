package ua.telesens.ostapenko.systemimitation.model.internal;

import java.util.ArrayList;
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
        public List<BusStation> toStation(BusRouteDecorator route, BusStation station) {
            List<BusStation> stations = (List<BusStation>) route.getStations();
            int position = stations.indexOf(station);

            List<BusStation> result = new ArrayList<>();
            for (int i = position + 1; i < stations.size(); i++) {
                result.add(stations.get(i));
            }
            return result;
        }
    }, BACK {
        @Override
        public List<BusStation> toStation(BusRouteDecorator route, BusStation station) {
            List<BusStation> stations = (List<BusStation>) route.getStations();
            int position = stations.indexOf(station);

            List<BusStation> result = new ArrayList<>();
            for (int i = position - 1; i >= 0; i--) {
                result.add(stations.get(i));
            }
            return result;
        }
    };

    public abstract List<BusStation> toStation(BusRouteDecorator route, BusStation station);

    public static RouteDirection getRandom() {
        List<RouteDirection> letters = Arrays.asList(RouteDirection.values());
        Collections.shuffle(letters);
        return letters.stream().findFirst().get();
    }

    public static RouteDirection getRandom(BusRouteDecorator route, BusStation station) {
        List<BusStation> stations = (List<BusStation>) route.getStations();
        if (stations.indexOf(station) == 0) {
            //From initialize station
            return RouteDirection.STRAIGHT;
        } else if (stations.indexOf(station) == (stations.size() - 1)) {
            //From final station
            return RouteDirection.BACK;
        }
        return getRandom();
    }

}
