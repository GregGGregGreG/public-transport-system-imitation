package ua.telesens.ostapenko.systemimitation.model.internal;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author root
 * @since 07.01.16
 */
@Builder
@Getter
@EqualsAndHashCode
@XStreamAlias("systemImitationReport")
public class Report {

    private final LocalDateTime startImitation;
    private final LocalDateTime endImitation;
    private final Duration duration;
    private final int routes;
    private final int stations;
    private final int buses;
    private final int busAvgCapacity;
    private final int numberTrips;
    private final int transportedPassenger;
    private final double busesPercentageOccupancy;
    private final long lazyPassenger;

}
