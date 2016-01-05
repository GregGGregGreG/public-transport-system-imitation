package ua.telesens.ostapenko.systemimitation.model.internal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author root
 * @since 14.12.15
 */
@Getter
@RequiredArgsConstructor(staticName = "of")
@ToString
public class Passenger {

    private final BusStation station;

}
