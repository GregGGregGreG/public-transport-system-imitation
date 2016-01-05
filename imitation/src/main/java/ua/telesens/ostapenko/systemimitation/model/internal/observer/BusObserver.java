package ua.telesens.ostapenko.systemimitation.model.internal.observer;

import lombok.extern.slf4j.Slf4j;
import ua.telesens.ostapenko.systemimitation.api.observer.SystemImitationObserver;
import ua.telesens.ostapenko.systemimitation.model.internal.*;

import java.time.LocalDateTime;
import java.util.*;

import static ua.telesens.ostapenko.systemimitation.model.internal.RouteStationType.FINAL;

/**
 * @author root
 * @since 14.12.15
 */
@Slf4j
public class BusObserver implements SystemImitationObserver {

    private final Bus bus;
    private final BusRouteDecorator route;
    private Map<StationObserver, Queue<Passenger>> passengers = Collections.emptyMap();
    private Map<DayType, List<ScheduleLine>> schedules = Collections.emptyMap();
    private long transpotedPassenger;
    private int countPassenger;

    private BusObserver(Bus bus, BusRouteDecorator route) {
        this.bus = bus;
        this.route = route;
        this.passengers = new HashMap<>();
        this.schedules = new HashMap<>();
        registerRoute();
    }

    public static BusObserver of(Bus bus, BusRouteDecorator route) {
        return new BusObserver(bus, route);
    }

    public Map<DayType, List<ScheduleLine>> getSchedules() {
        return schedules;
    }

    public synchronized void setSchedules(DayType dayType, Collection<ScheduleLine> scheduleLines) {
        if (Objects.isNull(schedules.get(dayType))) {
            schedules.put(dayType, new LinkedList<>(scheduleLines));
        } else {
            schedules.get(dayType).addAll(scheduleLines);
        }
    }

    //Update event

    @Override
    public synchronized void updateEvent(ImitationEvent event) {
        if (schedules.containsKey(event.getDayType())) {
            schedules
                    .get(event.getDayType())
                    .stream()
                    .parallel()
                    .filter(scheduleLine -> event.getTime().toLocalTime().equals(scheduleLine.getTime()))
                    .forEach(scheduleLine -> updateEvent(scheduleLine, event));
        }
    }

    private void updateEvent(ScheduleLine scheduleLine, ImitationEvent event) {
        StationObserver stationObserver = scheduleLine.getStationObserver();
        LocalDateTime time = event.getTime();
        RouteDirection direction = scheduleLine.getDirection();
        RouteStationType routeStationType = scheduleLine.getRouteStationType();
        int download = 0;
        int upload = 0;

        //Upload Passenger from final stationObserver
        if (routeStationType.equals(FINAL)) {
            upload = uploadAll(upload);
        } else {
            upload = uploadFrom(stationObserver, upload);
            download = downloadFrom(stationObserver, time, direction, download);
        }

        log.debug(String.format("%-6s|%-6s|%-12s|%-12s|%-10s|%-10s|%-12s|%-15s",
                time,
                bus.getNumber(),
                stationObserver.getStation().getName(),
                routeStationType,
                route,
                "Upload " + upload,
                "Download " + download,
                "Current count passenger\t" + countPassenger));
    }


    // Route register

    private void registerRoute() {
        route.getStationObservers()
                .parallelStream()
                .forEach(station -> passengers.put(station, new ArrayDeque<>()));
    }


    // Operations from passenger

    private int downloadFrom(StationObserver station, LocalDateTime time, RouteDirection direction, int download) {
        Passenger passenger;//Download passengers
        while (isNotFill() && station.hasNextPassenger(route, direction)) {
            passenger = station.getPassenger(route, direction);
            passengers.get(passenger.getStation()).add(passenger);

            log.trace(time +
                    " |\t" + bus.getNumber() +
                    " |\t" + station.getStation().getName() +
                    " |Add\t" + passenger);
            countPassenger++;
            download++;
        }
        return download;
    }

    private int uploadFrom(StationObserver stationObserver, int upload) {
        Queue<Passenger> uploadPassenger = passengers.get(stationObserver);

        upload += uploadPassenger.size();
        countPassenger -= upload;

        uploadPassenger.clear();
        return upload;
    }

    private int uploadAll(int upload) {
        passengers.forEach((station, que) -> que.clear());
        upload += countPassenger;
        countPassenger = 0;
        return upload;
    }

    private boolean isNotFill() {
        return countPassenger < bus.getCountPassenger();
    }


    //  String conversion

    @Override
    public String toString() {
        return String.valueOf(bus);
    }
}

