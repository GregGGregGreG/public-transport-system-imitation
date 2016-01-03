package ua.telesens.ostapenko.systemimitation.model.internal.observer;

import lombok.EqualsAndHashCode;
import ua.telesens.ostapenko.systemimitation.api.PassengerManager;
import ua.telesens.ostapenko.systemimitation.api.RouteManager;
import ua.telesens.ostapenko.systemimitation.api.observer.SystemImitationObserver;
import ua.telesens.ostapenko.systemimitation.model.internal.*;

import java.util.*;

/**
 * @author root
 * @since 14.12.15
 */
@EqualsAndHashCode(exclude = {"passengers", "routes", "countPassenger"})
public class StationObserver implements SystemImitationObserver, PassengerManager, RouteManager {

    private BusStation station;
    private Map<BusRouteDecorator, Map<RouteDirection, Queue<Passenger>>> passengers = Collections.emptyMap();
    //Use from optimize process generation passenger
    private List<BusRouteDecorator> routes = Collections.emptyList();
    private long countPassenger;

    public StationObserver(BusStation station, BusRouteDecorator route) {
        this.station = station;
        this.routes = new ArrayList<>();
        this.passengers = new HashMap<>();
        registerRoute(route);
    }

    // FIXME: 17.12.15 Add my exception
    @Override
    public void addPassenger(BusRouteDecorator route, RouteDirection direction, Passenger passenger) throws IllegalStateException {
        if (passenger.getStation().equals(station)) {
            throw new IllegalStateException(station + "\tNot valid\t" + passenger);
        }
        //Get Route
        Map<RouteDirection, Queue<Passenger>> routeMovementQueueMap = passengers.get(route);
        routeMovementQueueMap
                //Get direction route
                .get(direction)
                //Add passenger toStation que
                .add(passenger);
        //Increment count passenger
        countPassenger++;
    }

    @Override
    public Passenger getPassenger(BusRouteDecorator route, RouteDirection direction) {
        if (hasNextPassenger(route, direction)) {
            countPassenger--;
            return this.passengers.get(route).get(direction).poll();
        }
        throw new IllegalStateException();
    }

    public boolean hasNextPassenger(BusRouteDecorator route, RouteDirection direction) {
        return !passengers.get(route).get(direction).isEmpty();
    }

    @Override
    public void updateEvent(ImitationEvent event) {
//        isMovement(time);
//        stationRules.stream()
//                .filter(stationRule -> stationRule.is(time.toLocalTime()))
//                .forEach(stationRule -> {
//                    int count = stationRule.execute(time);
//                    genPassengers(count);
//                    log.debug(time.toLocalDate() + " |\t" + time.toLocalTime() + " |\t" + station +
//                            " |Gen passenger \t" + count + " |Current count passenger \t" + (getCountPassenger())
//                    );
//                });
    }

    // FIXME: 17.12.15 BAD code
//    private void isMovement(LocalDateTime time) {
//        routes.stream().filter(route -> route.getEnding().equals(time.toLocalTime())).
//                forEach(route -> {
//                    RouteType routeType = route.getType();
//                    if (routeType.equals(RouteType.LINE)) {
//                        for (EnumDirection direction : EnumDirection.values()) {
//                            passengers.get(route).get(direction).clear();
//                        }
//                    } else if (routeType.equals(RouteType.CYCLE)) {
//                        passengers.get(route).get(EnumDirection.STRAIGHT).clear();
//                    }
//                });
//    }
    @Override
    public void registerRoute(BusRouteDecorator route) {
        if (routes.contains(route)) {
            return;
        }
        routes.add(route);
        passengers.put(route, route.getType().getQue());
    }

    @Override
    public String toString() {
        return String.valueOf(station);
    }

    private void genPassengers(int count) {
        RouteDirection direction;
        BusRouteDecorator route;
        Passenger passenger;
        BusStation stationFinal;
        for (int i = 0; i < count; i++) {
            route = getRandomRoute();
            direction = RouteDirection.getRandom(route, station);
            stationFinal = direction.toStation(route, station)
                    .stream()
                    .findFirst()
                    .get();
            passenger = Passenger.of(stationFinal);

            addPassenger(route, direction, passenger);
//            log.debug(station + "Gen new passenger\t" + direction + "\t" + passenger);
        }
    }

    private BusRouteDecorator getRandomRoute() {
        List<BusRouteDecorator> buff = new ArrayList<>();
        buff.addAll(routes);
        Collections.shuffle(buff);
        return buff.stream().findFirst().get();
    }

    // FIXME: 18.12.15 Validate from type route
    private long getCountPassenger() {
        return countPassenger;
    }

    public BusStation getStation() {
        return station;
    }
}