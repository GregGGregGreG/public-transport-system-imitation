package ua.telesens.ostapenko.systemimitation.service;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Getter
@EqualsAndHashCode
public class BusSystemImitation implements SystemImitation, SystemImitationObservable {

    private static final ScheduleManager SCHEDULE_MANAGER = new ScheduleManager();
    private static final int IMITATION_STEP = 1;
    private RouteList source;
    private List<RouteDecorator> routes = Collections.emptyList();
    private List<StationObserver> stations = Collections.emptyList();
    private List<BusObserver> buses = Collections.emptyList();
    private LocalDateTime imitationTime;
    private LocalDateTime starting;
    private LocalDateTime end;
    private Map<DayType, LocalTime> endDay = Collections.emptyMap();
    private Map<DayType, LocalTime> startDay = Collections.emptyMap();

    public BusSystemImitation(RouteList source, LocalDateTime starting, LocalDateTime end) {
        this.source = source;
        this.routes = new ArrayList<>();
        this.stations = new ArrayList<>();
        this.buses = new ArrayList<>();
        this.starting = starting;
        this.imitationTime = starting;
        this.end = end;
        this.endDay = new HashMap<>();
        this.startDay = new HashMap<>();
        initRoutes();
        initEndDay();
        initStartDay();
    }

    @Override
    public void run() {
        while (hasNextStep()) {
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
        try {
            ImitationEvent event = createEvent();
            stations
                    .parallelStream()
                    .forEach(observer -> observer.updateEvent(event));
            buses
                    .parallelStream()
                    .forEach(observer -> observer.updateEvent(event));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    private boolean hasNextStep() {
        return imitationTime.isBefore(end);
    }

    private void nextStep() {
        DayType dayType = DayType.to(imitationTime.getDayOfWeek());
        LocalTime time = imitationTime.toLocalTime();
        if (endDay.get(dayType).equals(time)) {
            imitationTime = LocalDateTime.of(imitationTime.toLocalDate().plusDays(1), startDay.get(dayType));
            if (hasNextStep()) {
                log.debug("----->>>NEW DAY IN SYSTEM\t" + imitationTime);
                stations.forEach(StationObserver::clean);
            }
        } else {
            imitationTime = imitationTime.plusMinutes(IMITATION_STEP);
        }
    }

    private ImitationEvent createEvent() {
        return ImitationEvent.of(imitationTime, DayType.to(imitationTime.getDayOfWeek()));
    }

    private void initRoutes() {
        source
                .getRoutes()
                .parallelStream().
                forEach(route -> this.routes.add(RouteDecorator.of(route, this, SCHEDULE_MANAGER)));
    }

    private void initStartDay() {
        stations.forEach(station -> station.getPassengerGenerator().getSchedule().forEach((this::addStartDay)));
    }

    private void addStartDay(DayType day, List<PassengerGenerationSchedule> rules) {
        PassengerGenerationSchedule rule = rules
                .stream()
                .min((o1, o2) -> o1.getStart().compareTo(o2.getStart()))
                .get();

        LocalTime time = rule.getStart();


        if (Objects.isNull(startDay.get(day))) {
            startDay.put(day, time);
        } else if (time.isBefore(startDay.get(day))) {
            startDay.put(day, time);
        }
    }

    private void initEndDay() {
        stations.forEach(station -> station.getPassengerGenerator().getSchedule().forEach((this::addEndDay)));
    }

    private void addEndDay(DayType day, List<PassengerGenerationSchedule> rules) {
        PassengerGenerationSchedule rule = rules
                .stream()
                .max((o1, o2) -> o1.getEnd().compareTo(o2.getEnd()))
                .get();


        LocalTime time = rule.getEnd();

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
