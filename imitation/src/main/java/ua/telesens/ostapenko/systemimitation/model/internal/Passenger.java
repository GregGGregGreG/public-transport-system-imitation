package ua.telesens.ostapenko.systemimitation.model.internal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ua.telesens.ostapenko.systemimitation.api.observer.PassengerObserver;
import ua.telesens.ostapenko.systemimitation.model.internal.observer.StationObserver;
import ua.telesens.ostapenko.systemimitation.service.BusSystemImitation;

import java.time.LocalTime;

/**
 * @author root
 * @since 14.12.15
 */
@Getter
@RequiredArgsConstructor(staticName = "of")
@ToString
public class Passenger implements PassengerObserver {

    private LocalTime timer = LocalTime.of(0, 0);
    private final RouteDecorator route;
    private final RouteDirection direction;
    private final StationObserver station;
    private final LocalTime timeWaiting;

    @Override
    public Passenger updateTimer() {
        timer = timer.plusMinutes(BusSystemImitation.IMITATION_STEP);
        return this;
    }

    @Override
    public boolean isGone() {
        return timer.isAfter(timeWaiting);
    }
}
