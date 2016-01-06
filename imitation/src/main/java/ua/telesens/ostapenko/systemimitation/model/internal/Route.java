package ua.telesens.ostapenko.systemimitation.model.internal;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ua.telesens.ostapenko.systemimitation.api.decorator.RouteTransportPublic;

import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author root
 * @since 23.11.15
 */
@Getter
@EqualsAndHashCode(exclude = {"arcList", "rules", "buses"})
@RequiredArgsConstructor(staticName = "of")
@ToString(exclude = "uuid")
public class Route implements RouteTransportPublic {

    private UUID uuid = UUID.randomUUID();
    private final String name;
    private final RouteType type;
    private final Collection<RouteArc> arcList;
    private final Collection<Bus> buses;
    private final LocalTime starting;
    private final Map<DayType, List<RouteTrafficRule>> rules;

}