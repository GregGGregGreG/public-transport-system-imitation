package ua.telesens.ostapenko.systemimitation.api.observer;

import ua.telesens.ostapenko.systemimitation.model.internal.Route;
import ua.telesens.ostapenko.systemimitation.model.internal.ScheduleNode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author root
 * @since 10.12.15
 */
public interface SystemImitationObserver {

    void updateTime(LocalDateTime time, Map<Route, List<ScheduleNode>> schedule);

    default void setObservable(SystemImitationObservable observable) {
        observable.registerTransportObserver(this);
    }
}
