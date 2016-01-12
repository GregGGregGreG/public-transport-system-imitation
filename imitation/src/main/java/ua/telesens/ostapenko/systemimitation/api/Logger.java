package ua.telesens.ostapenko.systemimitation.api;

import ua.telesens.ostapenko.systemimitation.model.internal.LogBus;
import ua.telesens.ostapenko.systemimitation.model.internal.LogStation;

/**
 * @author root
 * @since 11.01.16
 */
public interface Logger {

    void addLog(LogBus log);

    void addLog(LogStation log);

}
