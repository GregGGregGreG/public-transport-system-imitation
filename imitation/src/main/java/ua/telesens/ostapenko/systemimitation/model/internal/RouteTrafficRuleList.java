package ua.telesens.ostapenko.systemimitation.model.internal;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * @author root
 * @since 07.01.16
 */
@Getter
@RequiredArgsConstructor(staticName = "of")
@EqualsAndHashCode
@XStreamAlias("trafficRules")
public class RouteTrafficRuleList {

    private UUID uuid = UUID.randomUUID();
    @XStreamImplicit(itemFieldName = "trafficRule")
    private final List<RouteTrafficRule> routeTrafficRules;

}
