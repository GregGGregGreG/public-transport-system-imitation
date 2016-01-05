package ua.telesens.ostapenko.systemimitation.model.internal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author root
 * @since 29.12.15
 */
@RequiredArgsConstructor(staticName = "of")
@Getter
@ToString
public class ImitationEvent {

    private final LocalDateTime time;
    private final DayType dayType;
}
