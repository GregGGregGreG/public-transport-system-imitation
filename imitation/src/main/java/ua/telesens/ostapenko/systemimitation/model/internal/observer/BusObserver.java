package ua.telesens.ostapenko.systemimitation.model.internal.observer;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ua.telesens.ostapenko.systemimitation.api.RouteManager;
import ua.telesens.ostapenko.systemimitation.api.observer.SystemImitationObserver;
import ua.telesens.ostapenko.systemimitation.model.internal.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static ua.telesens.ostapenko.systemimitation.model.internal.StationState.FINAL;

/**
 * @author root
 * @since 14.12.15
 */
@Slf4j
@EqualsAndHashCode(exclude = {"passengers", "route", "stations"})
public class BusObserver implements SystemImitationObserver, RouteManager {

    @Getter
    private Bus bus;
    private Route route;
    private List<Station> stations = Collections.emptyList();
    private Map<Station, Queue<Passenger>> passengers = Collections.emptyMap();

    private BusObserver(Bus bus) {
        this.bus = bus;
        passengers = new HashMap<>();
    }

    public static BusObserver of(Bus bus) {
        return new BusObserver(bus);
    }

    @Override
    public void updateTime(LocalDateTime time, Map<Route, List<ScheduleNode>> schedule) {
        // FIXME: 17.12.15  Add global event object
        Bus busSchedule;
        LocalTime timeSchedule;
        StationObserver stationObserver;
        Route routeSchedule;
        RouteMovement routeMovement;
        Passenger passenger;
        Queue<Passenger> uploadPassenger;

        for (ScheduleNode scheduleNode : schedule.get(route)) {
            busSchedule = scheduleNode.getBusObserver().getBus();
            timeSchedule = scheduleNode.getTime();

            if (bus.equals(busSchedule) && timeSchedule.equals(time.toLocalTime())) {
                routeSchedule = scheduleNode.getRoute();
                routeMovement = scheduleNode.getRouteMovement();
                stationObserver = scheduleNode.getStationObserver();

                // FIXME: 17.12.15 Bad style
                //Upload Passenger from final stationObserver
                if (scheduleNode.getStationState().equals(FINAL)) {
                    uploadAll();
                    log.trace("Upload all passenger!");
                } else {
                    //Upload from stationObserver
                    uploadPassenger = passengers.get(stationObserver.getStation());
                    log.debug(time.toLocalDate() +
                            " |\t" + timeSchedule +
                            " |\t" + busSchedule +
                            stationObserver.getStation() +
                            " |Upload \t" + uploadPassenger.size() + " passenger");
                    uploadPassenger.clear();

                    //Download passengers
                    while (isNotFill() && stationObserver.hasNextPassenger(routeSchedule, routeMovement)) {
                        passenger = stationObserver.getPassenger(routeSchedule, routeMovement);
                        passengers.get(passenger.getFinalStation()).add(passenger);

                        log.trace(time.toLocalDate() +
                                " |\t" + timeSchedule +
                                busSchedule +
                                " |\t" + stationObserver.getStation() +
                                " |Add\t" + passenger);
                    }

                }

                log.debug(time.toLocalDate() +
                        " |\t" + timeSchedule +
                        " |\t" + bus +
                        " |\t" + stationObserver.getStation() +
                        "\t" + scheduleNode.getStationState() +
                        " |\t" + routeSchedule +
                        " | Current count passenger \t" + getCurrentCountPassenger());
            }
        }
    }

    public int getCurrentCountPassenger() {
        int result = 0;
        for (Station station : stations) {
            for (Passenger ignored : passengers.get(station)) {
                result++;
            }
        }
        return result;
    }

    // FIXME: 17.12.15 Add validation
    @Override
    @NotNull
    public void registerRoute(Route add) {
        this.route = add;
        this.stations = RouteManager.getAllStation(route);
        for (Station station : stations) {
            passengers.put(station, new ArrayDeque<>());
        }
    }

    private void uploadAll() {
        for (Station station : stations) {
            passengers.get(station).clear();
        }
    }

    private boolean isNotFill() {
        return getCurrentCountPassenger() < bus.getCountPassenger();
    }
}
