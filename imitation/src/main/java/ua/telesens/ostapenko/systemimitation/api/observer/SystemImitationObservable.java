package ua.telesens.ostapenko.systemimitation.api.observer;

/**
 * @author root
 * @since 10.12.15
 */
public interface SystemImitationObservable {

    SystemImitationObserver register(SystemImitationObserver observer);

    void remove(SystemImitationObserver observer);

    void notifyAllObservers();

}
