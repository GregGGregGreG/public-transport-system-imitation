package ua.telesens.ostapenko.systemimitation.model.internal;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ua.telesens.ostapenko.systemimitation.api.decorator.RouteTransportPublic;

import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

/**
 * @author root
 * @since 23.11.15
 */
@Getter
@RequiredArgsConstructor(staticName = "of")
@EqualsAndHashCode(exclude = {})
@ToString(exclude = "uuid")
@XStreamAlias("route")
public class Route implements RouteTransportPublic {

    private UUID uuid = UUID.randomUUID();
    private final String name;
    private final RouteType type;
    private final double price;
    @XStreamAlias("arcs")
    private final Set<RouteArc> arcList;
    private final Set<Bus> buses;
    private final LocalTime starting;
    @XStreamAlias("routeTrafficRules")
    private final Set<RouteTrafficRuleList> rules;

}