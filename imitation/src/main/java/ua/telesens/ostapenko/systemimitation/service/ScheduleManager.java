package ua.telesens.ostapenko.systemimitation.service;

import ua.telesens.ostapenko.systemimitation.model.internal.*;
import ua.telesens.ostapenko.systemimitation.model.internal.observer.BusObserver;
import ua.telesens.ostapenko.systemimitation.model.internal.observer.StationObserver;

import java.time.LocalTime;
import java.util.*;

import static ua.telesens.ostapenko.systemimitation.model.internal.RouteDirection.BACK;
import static ua.telesens.ostapenko.systemimitation.model.internal.RouteDirection.STRAIGHT;
import static ua.telesens.ostapenko.systemimitation.model.internal.RouteStationType.*;
import static ua.telesens.ostapenko.systemimitation.model.internal.RouteType.CYCLE;

/**
 * @author root
 * @since 03.01.16
 */
public class ScheduleManager {
    // FIXME: 05.01.16 BADDDDDDDDDDDDDDDDDDDd
    // FIXME: 05.01.16 BADDDDDDDDDDDDDDDDDDDd
    // FIXME: 05.01.16 BADDDDDDDDDDDDDDDDDDDd
    // FIXME: 05.01.16 BADDDDDDDDDDDDDDDDDDDd
    // FIXME: 04.01.16 Bad algorithm!!!!!!!!!!!!!!!
    public void createSchedule(BusRouteDecorator route) {
        LocalTime starting;
        LocalTime time;
        LocalTime ruleEndTime;
        RouteDirection direction;
        RouteStationType routeStationType;
        List<ScheduleLine> buff;
        List<StationObserver> stationObservers;
        List<RouteArc> arcs = (List<RouteArc>) route.getArcList();
        List<BusObserver> busObservers = (List<BusObserver>) route.getBusObservers();

        BusObserver bus;
        StationObserver station;

        for (DayType dayType : DayType.values()) {
            // Get route from day type
            if (!route.getRules().containsKey(dayType)) {
                continue;
            }

            starting = route.getStarting();
            time = starting;
            ruleEndTime = starting;
            List<RouteTrafficRule> rules = route.getRules().get(dayType);
            buff = new ArrayList<>();

            for (RouteTrafficRule rule : rules) {
                for (int i = 0; i < rule.getCountBus(); i++) {
                    //Get station line
                    stationObservers = new ArrayList<>(route.getStationObservers());
                    //Get bus
                    bus = busObservers.get(i);
                    //Get last position bus in route
                    direction = directionTo(bus, dayType);

                    for (int i1 = 0; i1 < rule.getCountRace(); i1++) {
                        for (int i2 = 0; i2 < stationObservers.size(); i2++) {
                            //Get station
                            station = stationObservers.get(i2);

                            routeStationType = i2 == 0 ? INITIAL : i2 == stationObservers.size() - 1
                                    ? FINAL
                                    : INTERMEDIATE;


                            buff.add(ScheduleLine.of(time, bus, direction, station, routeStationType));

                            time = i2 == stationObservers.size() - 1
                                    //Pause between race
                                    ? time.plusMinutes(rule.getTimeOut().getMinute())
                                    //Pause between station
                                    : time.plusMinutes(arcs.get(i2).getInterval().getMinute());
                        }
                        //If route is cycle reverse stations
                        if (route.getType().equals(CYCLE)) {
                            Collections.reverse(stationObservers);
                            direction = switchDirect(direction);
                        }
                    }
                    //Set schedule
                    bus.setSchedules(dayType, buff);
                    //Clear buff
                    buff.clear();
                    //Increment starting time from next bus
                    starting = starting.plusMinutes(rule.getInterval().getMinute());
                    //Set time end rule
                    ruleEndTime = time;
                    //Set time from next bus
                    time = starting;
                }
                //Set time staring from next rule
                starting = ruleEndTime;
                //Set from next rule
                time = starting;
            }
        }
    }

    private RouteDirection directionTo(BusObserver bus, DayType dayType) {
        // FIXME: 04.01.16 Bad style
        return Objects.nonNull(bus.getSchedules().get(dayType))
                ? switchDirect(new LinkedList<>(bus.getSchedules().get(dayType)).getLast().getDirection())
                : STRAIGHT;
    }

    private RouteDirection switchDirect(RouteDirection direction) {
        return direction.equals(BACK) ? STRAIGHT : BACK;
    }
}

