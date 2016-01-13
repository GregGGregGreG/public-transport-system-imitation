package ua.telesens.ostapenko.systemimitation.dao.impl.mysql;

import org.springframework.jdbc.core.JdbcTemplate;
import ua.telesens.ostapenko.systemimitation.dao.ReportDAO;
import ua.telesens.ostapenko.systemimitation.model.internal.Report;

import java.time.LocalDateTime;

/**
 * @author root
 * @since 13.01.16
 */
public class MySqlReportDao implements ReportDAO {

    private JdbcTemplate jdbcTemplate;

    public MySqlReportDao(JdbcTemplate dataSource) {
        this.jdbcTemplate = dataSource;
    }

    @Override
    public int insertReport(Report add) {
        String insertTableSQL = "INSERT INTO REPORT_LOG"
                + "(startImitation," +
                "endImitation," +
                "duration," +
                "routes," +
                "stations," +
                "busAvgCapacity," +
                "numberTrips," +
                "transportedPassenger," +
                "busesPercentageOccupancy," +
                "lostPassenger," +
                "lazyPassenger," +
                "create_date_time) "
                + "VALUES" + "(?,?,?,?,?,?,?,?,?,?,?,?)";

        jdbcTemplate.update(insertTableSQL,
                add.getStartImitation(),
                add.getEndImitation(),
                add.getDuration().toString(),
                add.getRoutes(),
                add.getStations(),
                add.getBusAvgCapacity(),
                add.getNumberTrips(),
                add.getTransportedPassenger(),
                add.getBusesPercentageOccupancy(),
                add.getLostPassenger(),
                add.getLazyPassenger(),
                LocalDateTime.now());
        return 0;
    }
}
