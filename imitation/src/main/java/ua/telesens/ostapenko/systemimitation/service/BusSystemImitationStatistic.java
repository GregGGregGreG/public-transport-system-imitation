package ua.telesens.ostapenko.systemimitation.service;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.extended.NamedMapConverter;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import ua.telesens.ostapenko.systemimitation.api.BusStatistic;
import ua.telesens.ostapenko.systemimitation.api.Report;
import ua.telesens.ostapenko.systemimitation.model.internal.DayType;
import ua.telesens.ostapenko.systemimitation.model.internal.ReportData;
import ua.telesens.ostapenko.systemimitation.model.internal.observer.BusObserver;
import ua.telesens.ostapenko.systemimitation.model.internal.observer.StationObserver;
import ua.telesens.ostapenko.systemimitation.xstream.converters.DurationConverter;
import ua.telesens.ostapenko.systemimitation.xstream.converters.LocaleDateTimeConverter;
import ua.telesens.ostapenko.systemimitation.xstream.converters.LocaleTimeConverter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;

/**
 * @author root
 * @since 06.01.16
 */
@Slf4j
class BusSystemImitationStatistic {

    private final BusSystemImitation systemImitation;
    private static final String FORMAT = "%-40s%-20s";

    private BusSystemImitationStatistic(BusSystemImitation systemImitation) {
        this.systemImitation = systemImitation;
    }

    public static BusSystemImitationStatistic of(BusSystemImitation systemImitation) {
        return new BusSystemImitationStatistic(systemImitation);
    }

    public Report execute() {
        Instant start = Instant.now();
        log.info(String.format(FORMAT, "Start imitation in", start));
        systemImitation.run();
        Instant end = Instant.now();
        log.info(String.format(FORMAT, "Stop imitation in \t", end));
        log.info(String.format(FORMAT, "Duration imitation in \t", Duration.between(start, end)));
        return new Rep(systemImitation);
    }

    @EqualsAndHashCode
    private class Rep implements Report {
        private BusSystemImitation systemImitation;
        private static final String FORMAT = "%-40s%-20s";
        private ReportData data;

        Rep(BusSystemImitation systemImitation) {
            this.systemImitation = systemImitation;
            data = ReportData.builder()
                    .starting(systemImitation.getStarting())
                    .end(systemImitation.getEnd())
                    .duration(Duration.between(systemImitation.getStarting(), systemImitation.getEnd()))
                    .routes(systemImitation.getRoutes().size())
                    .buses(systemImitation.getBuses().size())
                    .stations(systemImitation.getStations().size())
                    .endDay(systemImitation.getEndDay())
                    .startDay(systemImitation.getStartDay())
                    .busAvgCapacity(getAvgCapacityBus())
                    .numberTrips(getNumberTrips())
                    .transportedPassenger(getAllPassenger())
                    .busesPercentageOccupancy(getOccupancy())
                    .lostPassenger(getLostPassenger())
                    .build();
        }

        @Override
        public Report show() {
            log.info("----- Summary Statistic -----");
            log.info(String.format(FORMAT, "Start imitation", data.getStarting()));
            log.info(String.format(FORMAT, "Stop imitation", data.getEnd()));
            log.info(String.format(FORMAT, "Duration imitation", data.getDuration()));
            log.info(String.format(FORMAT, "Count rout", data.getRoutes()));
            log.info(String.format(FORMAT, "Count bus", data.getBuses()));
            log.info(String.format(FORMAT, "Count station ", data.getStations()));
            log.info(String.format(FORMAT, "Start gen passenger from workday", data.getStartDay().get(DayType.WORKDAY)));
            log.info(String.format(FORMAT, "Stop gen passenger from workday", data.getEndDay().get(DayType.WORKDAY)));
            log.info(String.format(FORMAT, "Start gen passenger from holiday", data.getStartDay().get(DayType.HOLIDAY)));
            log.info(String.format(FORMAT, "Stop gen passenger from holiday", data.getEndDay().get(DayType.HOLIDAY)));
            log.info(String.format(FORMAT, "Avg capacity bus", data.getBusAvgCapacity()));
            log.info(String.format(FORMAT, "Number trips", data.getNumberTrips()));
            log.info(String.format(FORMAT, "All transported passenger", data.getTransportedPassenger()));
            log.info(String.format(FORMAT, "Occupancy Percentage of buses ", data.getBusesPercentageOccupancy()));
            log.info(String.format(FORMAT, "Lost passenger", data.getLostPassenger()));
            return this;
        }

        @Override
        public Report toXML() throws IOException {
            log.info("----- Statistic to XML -----");

            XStream xStream = new XStream();
            String name = "report/report_" + Instant.now() + ".xml";
            File file = new File(name);

            xStream.processAnnotations(ReportData.class);
            xStream.setMode(XStream.NO_REFERENCES);
            xStream.registerConverter(new LocaleDateTimeConverter());
            xStream.registerConverter(new LocaleTimeConverter());
            xStream.registerConverter(new DurationConverter());
            xStream.registerConverter(new NamedMapConverter(xStream.getMapper(),
                    "val",
                    "dayType", DayType.class,
                    "time", LocalTime.class));
            log.debug("\n" + xStream.toXML(data));
            xStream.toXML(data, new FileWriter(file));
            log.info(String.format(FORMAT, "Path to file", file.getPath()));
            return this;
        }

        private double getOccupancy() {
            double result;
            double transportPassenger = getAllPassenger();
            double numberTrips = getNumberTrips();
            double capacityBus = getAvgCapacityBus();
            result = (transportPassenger * 100) / (capacityBus * numberTrips);
            return result;
        }

        private int getAvgCapacityBus() {
            int numberBus = systemImitation.getBuses().size();
            int capacityBus;
            int buff = 0;
            for (BusObserver busObserver : systemImitation.getBuses()) {
                buff += busObserver.getBus().getCapacity();
            }
            capacityBus = buff / numberBus;
            return capacityBus;
        }

        private int getNumberTrips() {
            int numberTrips = 0;
            for (BusStatistic bus : systemImitation.getBuses()) {
                numberTrips += bus.getNumberTrips();
            }
            return numberTrips;
        }

        private int getAllPassenger() {
            int result = 0;
            for (BusStatistic bus : systemImitation.getBuses()) {
                result += bus.getPassengers();
            }
            return result;
        }

        private int getLostPassenger() {
            int result = 0;
            for (StationObserver stationObserver : systemImitation.getStations()) {
                result += stationObserver.getLostPassenger();
            }
            return result;
        }
    }
}
