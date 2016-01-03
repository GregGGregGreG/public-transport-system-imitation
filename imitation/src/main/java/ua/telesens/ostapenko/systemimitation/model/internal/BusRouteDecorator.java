package ua.telesens.ostapenko.systemimitation.model.internal;

import lombok.ToString;
import ua.telesens.ostapenko.systemimitation.api.decorator.RouteTransportPublic;
import ua.telesens.ostapenko.systemimitation.api.observer.SystemImitationObservable;
import ua.telesens.ostapenko.systemimitation.model.internal.observer.BusObserver;
import ua.telesens.ostapenko.systemimitation.model.internal.observer.StationObserver;
import ua.telesens.ostapenko.systemimitation.service.ScheduleManager;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.IntStream;

/**
 * @author root
 * @since 29.12.15
 */
@ToString(exclude = {"stationObservers","busObservers"})
public class BusRouteDecorator implements RouteTransportPublic {

    private final BusRoute route;
    private LocalTime ending;
    private Collection<BusStation> stations = Collections.emptyList();
    private Collection<BusObserver> busObservers = Collections.emptyList();
    private Collection<StationObserver> stationObservers = Collections.emptyList();

    private BusRouteDecorator(BusRoute route, SystemImitationObservable observable, ScheduleManager scheduleManager) {
        this.route = route;
        this.stations = new ArrayList<>();
        this.busObservers = new ArrayList<>();
        this.stationObservers = new ArrayList<>();
        parceArcStation();
        linkTo(observable);
        scheduleManager.createSchedule(this);
    }

    public static BusRouteDecorator of(BusRoute route, SystemImitationObservable observable, ScheduleManager scheduleManager) {
        return new BusRouteDecorator(route, observable, scheduleManager);
    }

    @Override
    public String getName() {
        return route.getName();
    }

    @Override
    public RouteType getType() {
        return route.getType();
    }

    @Override
    public Collection<RouteArc> getArcList() {
        return route.getArcList();
    }

    @Override
    public Collection<Bus> getBuses() {
        return route.getBuses();
    }

    @Override
    public LocalTime getStarting() {
        return route.getStarting();
    }

    @Override
    public Map<DayType, List<RouteTrafficRule>> getRules() {
        return route.getRules();
    }

    public Collection<BusStation> getStations() {
        return stations;
    }

    public LocalTime getEnding() {
        return ending;
    }

    public void setEnding(LocalTime ending) {
        this.ending = ending;
    }

    public Collection<BusObserver> getBusObservers() {
        return busObservers;
    }

    public void setBusObservers(Collection<BusObserver> busObservers) {
        this.busObservers = busObservers;
    }

    public Collection<StationObserver> getStationObservers() {
        return stationObservers;
    }

    public void setStationObservers(Collection<StationObserver> stationObservers) {
        this.stationObservers = stationObservers;
    }

    //Use from generation schedule
    public void linkTo(SystemImitationObservable observable) {
        getStations().
                parallelStream().
                forEach(station -> stationObservers.add((StationObserver) observable.register(new StationObserver(station, this))));

        route
                .getBuses()
                .parallelStream()
                .forEach(bus -> busObservers.add((BusObserver) observable.register(new BusObserver(bus, this))));
    }

    private void parceArcStation() {
        List<RouteArc> arcs = (List<RouteArc>) route.getArcList();
        IntStream
                .range(0, arcs.size())
                .parallel()
                .forEach(value -> initStation(arcs, value));
    }

    private void initStation(List<RouteArc> arcs, int value) {
        stations.add(arcs.get(value).getStart());
        if (value == arcs.size() - 1) {
            stations.add(arcs.get(value).getEnd());
        }
    }
}
