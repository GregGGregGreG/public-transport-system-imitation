package ua.telesens.ostapenko.systemimitation.model.internal;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Map;
import java.util.UUID;

/**
 * @author root
 * @since 23.11.15
 */
@Getter
@EqualsAndHashCode
@RequiredArgsConstructor(staticName = "of")
@ToString(exclude = {"rules"})
public class Station {

    private UUID uuid = UUID.randomUUID();
    private final String name;
    @XStreamAlias("stationPassengerGenerationRules")
    private final Map<DayType, PassengerGenerationRuleList> rules;

}
