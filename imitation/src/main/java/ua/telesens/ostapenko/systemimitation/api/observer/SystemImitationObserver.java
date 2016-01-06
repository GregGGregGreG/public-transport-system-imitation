package ua.telesens.ostapenko.systemimitation.api.observer;

import ua.telesens.ostapenko.systemimitation.model.internal.ImitationEvent;

/**
 * @author root
 * @since 10.12.15
 */
public interface SystemImitationObserver {

    void updateEvent(ImitationEvent event);
}
