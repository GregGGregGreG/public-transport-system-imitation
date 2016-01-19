package ua.telesens.ostapenko.systemimitation.dao.impl.mysql;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ua.telesens.ostapenko.systemimitation.dao.ReportDAO;
import ua.telesens.ostapenko.systemimitation.exeption.DataBaseException;
import ua.telesens.ostapenko.systemimitation.model.internal.Report;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;

/**
 * @author root
 * @since 13.01.16
 */
@Slf4j
public class MySqlReportDao implements ReportDAO {

    private JdbcTemplate jdbcTemplate;

    public MySqlReportDao(JdbcTemplate dataSource) {
        this.jdbcTemplate = dataSource;
    }

    // FIXME: 19.01.16 Add log report wrapper
    @Override
    public int insertReport(Report add) {
        String query = "INSERT INTO REPORT_LOG"
                + "(startImitation," +
                "endImitation," +
                "duration," +
                "routes," +
                "stations," +
                "busAvgCapacity," +
                "numberTrips," +
                "transportedPassenger," +
                "busesPercentageOccupancy," +
                "lazyPassenger," +
                "create_date_time) "
                + "VALUES" + "(?,?,?,?,?,?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(query, new String[]{"id_report_log"});
                        ps.setString(1, add.getStartImitation().toString());
                        ps.setString(2, String.valueOf(add.getEndImitation()));
                        ps.setString(3, String.valueOf(add.getDuration().toString()));
                        ps.setInt(4, add.getRoutes());
                        ps.setInt(5, add.getStations());
                        ps.setInt(6, add.getBusAvgCapacity());
                        ps.setInt(7, add.getNumberTrips());
                        ps.setInt(8, add.getTransportedPassenger());
                        ps.setDouble(9, add.getBusesPercentageOccupancy());
                        ps.setLong(10, add.getLazyPassenger());
                        ps.setString(11, String.valueOf(LocalDateTime.now()));
                        return ps;
                    }, keyHolder);
            log.debug("insert(" + add + ") --> id: " + keyHolder.getKey().intValue());
        } catch (DataAccessException e) {
            throw new DataBaseException("Error add report \t" + add, e);
        }
        return keyHolder.getKey().intValue();
    }
}
