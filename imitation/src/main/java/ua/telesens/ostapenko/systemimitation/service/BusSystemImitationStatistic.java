package ua.telesens.ostapenko.systemimitation.service;

import lombok.extern.slf4j.Slf4j;
import ua.telesens.ostapenko.systemimitation.api.Logger;
import ua.telesens.ostapenko.systemimitation.api.ReportManager;
import ua.telesens.ostapenko.systemimitation.model.internal.Bus;
import ua.telesens.ostapenko.systemimitation.model.internal.Report;
import ua.telesens.ostapenko.systemimitation.model.internal.observer.BusObserver;
import ua.telesens.ostapenko.systemimitation.model.internal.observer.StationObserver;

import java.time.Duration;
import java.time.Instant;
import java.util.OptionalDouble;

/**
 * @author root
 * @since 06.01.16
 */
@Slf4j
public class BusSystemImitationStatistic {

    private final BusSystemImitation systemImitation;
    private static final String FORMAT = "%-40s%-20s";

    private BusSystemImitationStatistic(BusSystemImitation systemImitation) {
        this.systemImitation = systemImitation;
    }

    public static BusSystemImitationStatistic of(BusSystemImitation systemImitation) {
        return new BusSystemImitationStatistic(systemImitation);
    }

    public ReportManager execute() {
        Instant start = Instant.now();
        log.info(String.format(FORMAT, "Start imitation in", start));
        systemImitation.run();
        Instant end = Instant.now();
        log.info(String.format(FORMAT, "Stop imitation in \t", end));
        log.info(String.format(FORMAT, "Duration imitation in \t", Duration.between(start, end)));
        return ReportManagerImpl.of(createReport());
    }

    public void setLogger(Logger logger) {
        systemImitation.setLogger(logger);
    }

    private Report createReport() {
        return Report.builder()
                .startImitation(systemImitation.getStarting())
                .endImitation(systemImitation.getEnd())
                .duration(getDurationImitation())
                .routes(systemImitation.getRoutes().size())
                .buses(systemImitation.getBuses().size())
                .stations(systemImitation.getStations().size())
                .busAvgCapacity((int) getAvgCapacityBus().getAsDouble())
                .numberTrips(getNumberTrips())
                .transportedPassenger(getAllPassengerTransportBus())
                .busesPercentageOccupancy(getOccupancy())
                .lazyPassenger(getLazyPassenger())
                .build();
    }

    private Duration getDurationImitation() {
        return Duration.between(systemImitation.getStarting(), systemImitation.getEnd());
    }

    private double getOccupancy() {
        double result;
        double transportPassenger = getAllPassengerTransportBus();
        double numberTrips = getNumberTrips();
        double capacityBus = getAvgCapacityBus().getAsDouble();
        result = (transportPassenger * 100) / (capacityBus * numberTrips);
        return result;
    }

    private OptionalDouble getAvgCapacityBus() {
        return systemImitation.getBuses().stream()
                .map(BusObserver::getBus)
                .mapToInt(Bus::getCapacity)
                .average();
    }

    private int getNumberTrips() {
        return systemImitation.getBuses().stream()
                .mapToInt(BusObserver::getNumberTrips)
                .sum();
    }

    private int getAllPassengerTransportBus() {
        return systemImitation.getBuses().stream()
                .mapToInt(BusObserver::getTransportedPassengers)
                .sum();
    }

    private int getLazyPassenger() {
        return systemImitation.getStations().stream()
                .mapToInt(StationObserver::getLazyPassenger)
                .sum();
    }
}

