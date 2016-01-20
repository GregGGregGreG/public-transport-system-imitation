package ua.telesens.ostapenko.systemimitation.model.internal.observer;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ua.telesens.ostapenko.systemimitation.api.StationStatistic;
import ua.telesens.ostapenko.systemimitation.api.observer.PassengerObservable;
import ua.telesens.ostapenko.systemimitation.api.observer.SystemImitationObserver;
import ua.telesens.ostapenko.systemimitation.model.internal.*;
import ua.telesens.ostapenko.systemimitation.service.PassengerGenerator;

import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author root
 * @since 14.12.15
 */
@Slf4j
@EqualsAndHashCode(exclude = {"passengers", "routes", "countPassengers", "lazyPassengers", "generator"})
public class StationObserver implements SystemImitationObserver, PassengerObservable, StationStatistic {

    @Getter
    private final Station station;
    //Use optimization find que passenger
    private Map<RouteDecorator, Map<RouteDirection, ConcurrentLinkedDeque<Passenger>>> passengers = new HashMap<>();
    @Getter
    private PassengerGenerator generator;
    //Use from optimize process generation passenger
    private List<RouteDecorator> routes = new ArrayList<>();
    @Getter
    private int countPassengers;
    // Statistic source
    @Getter
    private int lazyPassengers;

    private StationObserver(Station station, RouteDecorator route) {
        this.station = station;
        this.registerRoute(route);
        this.generator = new PassengerGenerator(this);
    }

    public static StationObserver of(Station station, RouteDecorator route) {
        Objects.requireNonNull(station);
        Objects.requireNonNull(route);
        return new StationObserver(station, route);
    }


    // Operations from passenger

    public void addPassenger(List<Passenger> passengers) {
        Objects.requireNonNull(passengers);
        passengers.forEach(this::addPassenger);
    }

    public void addPassenger(Passenger passenger) {
        Objects.requireNonNull(passenger);
        if (passenger.getStation().equals(this)) {
            throw new IllegalStateException(station + "\tNot valid\t" + passenger);
        }
        //Get Route
        Map<RouteDirection, ConcurrentLinkedDeque<Passenger>> routeMovementQueueMap = passengers.get(passenger.getRoute());
        routeMovementQueueMap
                //Get direction route
                .get(passenger.getDirection())
                //Add passenger toStation que
                .add(passenger);
        //Increment count passenger
        countPassengers++;
    }

    public Passenger getPassenger(RouteDecorator route, RouteDirection direction) {
        Objects.requireNonNull(route);
        Objects.requireNonNull(direction);
        if (hasNextPassenger(route, direction)) {
            countPassengers--;
            return this.passengers.get(route).get(direction).poll();
        }
        throw new IllegalStateException();
    }

    public boolean hasNextPassenger(RouteDecorator route, RouteDirection direction) {
        Objects.requireNonNull(route);
        Objects.requireNonNull(direction);
        return !passengers.get(route).get(direction).isEmpty();
    }

    @Override
    public void notifyAllPassenger() {
        passengers.forEach((route, directions) -> directions.forEach(this::notifyAllPassenger));
    }

    private void notifyAllPassenger(RouteDirection direction, ConcurrentLinkedDeque<Passenger> passengers) {
        passengers
                .forEach(passenger -> {
                    passenger.updateTimer();
                    if (passenger.isGone()) {
                        log.info(String.valueOf(passenger));
                        passengers.remove(passenger);
                        lazyPassengers++;
                        countPassengers--;
                    }
                });
    }


    // Handler event

    @Override
    public void updateEvent(ImitationEvent event) {
        Objects.requireNonNull(event);
        DayType dayType = event.getDayType();
        LocalTime time = event.getTime().toLocalTime();
        //Check lazy passenger
        notifyAllPassenger();
        //Generation new passengers
        addPassenger(generator.execute(dayType, time));
    }


    // Operations from route

    public void registerRoute(RouteDecorator route) {
        //Check route
        Objects.requireNonNull(route);
        if (routes.contains(route)) {
            return;
        }
        // Use from optimize generation passenger
        routes.add(route);
        //Add route and specific que passenger from route
        passengers.put(route, route.getType().getQue());
    }

    public List<RouteDecorator> getRoutes() {
        return Collections.unmodifiableList(routes);
    }


    //  String conversion

    @Override
    public String toString() {
        return String.valueOf(station);
    }
}