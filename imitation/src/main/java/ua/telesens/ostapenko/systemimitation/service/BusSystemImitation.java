package ua.telesens.ostapenko.systemimitation.service;

import ua.telesens.ostapenko.systemimitation.api.Report;
import ua.telesens.ostapenko.systemimitation.api.SystemImitation;
import ua.telesens.ostapenko.systemimitation.api.observer.SystemImitationObservable;
import ua.telesens.ostapenko.systemimitation.api.observer.SystemImitationObserver;
import ua.telesens.ostapenko.systemimitation.model.internal.BusRoute;
import ua.telesens.ostapenko.systemimitation.model.internal.BusRouteDecorator;
import ua.telesens.ostapenko.systemimitation.model.internal.DayType;
import ua.telesens.ostapenko.systemimitation.model.internal.ImitationEvent;
import ua.telesens.ostapenko.systemimitation.model.internal.observer.BusObserver;
import ua.telesens.ostapenko.systemimitation.model.internal.observer.StationObserver;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author root
 * @since 10.12.15
 */
public class BusSystemImitation implements SystemImitation, SystemImitationObservable {

    private static final ScheduleManager SCHEDULE_MANAGER = new ScheduleManager();

    private List<BusRouteDecorator> routes = Collections.emptyList();
    private List<SystemImitationObserver> stations = Collections.emptyList();
    private List<SystemImitationObserver> buses = Collections.emptyList();
    private LocalDateTime currentTime;
    private LocalDateTime starting;
    private LocalDateTime end;

    public BusSystemImitation(List<BusRoute> routes, LocalDateTime starting, LocalDateTime end) {
        this.routes = new ArrayList<>();
        this.stations = new ArrayList<>();
        this.buses = new ArrayList<>();
        this.starting = starting;
        this.currentTime = starting;
        this.end = end;
        initRoutes(routes);
    }

    @Override
    public Report run() {
        while (currentTime.isBefore(end)) {
            notifyAllObservers();
            currentTime = currentTime.plusMinutes(1);
        }
        return new Report() {
        };
    }

    @Override
    public void stop() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void status() {

    }

    @Override
    public SystemImitationObserver register(SystemImitationObserver observer) {
        if (observer.getClass().equals(BusObserver.class)) {
            return addObserver(buses, observer);
        } else if (observer.getClass().equals(StationObserver.class)) {
            return addObserver(stations, observer);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public void remove(SystemImitationObserver observer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void notifyAllObservers() {
        ImitationEvent event = createEvent();
        stations
                .parallelStream()
                .forEach(observer -> observer.updateEvent(event));
        buses
                .parallelStream()
                .forEach(observer -> observer.updateEvent(event));
    }

    private ImitationEvent createEvent() {
        return ImitationEvent.of(currentTime, DayType.to(currentTime.getDayOfWeek()));
    }

    private void initRoutes(List<BusRoute> routes) {
        routes.parallelStream().forEach(route -> this.routes.add(BusRouteDecorator.of(route, this, SCHEDULE_MANAGER)));
    }

    private SystemImitationObserver addObserver(List<SystemImitationObserver> observers, SystemImitationObserver observer) {
        if (observers.contains(observer)) {
            return observers.get(observers.indexOf(observer));
        }
        observers.add(observer);
        return observer;
    }
}
