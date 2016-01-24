package ua.telesens.ostapenko.systemimitation.model.internal;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import ua.telesens.ostapenko.systemimitation.validation.ImitationSourceStep1;
import ua.telesens.ostapenko.systemimitation.validation.ImitationSourceStep2;
import ua.telesens.ostapenko.systemimitation.validation.constraint.CheckImitationSource;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author root
 * @since 20.01.16
 */
@XStreamAlias(value = "imitationSource")
@RequiredArgsConstructor(staticName = "of")
@Getter
@CheckImitationSource(groups = ImitationSourceStep2.class)
public class ImitationSource {

    private static final int MAX_TIME_WAITING_PASSENGER = 120;
    private static final int MIN_TIME_WAITING_PASSENGER = 0;

    @XStreamAlias(value = "startImitation")
    @NotNull(groups = ImitationSourceStep1.class)
    private final LocalDateTime starting;

    @XStreamAlias(value = "endImitation")
    @NotNull(groups = ImitationSourceStep1.class)
    private final LocalDateTime end;

    @NotNull(groups = ImitationSourceStep1.class)
    @Valid
    private final RouteList routeList;

    @NotNull(groups = ImitationSourceStep1.class)
    @Range(
            min = MIN_TIME_WAITING_PASSENGER,
            max = MAX_TIME_WAITING_PASSENGER,
            groups = ImitationSourceStep1.class
    )
    private final int minTimePassengerWaiting;

    @NotNull(groups = ImitationSourceStep1.class)
    @Range(
            min = MIN_TIME_WAITING_PASSENGER,
            max = MAX_TIME_WAITING_PASSENGER,
            groups = ImitationSourceStep1.class
    )
    private final int maxTimePassengerWaiting;

}
