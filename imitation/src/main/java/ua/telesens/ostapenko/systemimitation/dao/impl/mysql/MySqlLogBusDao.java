package ua.telesens.ostapenko.systemimitation.dao.impl.mysql;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ua.telesens.ostapenko.systemimitation.dao.LogBusDAO;
import ua.telesens.ostapenko.systemimitation.exeption.DataBaseException;
import ua.telesens.ostapenko.systemimitation.model.internal.LogBus;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;

/**
 * @author root
 * @since 11.01.16
 */
@Slf4j
public class MySqlLogBusDao implements LogBusDAO {

    private JdbcTemplate jdbcTemplate;

    public MySqlLogBusDao(JdbcTemplate dataSource) {
        this.jdbcTemplate = dataSource;
    }

    // FIXME: 19.01.16 Add log bus wrapper
    @Override
    public int insertLogBus(final LogBus add) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "INSERT INTO BUS_LOG"
                + "(bus_number," +
                "time_stop," +
                " station_uuid," +
                " route_name," +
                "download," +
                "upload," +
                "current_count," +
                "create_date_time) "
                + "VALUES" + "(?,?,?,?,?,?,?,?)";
        try {
            jdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(query, new String[]{"id_bus_log"});
                        ps.setInt(1, add.getBusNUmber());
                        ps.setString(2, String.valueOf(add.getTimeStop()));
                        ps.setString(3, String.valueOf(add.getStationUUID()));
                        ps.setString(4, add.getRouteName());
                        ps.setInt(5, add.getDownload());
                        ps.setInt(6, add.getUpload());
                        ps.setInt(7, add.getCount());
                        ps.setString(8, String.valueOf(LocalDateTime.now()));
                        return ps;
                    }, keyHolder);
            log.debug("insert(" + add + ") --> id: " + keyHolder.getKey().intValue());
        } catch (DataAccessException e) {
            throw new DataBaseException("Error add log bus \t" + add, e);
        }
        return keyHolder.getKey().intValue();
    }
}
