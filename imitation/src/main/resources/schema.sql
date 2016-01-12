DROP TABLE IF EXISTS BUS_LOG;
DROP TABLE IF EXISTS STATION_LOG;

CREATE TABLE BUS_LOG (
  id_bus_log       INT(10)     NOT NULL AUTO_INCREMENT,
  bus_number       INT(5)      NOT NULL,
  time_stop        DATETIME    NOT NULL,
  station_uuid     VARCHAR(36) NOT NULL,
  route_name       VARCHAR(9)  NOT NULL,
  download         INT(5)      NOT NULL,
  upload           INT(5)      NOT NULL,
  current_count    INT(5)      NOT NULL,
  create_date_time DATETIME    NOT NULL,
  PRIMARY KEY (id_bus_log)
);
CREATE TABLE STATION_LOG (
  id_station_log   INT(10)     NOT NULL AUTO_INCREMENT,
  bus_number       INT(5)      NOT NULL,
  time_stop        DATETIME    NOT NULL,
  bus_uuid         VARCHAR(36) NOT NULL,
  route_name       VARCHAR(9)  NOT NULL,
  download         INT(5)      NOT NULL,
  upload           INT(5)      NOT NULL,
  current_count    INT(5)      NOT NULL,
  create_date_time DATETIME    NOT NULL,
  PRIMARY KEY (id_station_log)
);

