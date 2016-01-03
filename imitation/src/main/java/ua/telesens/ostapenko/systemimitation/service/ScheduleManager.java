package ua.telesens.ostapenko.systemimitation.service;

import ua.telesens.ostapenko.systemimitation.model.internal.*;
import ua.telesens.ostapenko.systemimitation.model.internal.observer.BusObserver;
import ua.telesens.ostapenko.systemimitation.model.internal.observer.StationObserver;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static ua.telesens.ostapenko.systemimitation.model.internal.RouteDirection.BACK;
import static ua.telesens.ostapenko.systemimitation.model.internal.RouteDirection.STRAIGHT;
import static ua.telesens.ostapenko.systemimitation.model.internal.RouteStationType.FINAL;
import static ua.telesens.ostapenko.systemimitation.model.internal.RouteStationType.INTERMEDIATE;
import static ua.telesens.ostapenko.systemimitation.model.internal.RouteType.CYCLE;

/**
 * @author root
 * @since 03.01.16
 */
public class ScheduleManager {

    public void createSchedule(BusRouteDecorator route) {
        LocalTime starting;
        LocalTime time;
        LocalTime ruleEndTime;
        RouteDirection direction;
        List<ScheduleLine> scheduleLines;
        RouteStationType routeStationType;
        List<StationObserver> stationObservers = (List<StationObserver>) route.getStationObservers();
        List<RouteArc> arcs = (List<RouteArc>) route.getArcList();
        List<BusObserver> busObservers = (List<BusObserver>) route.getBusObservers();

        for (DayType dayType : DayType.values()) {
            starting = route.getStarting();
            time = starting;
            ruleEndTime = starting;

            // Get route from day type
            if (!route.getRules().containsKey(dayType)) {
                continue;
            }

            List<RouteTrafficRule> rules = route.getRules().get(dayType);
            scheduleLines = new ArrayList<>();

            for (RouteTrafficRule rule : rules) {

                for (int i = 0; i < rule.getCountBus(); i++) {
                    //Get last position bus in route
                    List<ScheduleLine> buff = busObservers.get(i).getSchedules().get(dayType);
                    if (Objects.nonNull(buff)) {
                        if (buff.get(buff.size() - 1).getDirection().equals(BACK)) {
                            direction = STRAIGHT;
                        } else {
                            direction = BACK;
                        }
                    } else {
                        direction = STRAIGHT;
                    }

                    for (int i1 = 0; i1 < rule.getCountRace(); i1++) {

                        for (int i2 = 0; i2 < stationObservers.size(); i2++) {

                            routeStationType = i2 == stationObservers.size() - 1
                                    ? FINAL
                                    : INTERMEDIATE;

                            scheduleLines.add(ScheduleLine.of(time, busObservers.get(i), direction, stationObservers.get(i2), routeStationType));

                            time = i2 == stationObservers.size() - 1
                                    //Pause between race
                                    ? time.plusMinutes(rule.getTimeOut().getMinute())
                                    //Pause between station
                                    : time.plusMinutes(arcs.get(i2).getInterval().getMinute());
                        }
                        //If route is cycle reverse stations
                        if (route.getType().equals(CYCLE)) {
                            Collections.reverse(stationObservers);
                            if (direction.equals(BACK)) {
                                direction = STRAIGHT;
                            } else {
                                direction = BACK;
                            }
                        }
                    }

                    busObservers.get(i).setSchedules(dayType, scheduleLines);
                    scheduleLines.clear();
                    starting = starting.plusMinutes(rule.getInterval().getMinute());
                    ruleEndTime = time;
                    time = starting;
                }
                starting = ruleEndTime;
                time = starting;
            }
        }
    }
}

