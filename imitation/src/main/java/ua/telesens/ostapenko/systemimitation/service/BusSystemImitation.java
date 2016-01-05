package ua.telesens.ostapenko.systemimitation.service;

import com.google.common.collect.Iterables;
import ua.telesens.ostapenko.systemimitation.api.Report;
import ua.telesens.ostapenko.systemimitation.api.SystemImitation;
import ua.telesens.ostapenko.systemimitation.api.observer.SystemImitationObservable;
import ua.telesens.ostapenko.systemimitation.api.observer.SystemImitationObserver;
import ua.telesens.ostapenko.systemimitation.model.internal.*;
import ua.telesens.ostapenko.systemimitation.model.internal.observer.BusObserver;
import ua.telesens.ostapenko.systemimitation.model.internal.observer.StationObserver;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * @author root
 * @since 10.12.15
 */
public class BusSystemImitation implements SystemImitation, SystemImitationObservable {

    private static final ScheduleManager SCHEDULE_MANAGER = new ScheduleManager();

    private List<BusRouteDecorator> routes = Collections.emptyList();
    private List<StationObserver> stations = Collections.emptyList();
    private List<BusObserver> buses = Collections.emptyList();
    private LocalDateTime currentTime;
    private LocalDateTime starting;
    private LocalDateTime end;
    private Map<DayType, LocalTime> endDay = Collections.emptyMap();

    public BusSystemImitation(List<BusRoute> routes, LocalDateTime starting, LocalDateTime end) {
        this.routes = new ArrayList<>();
        this.stations = new ArrayList<>();
        this.buses = new ArrayList<>();
        this.starting = starting;
        this.currentTime = starting;
        this.end = end;
        this.endDay = new HashMap<>();
        initRoutes(routes);
        initEndDay();
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
            return addObserver(buses, (BusObserver) observer);
        } else if (observer.getClass().equals(StationObserver.class)) {
            return addObserver(stations, (StationObserver) observer);
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

    private void initEndDay() {
        stations.forEach(station -> station.getStation().getRules().forEach((this::addEndDay)));
    }

    private void addEndDay(DayType day, List<PassengerGenerationRule> rules) {
        Optional<PassengerGenerationRule> rule = rules
                .stream()
                .max(PassengerGenerationRule::compareTo);

        LocalTime time = Iterables.getLast(rule.get().getSchedule());

        if (Objects.isNull(endDay.get(day))) {
            endDay.put(day, time);
        } else if (time.isAfter(endDay.get(day))) {
            endDay.put(day, time);
        }
    }

    private <T> T addObserver(List<T> observers, T observer) {
        if (observers.contains(observer)) {
            return observers.get(observers.indexOf(observer));
        }
        observers.add(observer);
        return observer;
    }
}
