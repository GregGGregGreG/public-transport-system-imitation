package ua.telesens.ostapenko.systemimitation.model.internal;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalTime;
import java.util.UUID;

/**
 * @author root
 * @since 09.12.15
 */

@Getter
@RequiredArgsConstructor(staticName = "of")
@EqualsAndHashCode
@ToString(exclude = "uuid")
@XStreamAlias("arc")
public class RouteArc {

    private UUID uuid = UUID.randomUUID();
    @XStreamAlias("initialStation")
    private final Station start;
    @XStreamAlias("finalStation")
    private final Station end;
    private final LocalTime interval;

}
