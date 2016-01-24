package ua.telesens.ostapenko.systemimitation.model.internal;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ua.telesens.ostapenko.systemimitation.validation.ImitationSourceStep1;
import ua.telesens.ostapenko.systemimitation.validation.ImitationSourceStep2;
import ua.telesens.ostapenko.systemimitation.validation.constraint.CheckRouteList;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author root
 * @since 07.01.16
 */
@Getter
@RequiredArgsConstructor(staticName = "of")
@EqualsAndHashCode
@ToString
@XStreamAlias("routes")
public class RouteList {

    private static final int MAX_COUNT_ROUTE = 100;
    private static final int MIN_COUNT_ROUTE = 1;

    @XStreamImplicit
    @NotNull(groups = ImitationSourceStep1.class)
    @Size(min = MIN_COUNT_ROUTE, max = MAX_COUNT_ROUTE, groups = ImitationSourceStep1.class)
    @CheckRouteList(groups = ImitationSourceStep2.class)
    @Valid
    private final List<Route> routes;
}
