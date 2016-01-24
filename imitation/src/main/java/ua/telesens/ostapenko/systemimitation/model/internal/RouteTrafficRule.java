package ua.telesens.ostapenko.systemimitation.model.internal;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import ua.telesens.ostapenko.systemimitation.validation.ImitationSourceStep1;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.UUID;

/**
 * @author root
 * @since 29.12.15
 */
@Getter
@Setter
@RequiredArgsConstructor(staticName = "of")
@EqualsAndHashCode
@ToString
@XStreamAlias("routeTrafficRule")
public class RouteTrafficRule {

    private static final int MIN_COUNT_BUS = 1;
    private static final int MAX_COUNT_BUS = 20;
    private static final int MIN_COUNT_RACE = 1;
    private static final int MAX_COUNT_RACE = 20;

    @NotNull(groups = ImitationSourceStep1.class)
    private UUID uuid = UUID.randomUUID();

    @Range(min = MIN_COUNT_BUS, max = MAX_COUNT_BUS, groups = ImitationSourceStep1.class)
    private final int countBus;

    @Range(min = MIN_COUNT_RACE, max = MAX_COUNT_RACE, groups = ImitationSourceStep1.class)
    private final int countRace;
    // FIXME: 20.01.16 add validation from interval
    //Starting interval bus
    @NotNull(groups = ImitationSourceStep1.class)
    private final LocalTime interval;
    //Timeout between race
    @NotNull(groups = ImitationSourceStep1.class)
    private final LocalTime timeOut;

}
