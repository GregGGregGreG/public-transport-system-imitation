package ua.telesens.ostapenko.systemimitation.model.internal;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ua.telesens.ostapenko.systemimitation.validation.ImitationSourceStep1;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author root
 * @since 07.01.16
 */
@Getter
@RequiredArgsConstructor(staticName = "of")
@EqualsAndHashCode(exclude = "routeTrafficRules")
@ToString
@XStreamAlias("trafficRules")
public class RouteTrafficRuleList {

    private static final int MIN_COUNT_RULE = 1;
    private static final int MAX_COUNT_RULE = 100;

    @NotNull(groups = ImitationSourceStep1.class)
    private final DayType dayType;

    @XStreamImplicit(itemFieldName = "trafficRule")
    @NotNull(groups = ImitationSourceStep1.class)
    @Size(min = MIN_COUNT_RULE, max = MAX_COUNT_RULE, groups = ImitationSourceStep1.class)
    @Valid
    private final List<RouteTrafficRule> routeTrafficRules;

}
