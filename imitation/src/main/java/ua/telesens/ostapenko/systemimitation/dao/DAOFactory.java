package ua.telesens.ostapenko.systemimitation.dao;

/**
 * @author root
 * @since 11.01.16
 */
public interface DAOFactory {
    LogBusDAO getLogBusDAO();

    LogStationDAO getLogStationDAO();

}
