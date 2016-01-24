package ua.telesens.ostapenko.systemimitation.model.internal;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import ua.telesens.ostapenko.systemimitation.api.decorator.RouteTransportPublic;
import ua.telesens.ostapenko.systemimitation.validation.ImitationSourceStep1;
import ua.telesens.ostapenko.systemimitation.validation.ImitationSourceStep2;
import ua.telesens.ostapenko.systemimitation.validation.constraint.CheckRouteArc;
import ua.telesens.ostapenko.systemimitation.validation.constraint.RoutePrice;
import ua.telesens.ostapenko.systemimitation.validation.constraint.rule.route.CheckRouteTrafficRuleListDuplicate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author root
 * @since 23.11.15
 */
@RequiredArgsConstructor(staticName = "of")
@EqualsAndHashCode(exclude = {"uuid"})
@ToString(exclude = "uuid")
@XStreamAlias("route")
public class Route implements RouteTransportPublic {

    public static final int MIN_NAME_LENGTH = 1;
    public static final int MAX_NAME_LENGTH = 10;
    public static final double MAX_PRICE = 12.50;
    public static final int MIN_COUNT_BUS = 1;
    public static final int MAX_COUNT_BUS = 20;
    public static final int MIN_COUNT_ARC = 1;
    public static final int MAX_COUNT_ARC = 25;
    public static final int MIN_PRICE = 0;
    public static final int MIN_COUNT_RULE = 1;
    public static final int MAX_COUNT_RULE = 1000;

    @Getter
    @NotNull(groups = ImitationSourceStep1.class)
    private UUID uuid = UUID.randomUUID();

    @Getter
    @NotNull(groups = ImitationSourceStep1.class)
    @Length(min = MIN_NAME_LENGTH, max = MAX_NAME_LENGTH, groups = ImitationSourceStep1.class)
    private final String name;

    @Getter
    @NotNull(groups = ImitationSourceStep1.class)
    private final RouteType type;

    @Getter
    @RoutePrice(min = MIN_PRICE, max = MAX_PRICE, groups = ImitationSourceStep1.class)
    private final double price;

    @XStreamAlias("arcs")
    @NotNull(groups = ImitationSourceStep1.class)
    @Size(min = MIN_COUNT_ARC, max = MAX_COUNT_ARC, groups = ImitationSourceStep1.class)
    @CheckRouteArc(groups = ImitationSourceStep2.class)
    @Valid
    private final List<RouteArc> arcList;

    @NotNull(groups = ImitationSourceStep1.class)
    @Size(min = MIN_COUNT_BUS, max = MAX_COUNT_BUS, groups = ImitationSourceStep1.class)
    @Valid
    private final List<Bus> buses;

    @Getter
    @NotNull(groups = ImitationSourceStep1.class)
    private final LocalTime starting;

    @XStreamAlias("routeTrafficRules")
    @NotNull(groups = ImitationSourceStep1.class)
    @Size(min = MIN_COUNT_RULE, max = MAX_COUNT_RULE, groups = ImitationSourceStep1.class)
    @CheckRouteTrafficRuleListDuplicate(groups = ImitationSourceStep2.class)
    @Valid
    private final List<RouteTrafficRuleList> rules;

    @Override
    public List<RouteArc> getArcList() {
        return Collections.unmodifiableList(arcList);
    }

    @Override
    public List<Bus> getBuses() {
        return Collections.unmodifiableList(buses);
    }

    @Override
    public List<RouteTrafficRuleList> getRules() {
        return Collections.unmodifiableList(rules);
    }
}