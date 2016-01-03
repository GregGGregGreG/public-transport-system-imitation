package ua.telesens.ostapenko.systemimitation.model.internal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalTime;

/**
 * @author root
 * @since 29.12.15
 */
@ToString
@RequiredArgsConstructor(staticName = "of")
@Getter
public class RouteTrafficRule {

    private final int countBus;
    private final int countRace;
    private final LocalTime interval;
    private final LocalTime timeOut;

}
