package ua.telesens.ostapenko.systemimitation.model.internal;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ua.telesens.ostapenko.systemimitation.api.decorator.TransportPublic;

import java.util.UUID;

/**
 * @author root
 * @since 23.11.15
 */
@Getter
@RequiredArgsConstructor(staticName = "of")
@ToString(exclude = {"uuid","countPassenger"})
@EqualsAndHashCode
public class Bus implements TransportPublic {

    private UUID uuid = UUID.randomUUID();
    private final int number;
    private final int countPassenger;

}
