package ua.telesens.ostapenko.systemimitation.model.internal.observer;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ua.telesens.ostapenko.systemimitation.api.PassengerManager;
import ua.telesens.ostapenko.systemimitation.api.RouteManager;
import ua.telesens.ostapenko.systemimitation.api.StationRule;
import ua.telesens.ostapenko.systemimitation.api.observer.SystemImitationObserver;
import ua.telesens.ostapenko.systemimitation.model.internal.*;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author root
 * @since 14.12.15
 */
@Slf4j
public class StationObserver implements SystemImitationObserver, PassengerManager, RouteManager {

    @Getter
    private Station station;
    private Map<Route, Map<RouteMovement, Queue<Passenger>>> passengers = Collections.emptyMap();
    private List<StationRule> stationRules = Collections.emptyList();
    private List<Route> routes = Collections.emptyList();

    private StationObserver(Station station, List<StationRule> stationRules) {
        this.station = station;
        this.stationRules = stationRules;
        this.passengers = new HashMap<>();
        this.routes = new ArrayList<>();
    }

    public static StationObserver of(Station station, List<StationRule> stationRules) {
        return new StationObserver(station, stationRules);
    }

    // FIXME: 17.12.15 Add my exception
    @Override
    public void addPassenger(Route route, RouteMovement routeMovement, Passenger passenger) throws IllegalStateException {
        if (passenger.getFinalStation().equals(station)) {
            throw new IllegalStateException(station + "\tNot valid\t" + passenger);
        }
        //Get Route
        boolean a = passengers.containsKey(route);
        Map<RouteMovement, Queue<Passenger>> routeMovementQueueMap = passengers.get(route);
        routeMovementQueueMap
                //Get type way route
                .get(routeMovement)
                //Add passenger toStation que
                .add(passenger);
    }

    @Override
    public Passenger getPassenger(Route route, RouteMovement routeMovement) {
        if (hasNextPassenger(route, routeMovement)) {
            return passengers.get(route).get(routeMovement).poll();
        }
        throw new IllegalStateException();
    }

    public boolean hasNextPassenger(Route route, RouteMovement routeMovement) {
        return !passengers.get(route).get(routeMovement).isEmpty();
    }

    @Override
    public void updateTime(LocalDateTime time, Map<Route, List<ScheduleNode>> schedule) {
        isMovement(time);
        stationRules.stream()
                .filter(stationRule -> stationRule.is(time.toLocalTime()))
                .forEach(stationRule -> {
                    int count = stationRule.execute(time);
                    genPassengers(count);
                    log.debug(time.toLocalDate() + " |\t" + time.toLocalTime() + " |\t" + station +
                            " |Gen passenger \t" + count + " |Current count passenger \t" + (getCountPassenger())
                    );
                });
    }

    // FIXME: 17.12.15 BAD code
    private void isMovement(LocalDateTime time) {
        routes.stream().filter(route -> route.getEnding().equals(time.toLocalTime())).
                forEach(route -> {
                    RouteType routeType = route.getType();
                    if (routeType.equals(RouteType.LINE)) {
                        for (RouteMovement routeMovement : RouteMovement.values()) {
                            passengers.get(route).get(routeMovement).clear();
                        }
                    } else if (routeType.equals(RouteType.CYCLE)) {
                        passengers.get(route).get(RouteMovement.STRAIGHT).clear();
                    }
                });
    }

    @Override
    public void registerRoute(Route add) {
        routes.add(add);
        Map<RouteMovement, Queue<Passenger>> data = new HashMap<>();
        if (add.getType().equals(RouteType.LINE)) {
            for (RouteMovement routeMovement : RouteMovement.values()) {
                data.put(routeMovement, new ArrayDeque<>());
            }
        }
        passengers.put(add, data);
    }

    @Override
    public String toString() {
        return "StationObserver{" +
                "stationObserver=" + station +
                '}';
    }

    private void genPassengers(int count) {
        RouteMovement routeMovement;
        Route route;
        Passenger passenger;
        Station stationFinal;
        for (int i = 0; i < count; i++) {
            route = getRandomRoute();
            routeMovement = RouteMovement.getRandom(route, station);
            stationFinal = routeMovement.toStation(route, station)
                    .stream()
                    .findFirst()
                    .get();
            passenger = new Passenger(stationFinal);

            addPassenger(route, routeMovement, passenger);
            log.debug(station + "Gen new passenger\t" + routeMovement + "\t" + passenger);
        }
    }

    private Route getRandomRoute() {
        List<Route> buff = new ArrayList<>();
        buff.addAll(routes);
        Collections.shuffle(buff);
        return buff.stream().findFirst().get();
    }

    // FIXME: 18.12.15 Validate from type route
    private int getCountPassenger() {
        int result = 0;

        for (Route route : routes) {
            for (RouteMovement routeMovement : RouteMovement.values()) {
                result += passengers.get(route).get(routeMovement).size();
            }
        }
        return result;
    }
}