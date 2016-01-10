package ua.telesens.ostapenko.systemimitation.model.internal;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalTime;
import java.util.UUID;

/**
 * @author root
 * @since 29.12.15
 */
@Getter
@RequiredArgsConstructor(staticName = "of")
@EqualsAndHashCode
@ToString
@XStreamAlias("routeTrafficRule")
public class RouteTrafficRule {

    private UUID uuid = UUID.randomUUID();
    private final int countBus;
    private final int countRace;
    private final LocalTime interval;
    private final LocalTime timeOut;

}
