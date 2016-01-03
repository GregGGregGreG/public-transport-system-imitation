package ua.telesens.ostapenko.systemimitation.api.decorator;

import ua.telesens.ostapenko.systemimitation.model.internal.DayType;
import ua.telesens.ostapenko.systemimitation.model.internal.PassengerGenerationRule;

import java.util.List;
import java.util.Map;

/**
 * @author root
 * @since 29.12.15
 */
public interface StationPublic {

    String getName();

    Map<DayType, List<PassengerGenerationRule>> getRules();
}
