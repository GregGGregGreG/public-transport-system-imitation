package ua.telesens.ostapenko.systemimitation.model.internal;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ua.telesens.ostapenko.systemimitation.api.decorator.StationPublic;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author root
 * @since 23.11.15
 */
@Getter
@EqualsAndHashCode
@RequiredArgsConstructor(staticName = "of")
@ToString(exclude = {"uuid","rules"})
public class BusStation implements StationPublic {

    private UUID uuid = UUID.randomUUID();
    private final String name;
    private final Map<DayType, List<PassengerGenerationRule>> rules;

}
