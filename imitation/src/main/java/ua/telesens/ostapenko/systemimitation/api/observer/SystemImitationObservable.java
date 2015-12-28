package ua.telesens.ostapenko.systemimitation.api.observer;

/**
 * @author root
 * @since 10.12.15
 */
public interface SystemImitationObservable {

    void registerTransportObserver(SystemImitationObserver observer);

    void removeTransportObserver(SystemImitationObserver observer);

    void notifyTransportObservers();

}
