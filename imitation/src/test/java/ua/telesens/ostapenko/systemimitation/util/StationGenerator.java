package ua.telesens.ostapenko.systemimitation.util;

import ua.telesens.ostapenko.systemimitation.model.internal.Station;

/**
 * @author root
 * @since 20.01.16
 */
public class StationGenerator {

    public static Station generateWithOutRule() {
        return Station.of(RandomStringGenerator.generate(), null);
    }
}
