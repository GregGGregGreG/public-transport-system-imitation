package ua.telesens.ostapenko.systemimitation.model.internal;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ua.telesens.ostapenko.systemimitation.model.internal.observer.BusObserver;
import ua.telesens.ostapenko.systemimitation.model.internal.observer.StationObserver;

import java.time.LocalTime;

/**
 * @author root
 * @since 09.12.15
 */
@Data
@Getter
@RequiredArgsConstructor(staticName = "of")
public class ScheduleLine {

    private final LocalTime time;
    private final BusObserver busObserver;
    private final RouteDirection direction;
    private final StationObserver stationObserver;
    private final RouteStationType routeStationType;

}
