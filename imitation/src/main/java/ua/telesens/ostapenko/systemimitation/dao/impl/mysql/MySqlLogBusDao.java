package ua.telesens.ostapenko.systemimitation.dao.impl.mysql;

import org.springframework.jdbc.core.JdbcTemplate;
import ua.telesens.ostapenko.systemimitation.dao.LogBusDAO;
import ua.telesens.ostapenko.systemimitation.model.internal.LogBus;

import java.time.LocalDateTime;

/**
 * @author root
 * @since 11.01.16
 */
public class MySqlLogBusDao implements LogBusDAO {

    private JdbcTemplate jdbcTemplate;

    public MySqlLogBusDao(JdbcTemplate dataSource) {
        this.jdbcTemplate = dataSource;
    }

    @Override
    public int insertLogBus(LogBus add) {

        String insertTableSQL = "INSERT INTO BUS_LOG"
                + "(bus_number, " +
                "time_stop," +
                " station_uuid," +
                " route_name," +
                "download," +
                "upload," +
                "current_count," +
                "create_date_time) "
                + "VALUES" + "(?,?,?,?,?,?,?,?)";

        jdbcTemplate.update(insertTableSQL,
                add.getBusNUmber(),
                add.getTimeStop(),
                add.getStationUUID().toString(),
                add.getRouteName(),
                add.getDownload(),
                add.getUpload(),
                add.getCount(),
                LocalDateTime.now());
        return 0;
    }
}
