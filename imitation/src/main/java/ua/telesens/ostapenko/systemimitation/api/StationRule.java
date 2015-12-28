package ua.telesens.ostapenko.systemimitation.api;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author root
 * @since 10.12.15
 */
public interface StationRule {

    boolean is(LocalTime time);

    int execute(LocalDateTime time);

}
