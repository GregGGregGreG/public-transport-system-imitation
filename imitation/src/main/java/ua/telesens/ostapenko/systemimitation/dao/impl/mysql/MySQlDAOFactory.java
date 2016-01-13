package ua.telesens.ostapenko.systemimitation.dao.impl.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import ua.telesens.ostapenko.systemimitation.dao.DAOFactory;
import ua.telesens.ostapenko.systemimitation.dao.LogBusDAO;
import ua.telesens.ostapenko.systemimitation.dao.LogStationDAO;
import ua.telesens.ostapenko.systemimitation.dao.ReportDAO;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
 * @author root
 * @since 11.01.16
 */
@Component
public class MySQlDAOFactory extends JdbcDaoSupport implements DAOFactory {

    @Autowired
    private DataSource dataSource;

    public MySQlDAOFactory() {
    }

    public MySQlDAOFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Override
    public LogBusDAO getLogBusDAO() {
        return new MySqlLogBusDao(getJdbcTemplate());
    }

    @Override
    public LogStationDAO getLogStationDAO() {
        return new MySqlLogStationDao(getJdbcTemplate());
    }

    @Override
    public ReportDAO getReportDAO() {
        return new MySqlReportDao(getJdbcTemplate());
    }
}
