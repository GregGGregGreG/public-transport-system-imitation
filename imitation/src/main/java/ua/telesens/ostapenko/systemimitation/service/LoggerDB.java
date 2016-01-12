package ua.telesens.ostapenko.systemimitation.service;

import lombok.RequiredArgsConstructor;
import ua.telesens.ostapenko.systemimitation.api.Logger;
import ua.telesens.ostapenko.systemimitation.dao.DAOFactory;
import ua.telesens.ostapenko.systemimitation.model.internal.LogBus;
import ua.telesens.ostapenko.systemimitation.model.internal.LogStation;

/**
 * @author root
 * @since 12.01.16
 */
@RequiredArgsConstructor(staticName = "of")
public class LoggerDB implements Logger {

    private final DAOFactory daoFactory;

    @Override
    public void addLog(LogBus log)  {
        daoFactory.getLogBusDAO().insertLogBus(log);
    }

    @Override
    public void addLog(LogStation log) {
        daoFactory.getLogStationDAO().insertLogStation(log);
    }
}
