package ua.telesens.ostapenko.systemimitation.service;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ua.telesens.ostapenko.systemimitation.api.Logger;
import ua.telesens.ostapenko.systemimitation.api.ProgressBar;
import ua.telesens.ostapenko.systemimitation.api.SystemImitation;
import ua.telesens.ostapenko.systemimitation.api.observer.SystemImitationObservable;
import ua.telesens.ostapenko.systemimitation.api.observer.SystemImitationObserver;
import ua.telesens.ostapenko.systemimitation.model.internal.DayType;
import ua.telesens.ostapenko.systemimitation.model.internal.ImitationEvent;
import ua.telesens.ostapenko.systemimitation.model.internal.ImitationSource;
import ua.telesens.ostapenko.systemimitation.model.internal.RouteDecorator;
import ua.telesens.ostapenko.systemimitation.model.internal.observer.BusObserver;
import ua.telesens.ostapenko.systemimitation.model.internal.observer.StationObserver;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author root
 * @since 10.12.15
 */
@Slf4j
@EqualsAndHashCode
public class BusSystemImitation implements SystemImitation, SystemImitationObservable {

    public static int minTimePassengerWaiting;
    public static int maxTimePassengerWaiting;

    public static final int IMITATION_STEP = 1;
    private static final String FORMAT = "%-40s%-20s";

    private ProgressBar pb;
    private Logger logger;
    private ImitationSource source;
    private List<RouteDecorator> routes = new ArrayList<>();
    private List<StationObserver> stations = new ArrayList<>();
    private List<BusObserver> buses = new ArrayList<>();
    private LocalDateTime imitationTime;
    @Getter
    private LocalDateTime starting;
    @Getter
    private LocalDateTime end;

    public BusSystemImitation(ImitationSource source) {
        this.source = source;
        this.starting = source.getStarting();
        this.imitationTime = starting;
        this.end = source.getEnd();
        this.pb = new ProgressBarImpl(starting, end);
        minTimePassengerWaiting = source.getMinTimePassengerWaiting();
        maxTimePassengerWaiting = source.getMaxTimePassengerWaiting();
        initRoutes();
    }

    @Override
    public void run() {
        checkImitationLogger();
        pb.start();
        while (hasNextStep()) {
            pb.step(imitationTime);
            notifyAllObservers();
            nextStep();
        }
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
        stations.forEach(observer -> observer.updateEvent(event));
        buses.forEach(observer -> observer.updateEvent(event));
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
        buses.forEach(busObserver -> busObserver.setImitationLogger(logger));
    }

    public List<RouteDecorator> getRoutes() {
        return Collections.unmodifiableList(routes);
    }

    public List<StationObserver> getStations() {
        return Collections.unmodifiableList(stations);
    }

    public List<BusObserver> getBuses() {
        return Collections.unmodifiableList(buses);
    }

    private boolean hasNextStep() {
        return !imitationTime.isAfter(end);
    }

    private void nextStep() {
        imitationTime = imitationTime.plusMinutes(IMITATION_STEP);
    }

    private ImitationEvent createEvent() {
        return ImitationEvent.of(imitationTime, DayType.to(imitationTime.getDayOfWeek()));
    }

    private void initRoutes() {
        source
                .getRouteList()
                .getRoutes()
                .forEach(route -> this.routes.add(RouteDecorator.of(route, this)));
    }

    private <T> T addObserver(List<T> observers, T observer) {
        if (observers.contains(observer)) {
            return observers.get(observers.indexOf(observer));
        }
        observers.add(observer);
        return observer;
    }

    private void checkImitationLogger() {
        if (Objects.isNull(logger)) {
            log.info("Don't set imitation logger");
        } else {
            log.info(String.format(FORMAT, "Current imitation logger", logger));
        }
    }
}