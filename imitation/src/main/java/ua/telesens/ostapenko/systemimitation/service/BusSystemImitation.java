package ua.telesens.ostapenko.systemimitation.service;

import ua.telesens.ostapenko.systemimitation.api.Logger;
import ua.telesens.ostapenko.systemimitation.api.Report;
import ua.telesens.ostapenko.systemimitation.api.RouteManager;
import ua.telesens.ostapenko.systemimitation.api.SystemImitation;
import ua.telesens.ostapenko.systemimitation.api.observer.SystemImitationObservable;
import ua.telesens.ostapenko.systemimitation.api.observer.SystemImitationObserver;
import ua.telesens.ostapenko.systemimitation.model.internal.Arc;
import ua.telesens.ostapenko.systemimitation.model.internal.Route;
import ua.telesens.ostapenko.systemimitation.model.internal.ScheduleNode;
import ua.telesens.ostapenko.systemimitation.model.internal.observer.BusObserver;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author root
 * @since 10.12.15
 */
public class BusSystemImitation implements SystemImitation, SystemImitationObservable {

    private List<Route> routes;
    private List<SystemImitationObserver> observers = Collections.emptyList();
    private Map<Route, List<ScheduleNode>> schedule = Collections.emptyMap();
    private LocalDateTime currentTime;
    private LocalDateTime starting;
    private LocalDateTime end;

    public BusSystemImitation(List<Route> routes, LocalDateTime starting, LocalDateTime end) {
        this.observers = new ArrayList<>();
        this.routes = routes;
        this.starting = starting;
        this.currentTime = starting;
        this.end = end;
    }

    @Override
    public Report run() {
        registerRoutFromStation();
        registerRoutFromBus();
        schedule = Schedule.of(routes).execute();

        System.out.println("Start imitation in " + starting);
        System.out.println("Count busObserver \t" + getCountBus());
        System.out.println("Count stationObserver \t" + getCountStation());

        while (currentTime.isBefore(end)) {
            notifyTransportObservers();
            currentTime = currentTime.plusMinutes(1);
        }

        System.out.println("Stop imitation in " + currentTime);

        return new Report() {
        };
    }

    //Use from set passengers int que on station
    private void registerRoutFromBus() {
        for (Route route : routes) {
            List<BusObserver> busList = (List<BusObserver>) route.getBusList();
            for (RouteManager bus : busList) {
                bus.registerRoute(route);
            }
        }
    }

    //Use from set passengers int que on bus
    private void registerRoutFromStation() {
        for (Route route : routes) {
            List<Arc> arcs = (List<Arc>) route.getArcList();
            for (int i = 0; i < arcs.size(); i++) {
                arcs.get(i).getInitialStation().registerRoute(route);
                if ((i + 1) == arcs.size()) {
                    arcs.get(i).getFinalStation().registerRoute(route);
                }
            }
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
    public void addLogger(Logger logger) {

    }

    @Override
    public void registerTransportObserver(SystemImitationObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeTransportObserver(SystemImitationObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyTransportObservers() {
        for (SystemImitationObserver observer : observers) {
            observer.updateTime(currentTime, schedule);
        }
    }

    // FIXME: 17.12.15 Add system statistic interface
    public int getCountBus() {
        int result = 0;
        for (Route route : routes) {
            result += route.getBusList().size();
        }
        return result;
    }

    public int getCountStation() {
        int result = 0;
        for (Route route : routes) {
            result += (route.getArcList().size() + 1);
        }
        return result;
    }
}
