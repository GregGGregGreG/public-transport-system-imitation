package ua.telesens.ostapenko.systemimitation.model.internal.observer;

import ua.telesens.ostapenko.systemimitation.api.observer.SystemImitationObserver;
import ua.telesens.ostapenko.systemimitation.model.internal.*;

import java.util.*;

/**
 * @author root
 * @since 14.12.15
 */
public class BusObserver implements SystemImitationObserver {

    private Bus bus;
    private BusRouteDecorator route;
    private Map<BusStation, Queue<Passenger>> passengers = Collections.emptyMap();
    private Map<DayType, List<ScheduleLine>> schedules = Collections.emptyMap();
    private long transpotedPassenger;
    private int countPassenger;

    public BusObserver(Bus bus, BusRouteDecorator route) {
        this.bus = bus;
        this.route = route;
        this.passengers = new HashMap<>();
        this.schedules = new HashMap<>();
        registerRoute();
    }


    @Override
    public void updateEvent(ImitationEvent event) {
//        List<ScheduleLine> buff = schedules.get(event.getDayType());


//        // FIXME: 17.12.15  Add global event object
//        Bus busSchedule;
//        LocalTime timeSchedule;
//        StationObserver stationObserver;
//        Route routeSchedule;
//        EnumDirection direction;
//        Passenger passenger;
//        Queue<Passenger> uploadPassenger;
//
//        for (ScheduleNode scheduleNode : schedule.get(route)) {
//            busSchedule = scheduleNode.getBusObserver().getBus();
//            timeSchedule = scheduleNode.getTime();
//
//            if (bus.equals(busSchedule) && timeSchedule.equals(time.toLocalTime())) {
//                routeSchedule = scheduleNode.getRoute();
//                direction = scheduleNode.getDirection();
//                stationObserver = scheduleNode.getStationObserver();
//
//                // FIXME: 17.12.15 Bad style
//                //Upload Passenger from final stationObserver
//                if (scheduleNode.getStationState().equals(FINAL)) {
//                    uploadAll();
//                    log.trace("Upload all passenger!");
//                } else {
//                    //Upload from stationObserver
//                    uploadPassenger = passengers.get(stationObserver.getStation());
//                    log.debug(time.toLocalDate() +
//                            " |\t" + timeSchedule +
//                            " |\t" + busSchedule +
//                            stationObserver.getStation() +
//                            " |Upload \t" + uploadPassenger.size() + " passenger");
//                    uploadPassenger.clear();
//
//                    //Download passengers
//                    while (isNotFill() && stationObserver.hasNextPassenger(routeSchedule, direction)) {
//                        passenger = stationObserver.getPassenger(routeSchedule, direction);
//                        passengers.get(passenger.getFinalStation()).add(passenger);
//
//                        log.trace(time.toLocalDate() +
//                                " |\t" + timeSchedule +
//                                busSchedule +
//                                " |\t" + stationObserver.getStation() +
//                                " |Add\t" + passenger);
//                    }
//
//                }
//
//                log.debug(time.toLocalDate() +
//                        " |\t" + timeSchedule +
//                        " |\t" + bus +
//                        " |\t" + stationObserver.getStation() +
//                        "\t" + scheduleNode.getStationState() +
//                        " |\t" + routeSchedule +
//                        " | Current count passenger \t" + getCurrentCountPassenger());
//            }
//        }
    }

//    public Map<DayType, List<ScheduleLine>> getSchedules() {
//        return schedules;
//    }
//
//    public void setSchedules(Map<DayType, List<ScheduleLine>> schedules) {
//        this.schedules = schedules;
//    }
//
//    public long getTranspotedPassenger() {
//        return transpotedPassenger;
//    }


    public Map<DayType, List<ScheduleLine>> getSchedules() {
        return schedules;
    }

    // FIXME: 03.01.16 Stupid solve
    public void setSchedules(DayType dayType, List<ScheduleLine> schedules) {
        if (Objects.isNull(this.schedules.get(dayType))) {
            this.schedules.put(dayType, new ArrayList<ScheduleLine>());
            this.schedules.get(dayType).addAll(schedules);
        }else {
            this.schedules.get(dayType).addAll(schedules);
        }
    }

    private void registerRoute() {
        route.getStations()
                .parallelStream()
                .forEach(station -> passengers.put(station, new ArrayDeque<>()));
    }

    private void uploadAll() {
        passengers.forEach((station, que) -> que.clear());
    }

    private int getCountPassenger() {
        return countPassenger;
    }

    private boolean isNotFill() {
        return getCountPassenger() < bus.getCountPassenger();
    }

    @Override
    public String toString() {
        return String.valueOf(bus);
    }
}

