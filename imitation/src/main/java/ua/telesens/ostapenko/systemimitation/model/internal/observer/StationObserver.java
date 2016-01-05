package ua.telesens.ostapenko.systemimitation.model.internal.observer;

import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import ua.telesens.ostapenko.systemimitation.api.PassengerManager;
import ua.telesens.ostapenko.systemimitation.api.RouteManager;
import ua.telesens.ostapenko.systemimitation.api.observer.SystemImitationObserver;
import ua.telesens.ostapenko.systemimitation.model.internal.*;

import java.time.LocalTime;
import java.util.*;

/**
 * @author root
 * @since 14.12.15
 */
@Slf4j
@EqualsAndHashCode(exclude = {"passengers", "routes", "countPassenger"})
public class StationObserver implements SystemImitationObserver, PassengerManager, RouteManager {

    private final BusStation station;
    private Map<BusRouteDecorator, Map<RouteDirection, Queue<Passenger>>> passengers = Collections.emptyMap();
    //Use from optimize process generation passenger
    private Collection<BusRouteDecorator> routes = Collections.emptyList();
    private long countPassenger;

    private StationObserver(BusStation station, BusRouteDecorator route) {
        this.station = station;
        this.routes = new ArrayList<>();
        this.passengers = new HashMap<>();
        registerRoute(route);
    }

    public static StationObserver of(BusStation station, BusRouteDecorator route){
        return new StationObserver(station,route);
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
        List<PassengerGenerationRule> rules = station.getRules().get(event.getDayType());
        LocalTime time = event.getTime().toLocalTime();
        rules.stream()
                .filter(rule -> rule.is(time))
                .forEach(rule -> {
                    int count = rule.execute(time);
                    genPassengers(count);

                    log.debug(String.format("%-6s|%-12s|%-12s|%-20s",
                            event.getTime().toLocalTime(),
                            station.getName(),
                            "Generate " + count,
                            "Current count passenger " + getCountPassenger()

                    ));
                });
    }

    private void clean() {
        passengers.forEach((route, quePassenger) -> quePassenger.forEach((direction, que) -> que.clear()));
    }

    @Override
    public void registerRoute(BusRouteDecorator route) {
        if (routes.contains(route)) {
            return;
        }
        //Use from optimize generation passenger
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
            log.trace(station + "Gen new passenger\t" + direction + "\t" + passenger);
        }
    }

    private BusRouteDecorator getRandomRoute() {
        List<BusRouteDecorator> buff = new ArrayList<>();
        buff.addAll(routes);
        Collections.shuffle(buff);
        return buff.stream().findFirst().get();
    }

    private long getCountPassenger() {
        return countPassenger;
    }

    public BusStation getStation() {
        return station;
    }
}