package ua.telesens.ostapenko.systemimitation.model.internal;

import ua.telesens.ostapenko.systemimitation.api.RouteManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author root
 * @since 16.12.15
 */
public enum RouteMovement {

    STRAIGHT {
        @Override
        public List<Station> toStation(Route route, Station station) {
            List<Station> stations = RouteManager.getAllStation(route);
            int position = stations.indexOf(station);

            List<Station> result = new ArrayList<>();
            for (int i = position + 1; i < stations.size(); i++) {
                result.add(stations.get(i));
            }
            return result;
        }
    }, BACK {
        @Override
        public List<Station> toStation(Route route, Station station) {
            List<Station> stations = RouteManager.getAllStation(route);
            List<Station> result = new ArrayList<>();
            int position = stations.indexOf(station);

            for (int i = position - 1; i >= 0; i--) {
                result.add(stations.get(i));
            }
            return result;
        }
    };

    public abstract List<Station> toStation(Route route, Station position);

    public static RouteMovement getRandom() {
        List<RouteMovement> letters = Arrays.asList(RouteMovement.values());
        Collections.shuffle(letters);
        return letters.stream().findFirst().get();
    }

    public static RouteMovement getRandom(Route route, Station station) {
        List<Station> stations = RouteManager.getAllStation(route);
        RouteMovement result = getRandom();
        if (stations.indexOf(station) == 0) {
            result = RouteMovement.STRAIGHT;
        } else if (stations.indexOf(station) == (stations.size() - 1)) {
            result = RouteMovement.BACK;
        }
        return result;
    }

}
