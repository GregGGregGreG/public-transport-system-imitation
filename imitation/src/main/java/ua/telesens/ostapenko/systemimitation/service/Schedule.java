package ua.telesens.ostapenko.systemimitation.service;

import lombok.extern.slf4j.Slf4j;
import ua.telesens.ostapenko.systemimitation.model.internal.*;
import ua.telesens.ostapenko.systemimitation.model.internal.observer.BusObserver;
import ua.telesens.ostapenko.systemimitation.model.internal.observer.StationObserver;

import java.time.LocalTime;
import java.util.*;

import static ua.telesens.ostapenko.systemimitation.model.internal.RouteMovement.STRAIGHT;
import static ua.telesens.ostapenko.systemimitation.model.internal.RouteType.LINE;
import static ua.telesens.ostapenko.systemimitation.model.internal.StationState.FINAL;
import static ua.telesens.ostapenko.systemimitation.model.internal.StationState.INTERMEDIATE;

/**
 * @author root
 * @since 09.12.15
 */
@Slf4j
public class Schedule {

    private Map<Route, List<ScheduleNode>> schedule = Collections.emptyMap();
    private List<Route> routes = Collections.emptyList();

    private Schedule(List<Route> routes) {
        this.routes = routes;
        this.schedule = new HashMap<>();
    }

    public static Schedule of(List<Route> routes) {
        return new Schedule(routes);
    }

    public Map<Route, List<ScheduleNode>> execute() {
        routes.stream().forEach(route -> schedule.put(route, getSchedule(route)));
        return schedule;
    }

    //Calculation ending movement from rout
    private List<ScheduleNode> getSchedule(Route route) {
        List<ScheduleNode> result = new ArrayList<>();

        RouteType routeType = route.getType();
        List<BusObserver> busList = (List<BusObserver>) route.getBusList();
        List<Arc> arcList = (List<Arc>) route.getArcList();
        int countBus = route.getBusList().size();
        int countRace = route.getCountRace();
        LocalTime time = route.getStarting();
        long timeOut = route.getTimeOut();
        long interval = route.getInterval();

        Iterator<Arc> arcItr;
        BusObserver bus;
        Arc arc;
        RouteMovement routeMovement = STRAIGHT;
        for (int i = 0; i < countBus; i++) {
            bus = busList.get(i);

            for (int j = 0; j < countRace; j++) {
                arcItr = arcList.iterator();

                while (arcItr.hasNext()) {
                    arc = arcItr.next();
                    result.add(new ScheduleNode(
                            time,
                            bus,
                            arc.getInitialStation(),
                            INTERMEDIATE,
                            routeMovement,
                            route
                    ));

                    time = time.plusMinutes(arc.getInterval());

                    if (!arcItr.hasNext()) {
                        result.add(new ScheduleNode(
                                time,
                                bus,
                                arc.getFinalStation(),
                                FINAL,
                                routeMovement,
                                route
                        ));
                        //Set ending movement route
                        route.setEnding(time);

                        time = time.plusMinutes(timeOut);
                    }
                }
                //// FIXME: 15.12.15 Eb.. if
                if (routeType.equals(LINE)) {
                    arcListRevert(arcList);
                    //Set way route
                    routeMovement = routeMovement.equals(STRAIGHT) ? RouteMovement.BACK : RouteMovement.STRAIGHT;
                }
            }
            time = route.getStarting().plusMinutes(interval);
        }
        return result;
    }

    private void arcListRevert(List<Arc> list) {
        Collections.reverse(list);
        list.forEach(this::swapStation);
    }

    private void swapStation(Arc arc) {
        StationObserver buf = arc.getInitialStation();
        arc.setInitialStation(arc.getFinalStation());
        arc.setFinalStation(buf);
    }
}
