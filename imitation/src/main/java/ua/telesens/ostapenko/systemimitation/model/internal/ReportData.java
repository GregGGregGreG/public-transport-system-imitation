package ua.telesens.ostapenko.systemimitation.model.internal;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

/**
 * @author root
 * @since 07.01.16
 */
@Builder
@Getter
@EqualsAndHashCode
@XStreamAlias("systemImitationReport")
public class ReportData {

    private final LocalDateTime starting;
    private final LocalDateTime end;
    private final Duration duration;
    private final int routes;
    private final int stations;
    private final int buses;
    @XStreamAlias("minStartGenerationPassenger")
    private final Map<DayType, LocalTime> startDay;
    @XStreamAlias("maxEndGenerationPassenger")
    private final Map<DayType, LocalTime> endDay;
    private final int busAvgCapacity;
    private final int numberTrips;
    private final int transportedPassenger;
    private final double busesPercentageOccupancy;
    private final int lostPassenger;

}
