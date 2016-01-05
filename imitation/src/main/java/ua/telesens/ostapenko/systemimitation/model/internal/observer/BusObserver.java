package ua.telesens.ostapenko.systemimitation.model.internal.observer;

import lombok.extern.slf4j.Slf4j;
import ua.telesens.ostapenko.systemimitation.api.observer.SystemImitationObserver;
import ua.telesens.ostapenko.systemimitation.model.internal.*;

import java.time.LocalTime;
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
    private Map<BusStation, Queue<Passenger>> passengers = Collections.emptyMap();
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

    public synchronized void setSchedules(DayType dayType, Collection<ScheduleLine> schedules) {
        if (Objects.isNull(this.schedules.get(dayType))) {
            this.schedules.put(dayType, new ArrayList<>());
            this.schedules.get(dayType).addAll(schedules);
        } else {
            this.schedules.get(dayType).addAll(schedules);
        }
    }

    //Update event

    @Override
    public synchronized void updateEvent(ImitationEvent event) {
        schedules
                .get(event.getDayType())
                .stream()
                .parallel()
                .filter(scheduleLine -> event.getTime().toLocalTime().equals(scheduleLine.getTime()))
                .forEach(this::updateEvent);
    }

    private void updateEvent(ScheduleLine scheduleLine) {
        StationObserver stationObserver = scheduleLine.getStationObserver();
        LocalTime time = scheduleLine.getTime();
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
        route.getStations()
                .parallelStream()
                .forEach(station -> passengers.put(station, new ArrayDeque<>()));
    }


    // Operations from passenger

    private int downloadFrom(StationObserver stationObserver, LocalTime time, RouteDirection direction, int download) {
        Passenger passenger;//Download passengers
        while (isNotFill() && stationObserver.hasNextPassenger(route, direction)) {
            passenger = stationObserver.getPassenger(route, direction);
            passengers.get(passenger.getStation()).add(passenger);

            log.trace(time +
                    " |\t" + bus.getNumber() +
                    " |\t" + stationObserver.getStation().getName() +
                    " |Add\t" + passenger);
            countPassenger++;
            download++;
        }
        return download;
    }

    private int uploadFrom(StationObserver stationObserver, int upload) {
        Queue<Passenger> uploadPassenger;

        uploadPassenger = passengers.get(stationObserver.getStation());

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

