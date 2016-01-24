package ua.telesens.ostapenko.systemimitation.model.internal;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;
import ua.telesens.ostapenko.systemimitation.validation.ImitationSourceStep1;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.UUID;

/**
 * @author root
 * @since 09.12.15
 */

@Getter
@RequiredArgsConstructor(staticName = "of")
@EqualsAndHashCode(exclude = "uuid")
@ToString(exclude = "uuid")
@XStreamAlias("arc")
public class RouteArc {

    @NotNull(groups = ImitationSourceStep1.class)
    private UUID uuid = UUID.randomUUID();

    @XStreamAlias("numberArc")
    @Range(max = Route.MAX_COUNT_ARC, groups = ImitationSourceStep1.class)
    private final int number;

    @XStreamAlias("initialStation")
    @NotNull(groups = ImitationSourceStep1.class)
    @Valid
    private final Station start;

    @XStreamAlias("finalStation")
    @NotNull(groups = ImitationSourceStep1.class)
    @Valid
    private final Station end;

    @NotNull(groups = ImitationSourceStep1.class)
    private final LocalTime interval;

}
