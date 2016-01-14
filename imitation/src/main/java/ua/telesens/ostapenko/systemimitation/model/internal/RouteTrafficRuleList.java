package ua.telesens.ostapenko.systemimitation.model.internal;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @author root
 * @since 07.01.16
 */
@Getter
@RequiredArgsConstructor(staticName = "of")
@EqualsAndHashCode(exclude = "routeTrafficRules")
@XStreamAlias("trafficRules")
public class RouteTrafficRuleList {

    private final DayType dayType;
    @XStreamImplicit(itemFieldName = "trafficRule")
    private final List<RouteTrafficRule> routeTrafficRules;

}
