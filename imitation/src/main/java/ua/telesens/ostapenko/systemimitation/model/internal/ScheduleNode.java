package ua.telesens.ostapenko.systemimitation.model.internal;

import lombok.AllArgsConstructor;
import lombok.Data;
import ua.telesens.ostapenko.systemimitation.model.internal.observer.BusObserver;
import ua.telesens.ostapenko.systemimitation.model.internal.observer.StationObserver;

import java.time.LocalTime;

/**
 * @author root
 * @since 09.12.15
 */
@Data
@AllArgsConstructor
public class ScheduleNode {

    private LocalTime time;
    private BusObserver busObserver;
    private StationObserver stationObserver;
    private StationState stationState;
    private RouteMovement routeMovement;
    private Route route;

}
