package ua.telesens.ostapenko.systemimitation.model.internal;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ua.telesens.ostapenko.systemimitation.validation.RouteListStep1;
import ua.telesens.ostapenko.systemimitation.validation.RouteListStep2;
import ua.telesens.ostapenko.systemimitation.validation.constraint.rule.station.CheckPassengerRule;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.List;

/**
 * @author root
 * @since 07.01.16
 */
@RequiredArgsConstructor(staticName = "of")
@EqualsAndHashCode(exclude = "passengerGenerationRules")
@XStreamAlias("passengerGenerationRules")
public class PassengerGenerationRuleList {

    public static final int MIN_COUNT_RULE = 1;
    public static final int MAX_COUNT_RULE = 100;

    @Getter
    @NotNull(groups = RouteListStep1.class)
    private final DayType dayType;

    @XStreamImplicit
    @NotNull(groups = RouteListStep1.class)
    @Size(min = MIN_COUNT_RULE, max = MAX_COUNT_RULE, groups = RouteListStep1.class)
    @CheckPassengerRule(groups = RouteListStep2.class)
    @Valid
    private final List<PassengerGenerationRule> passengerGenerationRules;

    public List<PassengerGenerationRule> getPassengerGenerationRules() {
        return Collections.unmodifiableList(passengerGenerationRules);
    }
}
