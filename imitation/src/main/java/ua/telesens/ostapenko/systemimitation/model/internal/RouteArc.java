package ua.telesens.ostapenko.systemimitation.model.internal;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalTime;
import java.util.UUID;

/**
 * @author root
 * @since 09.12.15
 */
@Data
@Getter
@RequiredArgsConstructor(staticName = "of")
@ToString(exclude = "uuid")
public class RouteArc {

    private UUID uuid = UUID.randomUUID();
    private final BusStation start;
    private final BusStation end;
    private final LocalTime interval;

}
