package ua.telesens.ostapenko.systemimitation.api.observer;

import ua.telesens.ostapenko.systemimitation.model.internal.Passenger;

/**
 * @author root
 * @since 10.01.16
 */
public interface PassengerObserver {

    Passenger updateTimer();

    boolean isGone();
}
