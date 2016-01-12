package ua.telesens.ostapenko.systemimitation.dao.impl.mysql;

import org.springframework.jdbc.core.JdbcTemplate;
import ua.telesens.ostapenko.systemimitation.dao.LogStationDAO;
import ua.telesens.ostapenko.systemimitation.model.internal.LogStation;

import java.time.LocalDateTime;

/**
 * @author root
 * @since 11.01.16
 */
public class MySqlLogStationDao implements LogStationDAO {

    private JdbcTemplate jdbcTemplate;

    public MySqlLogStationDao(JdbcTemplate dataSource) {
        this.jdbcTemplate = dataSource;
    }

    @Override
    public int insertLogStation(LogStation add) {
        String insertTableSQL = "INSERT INTO STATION_LOG"
                + "(bus_number, " +
                "time_stop," +
                " bus_uuid," +
                " route_name," +
                "download," +
                "upload," +
                "current_count," +
                "create_date_time) "
                + "VALUES" + "(?,?,?,?,?,?,?,?)";

        jdbcTemplate.update(insertTableSQL,
                add.getBusNUmber(),
                add.getTimeStop(),
                add.getBusUUID().toString(),
                add.getRouteName(),
                add.getDownload(),
                add.getUpload(),
                add.getCount(),
                LocalDateTime.now());


        return 0;
    }

}
