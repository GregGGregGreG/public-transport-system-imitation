package ua.telesens.ostapenko.systemimitation.model.internal.observer;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ua.telesens.ostapenko.systemimitation.api.observer.PassengerObservable;
import ua.telesens.ostapenko.systemimitation.api.observer.SystemImitationObserver;
import ua.telesens.ostapenko.systemimitation.model.internal.*;
import ua.telesens.ostapenko.systemimitation.service.PassengerGenerator;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author root
 * @since 14.12.15
 */
@Slf4j
@EqualsAndHashCode(exclude = {"passengers", "routes", "countPassenger", "lazyPassenger", "passengerGenerator"})
public class StationObserver implements SystemImitationObserver, PassengerObservable {

    @Getter
    private final Station station;
    //Use optimization find que passenger
    private Map<RouteDecorator, Map<RouteDirection, ConcurrentLinkedDeque<Passenger>>> passengers = new HashMap<>();
    @Getter
    private PassengerGenerator passengerGenerator;
    //Use from optimize process generation passenger
    @Getter
    private List<RouteDecorator> routes = new ArrayList<>();
    private int lazyPassenger;
    @Getter
    private int countPassenger;

    private StationObserver(Station station, RouteDecorator route) {
        this.station = station;
        this.registerRoute(route);
        this.passengerGenerator = new PassengerGenerator(this);
    }

    public static StationObserver of(Station station, RouteDecorator route) {
        return new StationObserver(station, route);
    }

    public void addPassenger(List<Passenger> passengers) throws IllegalStateException {
        passengers.forEach(this::addPassenger);
    }

    public void addPassenger(Passenger passenger) throws IllegalStateException {
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
        countPassenger++;
    }

    public Passenger getPassenger(RouteDecorator route, RouteDirection direction) {
        if (hasNextPassenger(route, direction)) {
            countPassenger--;
            return this.passengers.get(route).get(direction).poll();
        }
        throw new IllegalStateException();
    }

    public boolean hasNextPassenger(RouteDecorator route, RouteDirection direction) {
        return !passengers.get(route).get(direction).isEmpty();
    }

    @Override
    public void updateEvent(ImitationEvent event) {
        DayType dayType = event.getDayType();
        LocalTime time = event.getTime().toLocalTime();
        notifyAllPassenger();
        addPassenger(passengerGenerator.execute(dayType, time));
    }

    public void clean() {
        passengers.forEach((route, quePassenger) -> quePassenger.forEach((direction, que) -> que.clear()));
        countPassenger = 0;
        log.debug(String.format("%-12s%-20s", station.getName(), "Remove All Passenger"));
    }

    public void registerRoute(RouteDecorator route) {
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

    public int getLazyPassenger() {
        return lazyPassenger;
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
                        lazyPassenger++;
                        countPassenger--;
                    }
                });
    }


}