package ua.telesens.ostapenko.systemimitation.model.internal;

import ua.telesens.ostapenko.systemimitation.api.decorator.RouteTransportPublic;
import ua.telesens.ostapenko.systemimitation.api.observer.SystemImitationObservable;
import ua.telesens.ostapenko.systemimitation.model.internal.observer.BusObserver;
import ua.telesens.ostapenko.systemimitation.model.internal.observer.StationObserver;
import ua.telesens.ostapenko.systemimitation.service.ScheduleManager;

import java.time.LocalTime;
import java.util.*;

/**
 * @author root
 * @since 29.12.15
 */
public class RouteDecorator implements RouteTransportPublic {

    private final Route route;
    private Collection<Station> stations = Collections.emptyList();
    private Collection<BusObserver> busObservers = Collections.emptyList();
    private Collection<StationObserver> stationObservers = Collections.emptyList();

    private RouteDecorator(Route route, SystemImitationObservable observable, ScheduleManager scheduleManager) {
        this.route = route;
        this.stations = new ArrayList<>();
        this.busObservers = new ArrayList<>();
        this.stationObservers = new ArrayList<>();
        parceArcStation();
        linkTo(observable);
        scheduleManager.createSchedule(this);
    }

    public static RouteDecorator of(Route route, SystemImitationObservable observable, ScheduleManager scheduleManager) {
        return new RouteDecorator(route, observable, scheduleManager);
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

    public Collection<Station> getStations() {
        return stations;
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

    //Use from generation schedule and from defend duplicate station and bus
    public void linkTo(SystemImitationObservable observable) {
        getStations().
                forEach(station -> stationObservers.add((StationObserver) observable.register(StationObserver.of(station, this))));
        route.getBuses()
                .forEach(bus -> busObservers.add((BusObserver) observable.register(BusObserver.of(bus, this))));
    }

    private void parceArcStation() {
        List<RouteArc> arcs = (List<RouteArc>) getArcList();
        int size = arcs.size();
        for (int i = 0; i < size; i++) {
            stations.add(arcs.get(i).getStart());
            if (i == size - 1) {
                stations.add(arcs.get(i).getEnd());
            }
        }
    }

    @Override
    public String toString() {
        return "route=" + route.getName();
    }
}
