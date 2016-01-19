package ua.telesens.ostapenko.systemimitation.model.internal;

import lombok.extern.slf4j.Slf4j;
import ua.telesens.ostapenko.systemimitation.api.decorator.RouteTransportPublic;
import ua.telesens.ostapenko.systemimitation.api.observer.SystemImitationObservable;
import ua.telesens.ostapenko.systemimitation.model.internal.observer.BusObserver;
import ua.telesens.ostapenko.systemimitation.model.internal.observer.StationObserver;
import ua.telesens.ostapenko.systemimitation.service.ScheduleManager;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author root
 * @since 29.12.15
 */
@Slf4j
public class RouteDecorator implements RouteTransportPublic {

    private static final ScheduleManager SCHEDULE_MANAGER = new ScheduleManager();
    private final Route route;
    private Collection<Station> stations = new ArrayList<>();
    private Collection<BusObserver> busObservers = new ArrayList<>();
    private Collection<StationObserver> stationObservers = new ArrayList<>();

    private RouteDecorator(Route route, SystemImitationObservable observable) {
        this.route = route;
        parseArcStation();
        linkTo(observable);
        SCHEDULE_MANAGER.create(this);
    }

    public static RouteDecorator of(Route route, SystemImitationObservable observable) {
        return new RouteDecorator(route, observable);
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
    public List<RouteTrafficRuleList> getRules() {
        return route.getRules();
    }

    public Collection<Station> getStations() {
        return Collections.unmodifiableCollection(stations);
    }

    public Collection<BusObserver> getBusObservers() {
        return Collections.unmodifiableCollection(busObservers);
    }

    public void setBusObservers(Collection<BusObserver> busObservers) {
        this.busObservers = busObservers;
    }

    public Collection<StationObserver> getStationObservers() {
        return Collections.unmodifiableCollection(stationObservers);
    }

    public void setStationObservers(Collection<StationObserver> stationObservers) {
        this.stationObservers = stationObservers;
    }

    //Use from generation schedule and from defend duplicate station and bus
    private void linkTo(SystemImitationObservable observable) {
        getStations().
                forEach(station -> stationObservers.add((StationObserver) observable.register(StationObserver.of(station, this))));
        route.getBuses()
                .forEach(bus -> busObservers.add((BusObserver) observable.register(BusObserver.of(bus, this))));
    }

    private void parseArcStation() {
        List<RouteArc> arcs = getArcList().stream()
                .sorted((o1, o2) -> Integer.compare(o1.getNumber(), o2.getNumber()))
                .collect(Collectors.toList());

        log.debug(String.format("%-40s%-80s", "Parse route arc", arcs));

        int size = arcs.size();
        for (int i = 0; i < size; i++) {
            stations.add(arcs.get(i).getStart());
            if (i == size - 1) {
                stations.add(arcs.get(i).getEnd());
            }
        }

        log.debug(String.format("%-40s%-80s", "Init station", stations));
    }

    @Override
    public String toString() {
        return "route=" + route.getName();
    }
}
